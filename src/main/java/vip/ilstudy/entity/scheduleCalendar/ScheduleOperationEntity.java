package vip.ilstudy.entity.scheduleCalendar;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import vip.ilstudy.entity.BaseEntity;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("x_schedule_operation")
public class ScheduleOperationEntity extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long operationId;

    private Long scheduleId;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private LocalDate operationDate;

    private String operationStatus;
}
