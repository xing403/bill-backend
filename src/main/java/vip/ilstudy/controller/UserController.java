package vip.ilstudy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vip.ilstudy.config.constant.Constant;
import vip.ilstudy.entity.LoginUserEntity;
import vip.ilstudy.entity.ResultEntity;
import vip.ilstudy.entity.TablePageEntity;
import vip.ilstudy.entity.UserEntity;
import vip.ilstudy.service.LoginUserService;
import vip.ilstudy.service.RedisCacheService;
import vip.ilstudy.service.UserService;
import vip.ilstudy.utils.ResultUtils;
import vip.ilstudy.utils.ServletUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private LoginUserService loginUserService;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisCacheService redisCacheService;


    @GetMapping("information")
    public ResultEntity<UserEntity> getUserInformation() throws Exception {
        UserEntity loginUser = loginUserService.getLoginUser(ServletUtils.getRequest()).getUserEntity();
        return ResultUtils.success(loginUser);
    }

    @GetMapping("information/{id}")
    public ResultEntity<UserEntity> getUserInformationByUserId(@PathVariable("id") Long userId) throws Exception {
        UserEntity loginUser = userService.getUserEntityById(userId);
        return ResultUtils.success(loginUser);
    }
    @GetMapping("list")
    public ResultEntity<TablePageEntity<UserEntity>> getUserList(@RequestParam("pageNum") Long pageNum, @RequestParam("pageSize") Long pageSize) {
        IPage<UserEntity> userList = userService.getUserList(pageNum, pageSize);
        return ResultUtils.success(new TablePageEntity<>(userList));
    }

    @PostMapping("")
    public ResultEntity<Boolean> addUser(@RequestBody LoginUserEntity loginUser) {
        Integer i = userService.insertUser(loginUser);
        if(i > 0){
            return ResultUtils.success();
        }
        return ResultUtils.error("添加失败");
    }

    @DeleteMapping("/{userId}")
    public ResultEntity<Boolean> deleteUserByUserId(@PathVariable("userId") Long userId) {
        if(userService.deleteUserById(userId)){
            return ResultUtils.success();
        }
        return ResultUtils.error("删除失败");
    }

    @GetMapping("/enable/{userId}")
    public ResultEntity<Boolean> enableUserByUserId(@PathVariable("userId") Long userId) throws Exception {
        if(userService.enableUserByUserId(userId)){
            return ResultUtils.success();
        }
        return ResultUtils.error("操作失败");
    }
    @GetMapping("/disable/{userId}")
    public ResultEntity<Boolean> disableUserByUserId(@PathVariable("userId") Long userId) throws Exception {
        if(userService.disableUserByUserId(userId)){
            return ResultUtils.success();
        }
        return ResultUtils.error("操作失败");
    }
    @PutMapping("")
    public ResultEntity<Boolean> updateUserByUsername(@RequestBody UserEntity userEntity) {
        if(userService.updateUserByUserName(userEntity)){
            return ResultUtils.success();
        }
        return ResultUtils.error("更新失败");
    }

    @GetMapping("allLoginUser")
    public ResultEntity<TablePageEntity<UserEntity>> getAllLoginUser() {
        Collection<String> keys = redisCacheService.keys(Constant.LOGIN_TOKEN_KEY + "*");
        List<UserEntity> userEntities = new ArrayList<>();
        for (String key : keys) {
            LoginUserEntity loginUser = redisCacheService.getCacheObject(key);
            userEntities.add(loginUser.getUserEntity());
        }
        return ResultUtils.success(new TablePageEntity<>(
                userEntities,
                1L,
                (long) userEntities.size(),
                (long) userEntities.size())
        );
    }
}
