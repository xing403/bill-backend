package vip.ilstudy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
