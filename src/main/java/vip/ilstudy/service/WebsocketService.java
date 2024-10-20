package vip.ilstudy.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebsocketService {

    private final SimpMessagingTemplate template;

    public WebsocketService(SimpMessagingTemplate template) {
        this.template = template;
    }

    /**
     * 发送给所有接收者
     *
     * @param topic   接收主题
     * @param message 消息体
     */
    public void sendPublicMessage(String topic, String message) {
        template.convertAndSend(topic, message);
    }

    /**
     * 发送消息给指定接收者
     *
     * @param socketId 接收者Id
     * @param topic    接收主题
     * @param message  消息体
     */

    public void sendPrivateMessage(String socketId, String topic, String message) {
        template.convertAndSendToUser(socketId, topic, message);
    }

}
