package vip.ilstudy.service;


import io.jsonwebtoken.Claims;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vip.ilstudy.config.Constant;
import vip.ilstudy.entity.LoginUserEntity;
import vip.ilstudy.entity.UserEntity;
import vip.ilstudy.utils.JwtTokenUtils;

@Service
public class LoginUserService implements UserDetailsService {
    Logger log = org.slf4j.LoggerFactory.getLogger(LoginUserService.class);

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;
    @Autowired
    private RedisCacheService redisCacheService;

    /**
     * 实现获取用户方法
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntityByUsername;
        try {
            userEntityByUsername = userService.getUserEntityByUsername(username);
        } catch (Exception e) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        LoginUserEntity loginUserEntity = new LoginUserEntity();
        loginUserEntity.setUsername(userEntityByUsername.getUsername());
        loginUserEntity.setPassword(userEntityByUsername.getPassword());

        return loginUserEntity;
    }

    /**
     * 获取用户信息
     * @param request
     * @return
     */
    public LoginUserEntity getLoginUser(HttpServletRequest request) throws Exception {
        // 获取请求携带的令牌
        String token = JwtTokenUtils.getToken(request);
        if (StringUtils.isNotEmpty(token)) {
            try {
                Claims claims = tokenService.parseToken(token);
                // 解析对应的权限以及用户信息
                String uuid = (String) claims.get(Constant.LOGIN_USER_KEY);
                String userKey = tokenService.getTokenKey(uuid);
                return redisCacheService.getCacheObject(userKey);
            } catch (Exception e) {
                log.error("获取用户信息异常'{}'", e.getMessage());
                throw new Exception("获取用户信息异常");
            }
        }
        return null;
    }
}
