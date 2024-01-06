package com.bill.backend.modules.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@TableName("share")
@Data
public class Share extends Base implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String k;
    private String v;
    private String type;
    private Integer valid;
    private Long userId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
