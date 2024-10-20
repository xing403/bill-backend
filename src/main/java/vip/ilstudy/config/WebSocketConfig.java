package vip.ilstudy.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import vip.ilstudy.config.constant.Constant;
import vip.ilstudy.handler.socket.WebSocketHandshakeHandler;
import vip.ilstudy.handler.socket.WebSocketInterceptor;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(final MessageBrokerRegistry registry) {
        // 消息入口 默认为 /topic
        registry.enableSimpleBroker("/topic");
    }

    @Override
    public void registerStompEndpoints(final StompEndpointRegistry registry) {
        registry.addEndpoint(
                        Constant.WEBSOCKET_REQUEST_PATH_PREFIX,
                        Constant.WEBSOCKET_REQUEST_PATH_PREFIX + "/{token}"
                )
                .setAllowedOrigins("*")
                .setHandshakeHandler(new WebSocketHandshakeHandler())
                .addInterceptors(new WebSocketInterceptor());
    }
}
