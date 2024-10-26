package vip.ilstudy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("x_permission")
public class PermissionEntity extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private String permissionId;

    private String permissionName;

    private String permissionType;

}
