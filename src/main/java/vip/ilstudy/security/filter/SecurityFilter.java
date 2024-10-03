package vip.ilstudy.security.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import vip.ilstudy.config.Constant;
import vip.ilstudy.entity.LoginUserEntity;
import vip.ilstudy.service.LoginUserService;
import vip.ilstudy.service.TokenService;
import vip.ilstudy.utils.JwtTokenUtils;
import vip.ilstudy.utils.ResultUtils;
import vip.ilstudy.utils.ServletUtils;

import java.io.IOException;
import java.util.Arrays;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    Logger log = LoggerFactory.getLogger(SecurityFilter.class);

    @Autowired
    private TokenService tokenService;
    @Autowired
    private LoginUserService loginUserService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestUri = request.getRequestURI();

        if (Arrays.asList(Constant.WRITE_PATH).contains(requestUri)) {
            filterChain.doFilter(request, response);
            return;
        } else if (Arrays.asList(Constant.BLACK_PATH).contains(requestUri)) {
            String jsonString = JSON.toJSONString(ResultUtils.error("无权限访问"));
            ServletUtils.renderString(response, jsonString);
            return;
        }

        String token = JwtTokenUtils.getToken(request);
        if (StringUtils.isEmpty(token)) {
            log.error("token 为空");
            String jsonString = JSONObject.toJSONString(ResultUtils.error("未登录"));
            ServletUtils.renderString(response, jsonString);
            return;
        }
        try {
            tokenService.verifyToken(request);
            LoginUserEntity loginUser = loginUserService.getLoginUser(request);
            log.info("用户 {} 登录成功", loginUser.getUsername());
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            String jsonString = JSON.toJSONString(ResultUtils.error("无权限访问"));
            log.error(jsonString);
            ServletUtils.renderString(response, jsonString);
        }

    }
}
