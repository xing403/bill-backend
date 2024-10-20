package vip.ilstudy.handler.security.filter;

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
import org.springframework.web.filter.OncePerRequestFilter;
import vip.ilstudy.config.constant.Constant;
import vip.ilstudy.entity.LoginUserEntity;
import vip.ilstudy.service.LoginUserService;
import vip.ilstudy.service.TokenService;
import vip.ilstudy.utils.JwtTokenUtils;
import vip.ilstudy.utils.ResultUtils;
import vip.ilstudy.utils.ServletUtils;
import vip.ilstudy.utils.StringUtils;

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

        log.info("请求 URI {}", requestUri);
        for (String item : Constant.WRITE_PATH) {
            if (requestUri.startsWith(item)) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        for (String item : Constant.BLACK_PATH) {
            if (requestUri.startsWith(item)) {
                String jsonString = JSON.toJSONString(ResultUtils.error("无权限访问"));
                ServletUtils.renderString(response, jsonString);
                return;
            }
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

            if (loginUser == null) {
                String jsonString = JSON.toJSONString(ResultUtils.error(400, "登录已过期"));
                ServletUtils.renderString(response, jsonString);
                return;
            }

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            String jsonString = JSON.toJSONString(ResultUtils.error(Arrays.stream(e.getMessage().split(" ")).toList().getLast()));
            log.error(jsonString);
            ServletUtils.renderString(response, jsonString);
        }

    }
}
