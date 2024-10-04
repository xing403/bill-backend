package vip.ilstudy.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vip.ilstudy.entity.LoginUserEntity;
import vip.ilstudy.entity.ResultEntity;
import vip.ilstudy.service.LoginUserService;
import vip.ilstudy.service.UserService;
import vip.ilstudy.utils.ResultUtils;

@RestController
@RequestMapping("")
public class LoginController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private LoginUserService loginUserService;

    /**
     * 登录 (该路由 并未执行，而是直接进行登录校验)
     *
     * @param loginUser
     * @return
     */
    @PostMapping("/login")
    public ResultEntity<Boolean> login(@Valid @RequestBody LoginUserEntity loginUser) {
        return ResultUtils.success(true);
    }

    /**
     * 用户注册
     *
     * @param loginUser
     * @return
     */
    @PostMapping("/register")
    public ResultEntity<Boolean> register(@Valid @RequestBody LoginUserEntity loginUser) {
        Integer i = userService.insertUser(loginUser);
        if (i == 0) {
            return ResultUtils.error("用户名已存在");
        }
        return ResultUtils.success();
    }

    /**
     * 退出登录
     *
     * @return
     */
    @PostMapping("/logout")
    public ResultEntity<Boolean> logout() {
        Boolean logout = loginUserService.logout();
        if (logout) {
            return ResultUtils.success();
        }
        return ResultUtils.error("账号已退出，或登录已过期");
    }
}
