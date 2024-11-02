package vip.ilstudy.entity.scheduleCalendar;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import vip.ilstudy.entity.BaseEntity;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("x_schedule_calendar")
public class ScheduleCalendarEntity extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long scheduleId;

    private String scheduleTitle;

    private String scheduleModel;

    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private LocalDate scheduleRangeStart;

    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private LocalDate scheduleRangeEnd;

    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private LocalDate scheduleDate;

    private String scheduleCron;

}
