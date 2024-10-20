package vip.ilstudy.handler.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import vip.ilstudy.handler.security.filter.SecurityFilter;
import vip.ilstudy.service.TokenService;
import java.util.Map;

/**
 * WebSocket 拦截器
 */
@Component
public class WebSocketInterceptor implements HandshakeInterceptor {


    Logger log = LoggerFactory.getLogger(SecurityFilter.class);

    @Autowired
    private TokenService tokenService;

    /**
     * 在握手进行拦截
     *
     * @param request
     * @param response
     * @param handler
     * @param map
     * @return
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler handler, Map<String, Object> map) {
        // 当前已经使用 spring security 认证, 无需再次认证
        return true;
    }

    /**
     * 握手之后拦截
     *
     * @param request
     * @param response
     * @param wsHandler
     * @param exception
     */
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
    }
}