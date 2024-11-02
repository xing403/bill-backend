package vip.ilstudy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import vip.ilstudy.entity.scheduleCalendar.ScheduleCalendarDTO;
import vip.ilstudy.entity.scheduleCalendar.ScheduleCalendarEntity;

import java.util.List;


public interface ScheduleCalendarService extends IService<ScheduleCalendarEntity> {


    Boolean insertScheduleCalendar(ScheduleCalendarEntity scheduleCalendarEntity);

    Boolean deleteScheduleCalendarByScheduleId(Long scheduleId);

    Boolean updateScheduleCalendarByScheduleId(ScheduleCalendarEntity scheduleCalendarEntity);

    List<ScheduleCalendarEntity> getScheduleList();

    List<ScheduleCalendarDTO> getScheduleCalendarDTOList();

}
