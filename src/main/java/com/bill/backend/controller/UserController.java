package com.bill.backend.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bill.backend.modules.constant.Setting;
import com.google.code.kaptcha.Producer;
import com.bill.backend.modules.BaseResponse;
import com.bill.backend.modules.constant.ErrorCode;
import com.bill.backend.modules.constant.Setting;
import com.bill.backend.modules.dao.UserRequest;
import com.bill.backend.modules.entity.User;
import com.bill.backend.modules.exception.BusinessException;
import com.bill.backend.service.UserService;
import com.bill.backend.utils.*;
import com.google.code.kaptcha.Producer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@Api(tags = "用户")
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private Producer captchaProducerMath;
    @Resource
    private RedisCacheUtils redisCacheUtils;
    @Resource
    private HttpServletRequest request;


    @ApiOperation("获取用户信息")
    @GetMapping("/get")
    public BaseResponse<User> get(@RequestHeader("Authorization") String token) {

        User self = userService.getCurrentUser(token);

        User user = userService.getByUsername(self.getUsername());

        return ResultUtils.success(user, "");
    }

    @ApiOperation("获取用户列表")
    @PostMapping("/list")
    public BaseResponse<Map<String, Object>> list(
            @RequestHeader("Authorization") String token,
            @RequestParam("page") Long pageNum,
            @RequestParam("pageSize") Integer pageSize) {

        User self = userService.getCurrentUser(token);

        if (self.getAuth().equals("user"))
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "无权限");

        if (pageNum == null || pageNum < 1)
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "页码错误");

        if (pageSize == null || pageSize < 1)
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "页大小错误");

        Page<User> userPage = new Page<>(pageNum, pageSize);
        IPage<User> list = userService.list(self.getAuth(), userPage);
        List<User> users = list.getRecords();

        for (User user : users) {
            if (user.getAvatar() != null && !user.getAvatar().isEmpty()) {
                user.setAvatar(FileUtils.fileCompletePath(request, "", user.getAvatar()));
            } else {
                user.setAvatar("");
            }
        }

        Map<String, Object> map = new HashMap<>();
        map.put("total", list.getTotal());
        map.put("list", users);
        map.put("page", pageNum);
        map.put("pageSize", pageSize);

        return ResultUtils.success(map, "查询成功");
    }

    @ApiOperation("注册")
    @PostMapping("/register")
    public BaseResponse<Boolean> register(@RequestBody UserRequest userRequest) {
        String username = userRequest.getUsername();
        String password = userRequest.getPassword();
        String checkPassword = userRequest.getCheckPassword();
        if (!password.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次密码不一致");
        }

        String uuid = userRequest.getUuid();
        if (!redisCacheUtils.hasKey(uuid)) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "验证码已过期");
        }

        String code = userRequest.getCode();
        if (!redisCacheUtils.getCache(uuid).equals(code)) {
            redisCacheUtils.delete(uuid);
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "验证码错误");
        }

        User user = userService.getByUsername(username);
        if (user != null) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "创建失败，用户已存在");
        }
        if (!userService.register(username, password)) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "注册失败");
        }
        return ResultUtils.success(true, "注册成功");
    }

    @ApiOperation("登录")
    @PostMapping("/login")
    public BaseResponse<Map<String, Object>> login(@RequestBody UserRequest userRequest) {
        String username = userRequest.getUsername();
        String password = userRequest.getPassword();
        String uuid = userRequest.getUuid();
        if (!redisCacheUtils.hasKey(uuid)) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "验证码已过期");
        }
        String code = userRequest.getCode();
        if (!redisCacheUtils.getCache(uuid).equals(code)) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "验证码错误");
        }
        User login = userService.login(username, password);
        if (login == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "用户名/密码错误");
        }
        if (login.getStatus() == 1) {
            throw new BusinessException(ErrorCode.FORBIDDEN_ERROR, "用户已被禁用");
        }
        // 生成token
        String token = UUIDUtil.get32UUID();
        // 该token 有效期 24小时
        redisCacheUtils.setCache(token, login, Setting.LOGIN_TIMEOUT, TimeUnit.MINUTES);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("token", token);
        resultMap.put("failure_time", (int) (System.currentTimeMillis() / 1000 + Setting.LOGIN_TIMEOUT * 60));
        return ResultUtils.success(resultMap, "登录成功!");
    }

    @ApiOperation("获取用户信息")
    @GetMapping("/logout")
    public BaseResponse<Boolean> logout(@RequestHeader("Authorization") String token) {
        User self = userService.getCurrentUser(token);

        if (self == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "用户已退出");
        }

        redisCacheUtils.delete(token);
        return ResultUtils.success(true, "退出成功");
    }

    @ApiOperation("修改用户密码")
    @PostMapping("/updatePassword")
    public BaseResponse<Boolean> updatePassword(@RequestHeader("Authorization") String token, @RequestBody UserRequest userRequest) {

        User self = userService.getCurrentUser(token);

        if (!UserUtils.encodePassword(userRequest.getPassword()).equals(self.getPassword())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "原密码错误");
        }

        if (!userRequest.getNewPassword().equals(userRequest.getCheckPassword())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "新密码与确认密码不一致");
        }

        if (UserUtils.encodePassword(userRequest.getNewPassword()).equals(self.getPassword())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "新密码不能与原密码相同");
        }


        return userService.updatePassword(self.getUsername(), userRequest.getNewPassword()) > 0 ?
                ResultUtils.success(true, "修改成功") :
                ResultUtils.error(ErrorCode.SYSTEM_ERROR, "修改失败");

    }

    @ApiOperation("更新用户信息")
    @PostMapping("/update")
    public BaseResponse<Boolean> update(@RequestHeader("Authorization") String token, @RequestBody User user) {
        User self = userService.getCurrentUser(token);
        Integer res;
        if ((self.getUsername().equals(user.getUsername())) ||
                (self.getAuth().equals("root") && (user.getAuth().equals("admin") || user.getAuth().equals("user"))) ||
                self.getAuth().equals("admin") && user.getAuth().equals("user")
        ) {
            res = userService.update(user);
        } else {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "没有修改权限");
        }
        return res > 0 ? ResultUtils.success(true, "修改成功") : ResultUtils.error(ErrorCode.SYSTEM_ERROR, "修改失败");
    }

    @ApiOperation("删除用户")
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestHeader("Authorization") String token, @RequestBody User u) {
        User self = userService.getCurrentUser(token);

        User user = userService.getByUsername(u.getUsername());
        if (user == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "删除用户不存在");
        }
        Integer res;
        if ((self.getUsername().equals(user.getUsername())) ||
                (self.getAuth().equals("root") && (user.getAuth().equals("admin") || user.getAuth().equals("user"))) ||
                self.getAuth().equals("admin") && user.getAuth().equals("user")
        ) {
            res = userService.delete(user.getUsername());
        } else {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "没有删除权限");
        }
        return res > 0 ? ResultUtils.success(true, "删除成功") : ResultUtils.error(ErrorCode.SYSTEM_ERROR, "修改失败");
    }

    @ApiOperation("生成图片验证码")
    @GetMapping("/captcha")
    public BaseResponse<Map<String, String>> getCaptcha() {

        String text = captchaProducerMath.createText();
        String capStr = text.substring(0, text.lastIndexOf("@"));
        String code = text.substring(text.lastIndexOf("@") + 1);

        BufferedImage image = captchaProducerMath.createImage(capStr);
        String uuid = UUIDUtil.get32UUID();
        // 验证码有效期 2 分钟
        redisCacheUtils.setCache(uuid, code, Setting.CAPTCHA_TIMEOUT, TimeUnit.MINUTES);

        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", os);
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "验证码生成失败");
        }
        Map<String, String> map = new HashMap<>();
        map.put("uuid", uuid);
        map.put("img", Base64Utils.encode(os.toByteArray()));
        return ResultUtils.success(map, "");
    }
}
