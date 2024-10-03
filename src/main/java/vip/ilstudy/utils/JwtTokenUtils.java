package vip.ilstudy.utils;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import vip.ilstudy.config.Constant;
import vip.ilstudy.service.TokenService;


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
        String token = request.getHeader("Authorization");
        if (StringUtils.isNotEmpty(token) && token.startsWith(Constant.TOKEN_PREFIX)) {
            token = token.replace(Constant.TOKEN_PREFIX, "");
        }
        return token;
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
