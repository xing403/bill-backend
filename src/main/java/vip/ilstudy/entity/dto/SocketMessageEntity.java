package vip.ilstudy.entity.dto;

import com.alibaba.fastjson2.JSONObject;
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
    private String topic;

    private String from;

    private String to;
    /**
     * 消息内容
     */
    private String message;

    // 手动映射
    public static SocketMessageEntity fromJson(String json) {
        JSONObject parse = JSONObject.parse(json);
        SocketMessageEntity user = new SocketMessageEntity();
        user.setTopic(parse.getString("topic"));
        user.setMessage(parse.getString("message"));
        return user;
    }
}
