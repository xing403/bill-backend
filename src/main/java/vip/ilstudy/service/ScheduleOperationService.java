package vip.ilstudy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import vip.ilstudy.entity.scheduleCalendar.ScheduleOperationEntity;
import vip.ilstudy.entity.scheduleCalendar.ScheduleOperationGroup;

import java.time.LocalDate;
import java.util.List;


public interface ScheduleOperationService extends IService<ScheduleOperationEntity> {

    List<ScheduleOperationEntity> getScheduleOperationList();
    List<ScheduleOperationEntity> getScheduleOperationList(Long ScheduleId);

    Boolean insertOrUpdateScheduleOperation(ScheduleOperationEntity scheduleOperationEntity);

    Boolean insertScheduleOperation(ScheduleOperationEntity scheduleOperationEntity);

    Boolean updateScheduleOperation(ScheduleOperationEntity scheduleOperationEntity);

    Boolean deleteScheduleOperationById(Long operationId);

    Boolean deleteScheduleOperationByScheduleIdAndOperationDate(Long scheduleId, LocalDate operationDate);
    ScheduleOperationEntity getScheduleOperationByScheduleIdAndOperationDate(Long scheduleId, LocalDate operationDate);

}
