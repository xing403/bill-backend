package vip.ilstudy.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import vip.ilstudy.entity.dto.SocketMessageEntity;

@Controller
public class SocketController extends BaseController {

    @MessageMapping("/message")
    @SendTo("/topic/message")
    public SocketMessageEntity sentMessage(final SocketMessageEntity message) {
        return message;
    }

    @MessageMapping("/private")
    @SendToUser("/topic/private")
    public SocketMessageEntity sendPrivateMessage(final SocketMessageEntity message) {
        return message;
    }
}
