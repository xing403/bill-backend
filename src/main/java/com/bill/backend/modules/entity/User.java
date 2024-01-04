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
@TableName("user")
public class User extends Base implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String username;
    private String password;
    private String avatar;
    private String phone;
    private String email;
    private String gender;
    private String auth;
    private Integer status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
