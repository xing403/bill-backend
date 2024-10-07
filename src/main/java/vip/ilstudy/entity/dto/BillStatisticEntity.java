package vip.ilstudy.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

/**
 * 账单统计实体
 */
@Data
public class BillStatisticEntity {

    /**
     * 账单日期
     */
    @NotNull(message = "账单日期不能为 null")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private LocalDate billTime;

    /**
     * 收入
     */
    private Double billIncome;
    /**
     * 支出
     */
    private Double billExpend;

    /**
     * 收入计数
     */
    private Integer billIncomeCount;
    /**
     * 支出计数
     */
    private Integer billExpendCount;

}
