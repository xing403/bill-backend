package vip.ilstudy.security.handler;

import com.alibaba.fastjson.JSON;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import vip.ilstudy.config.Constant;
import vip.ilstudy.entity.LoginUserEntity;
import vip.ilstudy.service.RedisCacheService;
import vip.ilstudy.service.TokenService;
import vip.ilstudy.utils.JwtTokenUtils;
import vip.ilstudy.utils.ResultUtils;
import vip.ilstudy.utils.ServletUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 认证上下文处理
 */

@Component
public class AuthenticationContextHandler extends SimpleUrlAuthenticationFailureHandler
        implements AuthenticationSuccessHandler, AuthenticationEntryPoint, AccessDeniedHandler {

    Logger log = LoggerFactory.getLogger(AuthenticationContextHandler.class);

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RedisCacheService redisCacheService;

    /**
     * 登录成功处理
     *
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 组装JWT
        LoginUserEntity loginUserEntity = (LoginUserEntity) authentication.getPrincipal();
        String token = tokenService.generateToken(loginUserEntity);
        //存放token到cookie中，最好时直接返回json
//        JwtTokenUtils.setCookieToken(response, token);
        // token 与 loginUserEntity 记录到redis 中

        redisCacheService.setCacheObject(
                tokenService.getTokenKey(loginUserEntity.getToken()),
                loginUserEntity,
                Constant.TOKEN_EXPIRE_TIME,
                TimeUnit.SECONDS
        );

        log.info("用户 {} 登录成功", loginUserEntity.getUsername());

        String jsonString = JSON.toJSONString(ResultUtils.success(token));
        ServletUtils.renderString(response, jsonString);
    }

    /**
     * 登录失败处理
     *
     * @param request
     * @param response
     * @param exception
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String username = request.getParameter("username");

        String message = String.format("用户 %s 不存在", username);

        if (exception instanceof LockedException) {
            message = String.format("用户 %s 被冻结", username);
        } else if (exception instanceof BadCredentialsException) {
            message = "用户名或密码错误";
        }

        log.error("登录异常：{}", message);

        String jsonString = JSON.toJSONString(ResultUtils.error(message));
        ServletUtils.renderString(response, jsonString);
    }

    /**
     * 认证失败处理
     *
     * @param request
     * @param response
     * @param authException
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        log.error("[commence][访问 URL({}) 时，没有登录]", request.getRequestURI(), authException);

        response.sendError(500, "未登录");
    }

    /**
     * 鉴权失败处理
     *
     * @param request
     * @param response
     * @param accessDeniedException
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {

        log.warn("[commence][访问 URL({}) 时，用户({}) 权限不够]", request.getRequestURI(), "", accessDeniedException);
        response.sendError(500, "未登录");
    }

}
