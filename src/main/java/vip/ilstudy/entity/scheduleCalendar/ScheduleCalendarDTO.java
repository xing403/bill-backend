package vip.ilstudy.entity.scheduleCalendar;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ScheduleCalendarDTO extends ScheduleCalendarEntity{
    ScheduleOperationGroup group;


    public ScheduleCalendarDTO(ScheduleCalendarEntity scheduleCalendarEntity, ScheduleOperationGroup group) {
        this.setScheduleId(scheduleCalendarEntity.getScheduleId());
        this.setScheduleTitle(scheduleCalendarEntity.getScheduleTitle());
        this.setScheduleModel(scheduleCalendarEntity.getScheduleModel());
        this.setScheduleRangeStart(scheduleCalendarEntity.getScheduleRangeStart());
        this.setScheduleRangeEnd(scheduleCalendarEntity.getScheduleRangeEnd());
        this.setScheduleDate(scheduleCalendarEntity.getScheduleDate());
        this.setScheduleCron(scheduleCalendarEntity.getScheduleCron());
        this.setGroup(group);
    }
}
