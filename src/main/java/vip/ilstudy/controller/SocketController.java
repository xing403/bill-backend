package vip.ilstudy.controller;

import jakarta.websocket.server.ServerEndpoint;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class SocketController extends BaseController{


    @MessageMapping("/hello")
    @SendTo("/topic")
    public String hello(@Payload String message) {
        return "123";
    }
    //接收前端subscribe命令发送的
    @SubscribeMapping("/subscribe")
    public String subscribe() {
        return "456";
    }
    //接收前端send命令，但是单对单返回
    @MessageMapping("/test")
    @SendToUser("/queue/test")
    public String user(Principal principal, @Payload String message) {

        return "111";
    }
}
