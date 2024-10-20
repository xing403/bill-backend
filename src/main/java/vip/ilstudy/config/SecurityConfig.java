package vip.ilstudy.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import vip.ilstudy.handler.security.filter.SecurityFilter;
import vip.ilstudy.handler.security.handler.AuthenticationContextHandler;
import vip.ilstudy.service.LoginUserService;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    @Autowired
    private LoginUserService loginUserService;

    @Autowired
    private SecurityFilter securityFilter;
    @Autowired
    private AuthenticationContextHandler authenticationContextHandler;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // 加密方式
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 核心配置
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity request) throws Exception {
        request
                .csrf(AbstractHttpConfigurer::disable)
                // 自定义登录处理器
                .formLogin(formLoginCustomizer -> {
                            formLoginCustomizer
                                    .loginProcessingUrl("/login")
                                    .usernameParameter("username")
                                    .passwordParameter("password")
                                    // 登录鉴权成功处理器 onAuthenticationSuccess
                                    .successHandler(authenticationContextHandler)
                                    // 登录鉴权失败处理器 onAuthenticationFailure
                                    .failureHandler(authenticationContextHandler)
                                    .permitAll();
                        }
                )
                .logout(AbstractHttpConfigurer::disable) // 禁用默认退出功能
                //  基于 token， 不需要 session
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //  设置 处理鉴权失败、认证失败
                .exceptionHandling(exceptions ->
                        exceptions
                                // 认证入口点 commence
                                .authenticationEntryPoint(authenticationContextHandler)
                                // 访问拒绝处理器 handle
                                .accessDeniedHandler(authenticationContextHandler)
                )
                .authorizeHttpRequests(authorizeHttpRequest -> authorizeHttpRequest
                        .requestMatchers("/login", "/register").permitAll()
                        .requestMatchers("/websocket/**").permitAll() // 允许WebSocket端点
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
        return request.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return loginUserService;
    }

    /**
     * @return
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        // 调用 loadUserByUserName 获取userDetail信息，在 AbstractUserDetailsAuthenticationProvider 里执行用户状态检查
        authProvider.setUserDetailsService(loginUserService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }


}
