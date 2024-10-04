package vip.ilstudy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.Set;

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
    @JsonIgnore
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 登录用户权限
     */
    private Set<String> permissions;
    /**
     * token
     */
    @JsonIgnore
    private String token;

    /**
     * 用户实体
     */
    private UserEntity userEntity;

    public LoginUserEntity() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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

    public Boolean isAdmin(){
        return username.equals("admin");
    }
}
