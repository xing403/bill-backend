package vip.ilstudy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;


import java.time.LocalDate;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("x_bill")
public class BillEntity extends BaseEntity {
    /**
     * 账单编号
     */
    @TableId(type = IdType.AUTO)
    private Long billId;

    /**
     * 账单标题
     */
    @NotBlank(message = "账单日期不能为空")
    private String billTitle;

    /**
     * 账单类型
     */
    @NotBlank(message = "账单日期不能为空")
    private String billType;

    /**
     * 账单金额
     */
    private Double billAmount;

    /**
     * 账单日期
     */
    @NotNull(message = "账单日期不能为 null")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime billTime;
}
