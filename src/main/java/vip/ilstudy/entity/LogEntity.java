package vip.ilstudy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("x_log")
public class LogEntity extends BaseEntity {

    /**
     * 日志ID
     */
    @TableId(type = IdType.AUTO)
    private String logId;
    /**
     * 日志标题
     */
    private String logTitle;
    /**
     * 日志类型
     */
    private String logType;
    /**
     * 执行方法
     */
    private String method;
    /**
     * 请求方式
     */
    private String requestMethod;
}
