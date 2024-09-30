package vip.ilstudy.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.ilstudy.entity.LoginUserEntity;
import vip.ilstudy.entity.ResultEntity;
import vip.ilstudy.service.UserService;
import vip.ilstudy.utils.ResultUtils;

@RestController
@RequestMapping("")
public class LoginController extends BaseController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResultEntity<Boolean> login(@Valid @RequestBody LoginUserEntity loginUser) {

        return ResultUtils.success(true);
    }

    @PostMapping("/register")
    public ResultEntity<Boolean> register(@Valid @RequestBody LoginUserEntity loginUser) {
        Integer i = userService.insertUser(loginUser);
        if (i == 0) {
            return ResultUtils.error("用户名已存在");
        }
        return ResultUtils.success();
    }
}
