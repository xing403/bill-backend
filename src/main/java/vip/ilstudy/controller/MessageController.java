package vip.ilstudy.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.ilstudy.entity.ResultEntity;
import vip.ilstudy.entity.dto.SocketMessageEntity;
import vip.ilstudy.service.WebsocketService;
import vip.ilstudy.utils.ResultUtils;

@RestController
@RequestMapping("message")
public class MessageController extends BaseController {

    @Autowired
    private WebsocketService socketService;

    @PostMapping("/public")
    public ResultEntity<Boolean> sendPublicMessage(@Valid @RequestBody SocketMessageEntity message) {
        socketService.sendPublicMessage(message.getTopic(), message.getMessage());
        return ResultUtils.success();
    }

    @PostMapping("/private")
    public ResultEntity<Boolean> sendPrivateMessage(@Valid @RequestBody SocketMessageEntity message) {
        if (message.getTo() == null) {
            return ResultUtils.error("接收者不存在");
        }
        socketService.sendPrivateMessage(message.getTo(), message.getTopic(), message.getMessage());
        return ResultUtils.success();
    }
}
