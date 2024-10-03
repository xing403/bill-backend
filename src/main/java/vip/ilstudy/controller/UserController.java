package vip.ilstudy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.ilstudy.entity.ResultEntity;
import vip.ilstudy.entity.UserEntity;
import vip.ilstudy.service.LoginUserService;
import vip.ilstudy.utils.ResultUtils;
import vip.ilstudy.utils.ServletUtils;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private LoginUserService loginUserService;

    @GetMapping("information")
    public ResultEntity<UserEntity> getUserInformation() throws Exception {
        UserEntity loginUser = loginUserService.getLoginUser(ServletUtils.getRequest()).getUserEntity();
        return ResultUtils.success(loginUser);
    }
}
