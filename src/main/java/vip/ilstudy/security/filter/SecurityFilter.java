package vip.ilstudy.security.filter;

import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import vip.ilstudy.entity.LoginUserEntity;
import vip.ilstudy.service.TokenService;
import vip.ilstudy.utils.JwtTokenUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    Logger log = LoggerFactory.getLogger(SecurityFilter.class);

    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestUri = request.getRequestURI();
        log.info("requestUri={}", requestUri);

        // 白名单过滤
        if (requestUri.contains("login") || requestUri.contains("register")) {
            filterChain.doFilter(request, response);
            return;
        }
        // 黑名单过滤

        // 验证token是否有效
        String token = JwtTokenUtils.getToken(request);
        if (StringUtils.isEmpty(token)) {
            response.sendError(500, "未登录");
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthenticationToken(token, requestUri);
        if (authentication == null) {
            filterChain.doFilter(request, response);
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(String token, String requestUri) {
        try {
            Claims claims = tokenService.getClaimsFromToken(token);
            if (claims == null) {
                log.error("token中过期，claims为空！ requestUri={}", requestUri);
                return null;
            }
            String username = claims.getSubject();
            String userId = claims.getId();
            if (StringUtils.isEmpty(username) || StringUtils.isEmpty(userId)) {
                log.error("token中username或userId为空！ requestUri={}", requestUri);
                return null;
            }
            // 获取角色
            List<GrantedAuthority> authorities = new ArrayList<>();
            String authority = claims.get("authorities").toString();
            if (!StringUtils.isEmpty(authority)) {
                @SuppressWarnings("unchecked")
                List<Map<String, String>> authorityMap = JSONObject.parseObject(authority, List.class);
                for (Map<String, String> role : authorityMap) {
                    if (!role.isEmpty()) {
                        authorities.add(new SimpleGrantedAuthority(role.get("authority")));
                    }
                }
            }
            LoginUserEntity loginUserEntity = new LoginUserEntity();
            loginUserEntity.setUsername(username);
            loginUserEntity.setPassword(claims.get("password").toString());

            return new UsernamePasswordAuthenticationToken(loginUserEntity, userId, authorities);
        } catch (ExpiredJwtException e) {
            logger.error("Token已过期: {} " + e);
            /* throw new TokenException("Token已过期"); */
        } catch (UnsupportedJwtException e) {
            logger.error("requestURI=" + requestUri + ",token=" + token + ",Token格式错误: {} " + e);
            /* throw new TokenException("Token格式错误"); */
        } catch (MalformedJwtException e) {
            logger.error("requestURI=" + requestUri + ",token=" + token + ",Token没有被正确构造: {} " + e);
            /* throw new TokenException("Token没有被正确构造"); */
        } catch (SignatureException e) {
            logger.error("requestURI=" + requestUri + ",token=" + token + ",签名失败: {} " + e);
            /* throw new TokenException("签名失败"); */
        } catch (IllegalArgumentException e) {
            logger.error("requestURI=" + requestUri + ",token=" + token + ",非法参数异常: {} " + e);
            /* throw new TokenException("非法参数异常"); */
        }
        return null;
    }
}
