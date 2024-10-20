package vip.ilstudy.handler.socket;

import com.sun.security.auth.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

import org.springframework.web.socket.WebSocketHandler;
import vip.ilstudy.entity.LoginUserEntity;
import vip.ilstudy.utils.ServletUtils;

/**
 * WebSocket 握手处理器
 */
@Slf4j
@Component
public class WebSocketHandshakeHandler extends DefaultHandshakeHandler {

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        LoginUserEntity loginUser = ServletUtils.getLoginUser();
        if (loginUser == null) {
            return null;
        }
        return new UserPrincipal(loginUser.getUsername());
    }


}
