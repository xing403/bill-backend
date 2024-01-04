package com.bill.backend.modules.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("bill")
public class Bill extends Base implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String title;
    private Double amount;
    private Integer type;
    private String tags;
    private String detail;
    private String dataTime;
    private Long userId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
