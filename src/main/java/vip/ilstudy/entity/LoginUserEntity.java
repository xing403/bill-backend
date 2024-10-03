package vip.ilstudy.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.List;

/**
 * 登录用户实体类
 * isAccountNonLocked -> isEnabled -> isAccountNonExpired -> isCredentialsNonExpired
 */
@Data
public class LoginUserEntity implements UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * token
     */
    private String token;

    public LoginUserEntity() {
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    /**
     * 账号未过期
     *
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 账号未上锁
     *
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 证书未过期
     *
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 已启用
     *
     * @return
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
