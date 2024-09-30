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
import vip.ilstudy.entity.LoginUserEntity;
import vip.ilstudy.entity.ResultEntity;
import vip.ilstudy.service.TokenService;
import vip.ilstudy.utils.JwtTokenUtils;
import vip.ilstudy.utils.ResultUtils;

import java.io.EOFException;
import java.io.IOException;

/**
 * 认证上下文处理
 */

@Component
public class AuthenticationContextHandler extends SimpleUrlAuthenticationFailureHandler
        implements AuthenticationSuccessHandler, LogoutSuccessHandler, AuthenticationEntryPoint, AccessDeniedHandler {

    Logger log = LoggerFactory.getLogger(AuthenticationContextHandler.class);

    @Autowired
    private TokenService tokenService;

    /**
     * 鉴权成功处理
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
        JwtTokenUtils.setCookieToken(response, token);
        log.info("登录成功");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String jsonString = JSON.toJSONString(ResultUtils.success(token));
        response.getWriter().write(jsonString);
    }

    /**
     * 认证失败处理
     *
     * @param request
     * @param response
     * @param exception
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if (exception != null) {
            log.info("【用户名不存在】" + exception.getMessage());
            return;
        }
        if (exception instanceof LockedException) {
            log.info("【用户被冻结】" + exception.getMessage());
            return;
        }
        if (exception instanceof BadCredentialsException) {
            log.info("【用户名或密码错误】" + exception.getMessage());
            throw new EOFException();
        }
        log.info("-----------登录验证失败，其他登录失败错误");

    }

    /**
     * 退出登录处理器
     *
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        JwtTokenUtils.deleteCookieToken(response);
        response.sendError(500, "未登录");
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
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.error("[commence][访问 URL({}) 时，没有登录]", request.getRequestURI(), authException);
        String requestUri = request.getRequestURI();
        String browser = request.getHeader("user-agent");
        // 请求login 或者 又admin字段都判断未登录 需要重新登录
        //        if (isAdminLogin(requestUri) ) {
        //            JwtTokenUtils.deleteCookieToken(response);
        //            String token = JwtTokenUtils.getToken(request);
        //            response.sendRedirect("/xxx/login.html");
        //        }
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
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        log.warn("[commence][访问 URL({}) 时，用户({}) 权限不够]", request.getRequestURI(), "", accessDeniedException);
        response.sendError(500, "未登录");
    }

}
