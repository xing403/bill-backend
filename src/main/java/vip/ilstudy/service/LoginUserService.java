package vip.ilstudy.service;


import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import vip.ilstudy.entity.LoginUserEntity;
import vip.ilstudy.entity.UserEntity;
import vip.ilstudy.utils.SecurityUtils;

@Service
public class LoginUserService implements UserDetailsService {
    Logger log = org.slf4j.LoggerFactory.getLogger(LoginUserService.class);
    @Autowired
    private UserService userService;

    /**
     * 实现获取用户方法
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
}
