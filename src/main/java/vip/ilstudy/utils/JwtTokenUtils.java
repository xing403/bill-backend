package vip.ilstudy.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vip.ilstudy.entity.LoginUserEntity;
import vip.ilstudy.service.TokenService;

import java.util.HashMap;
import java.util.Map;

/**
 * Token 工具类
 */

public class JwtTokenUtils {
    @Autowired
    private TokenService tokenService;

    /**
     * 获取请求头中的 token
     *
     * @param request
     * @return
     */
    public static String getToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    /**
     * 删除请求中 的 cookie token
     */
    public static void deleteCookieToken(HttpServletResponse response) {

    }


    /**
     * @param response
     * @param token
     */
    public static void setCookieToken(HttpServletResponse response, String token) {

    }
}
