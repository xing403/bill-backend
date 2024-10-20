package vip.ilstudy.entity.dto;

import jakarta.websocket.Session;
import lombok.Data;

import java.util.Set;

@Data
public class SocketEntity {
    /**
     * 用户token
     */
    private String token;
    /**
     * websocket会话
     */
    private Session session;
    /**
     * 订阅主题
     */
    private Set<String> topics;
}
