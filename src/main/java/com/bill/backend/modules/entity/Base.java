package com.bill.backend.modules.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.util.Date;

@Data
public class Base {

    private Date createTime;
    private Date updateTime;
    @TableLogic
    private Integer isDelete;
}
