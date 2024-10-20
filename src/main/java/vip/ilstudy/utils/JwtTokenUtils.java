package vip.ilstudy.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vip.ilstudy.config.constant.Constant;

import java.util.Arrays;
import java.util.List;


/**
 * Token 工具类
 */

public class JwtTokenUtils {

    /**
     * 获取请求头中的 token
     *
     * @param request
     * @return
     */
    public static String getToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if (request.getRequestURI().contains(Constant.WEBSOCKET_REQUEST_PATH_PREFIX)) { // websocket 请求
            List<String> paths = Arrays.stream(request.getRequestURI().split("/"))
                    .filter(s -> !s.isEmpty()).toList();

            token = paths.size() > 1 ? paths.getLast() : request.getParameter("token");
        }

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
