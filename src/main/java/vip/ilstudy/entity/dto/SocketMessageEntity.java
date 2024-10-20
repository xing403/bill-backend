package vip.ilstudy.entity.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class SocketMessageEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 发送主题
     */
    @NotBlank(message = "主题不能为空")
    private String topic;

    private String from;

    private String to;
    /**
     * 消息内容
     */
    @NotBlank(message = "消息内容不能为空")
    private String message;

}
