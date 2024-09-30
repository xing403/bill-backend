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
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import vip.ilstudy.security.filter.SecurityFilter;
import vip.ilstudy.security.handler.AuthenticationContextHandler;
import vip.ilstudy.service.LoginUserService;

@Slf4j
@Configuration
@EnableWebSecurity
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
        return request
                .csrf(AbstractHttpConfigurer::disable)
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
                .logout(logoutCustomizer ->
                        // 退出登录成功处理器 logoutSuccess
                        logoutCustomizer.logoutSuccessHandler(authenticationContextHandler)
                )
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
                //  设置 controller 权限
                .authorizeHttpRequests(authorizeHttpRequest -> authorizeHttpRequest
                                .requestMatchers("/login", "/register").anonymous()
                                //  允许直接访问 授权登录接口
//                        .requestMatchers(HttpMethod.POST, "/web/authenticate").permitAll()
                                //  允许 SpringMVC 的默认错误地址匿名访问
//                        .requestMatchers("/error").permitAll()
                                //  其他所有接口必须有Authority信息，Authority在登录成功后的UserDetailImpl对象中默认设置“ROLE_USER”
                                //.requestMatchers("/**").hasAnyAuthority("ROLE_USER")
//                        .requestMatchers("/heartBeat/**", "/main/**").permitAll()
                                //  允许任意请求被已登录用户访问，不检查Authority
                                .requestMatchers("/**").permitAll()
                                .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
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

    /**
     * 配置跨源访问(CORS)
     *
     * @return
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

}
