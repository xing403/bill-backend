package vip.ilstudy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vip.ilstudy.entity.scheduleCalendar.ScheduleCalendarDTO;
import vip.ilstudy.entity.scheduleCalendar.ScheduleCalendarEntity;
import vip.ilstudy.entity.scheduleCalendar.ScheduleOperationEntity;
import vip.ilstudy.entity.scheduleCalendar.ScheduleOperationGroup;
import vip.ilstudy.mapper.ScheduleCalendarMapper;
import vip.ilstudy.service.ScheduleCalendarService;
import vip.ilstudy.service.ScheduleOperationService;

import java.time.LocalDate;
import java.util.List;

@Service
public class ScheduleCalendarServiceImpl extends ServiceImpl<ScheduleCalendarMapper, ScheduleCalendarEntity> implements ScheduleCalendarService {

    @Autowired
    private ScheduleCalendarMapper scheduleCalendarMapper;
    @Autowired
    private ScheduleOperationService scheduleOperationService;

    @Override
    public Boolean insertScheduleCalendar(ScheduleCalendarEntity scheduleCalendarEntity) {
        return scheduleCalendarMapper.insert(scheduleCalendarEntity) > 0;
    }

    @Override
    public Boolean deleteScheduleCalendarByScheduleId(Long scheduleId) {
        return scheduleCalendarMapper.deleteById(scheduleId) > 0;
    }

    @Override
    public Boolean updateScheduleCalendarByScheduleId(ScheduleCalendarEntity scheduleCalendarEntity) {
        return scheduleCalendarMapper.updateById(scheduleCalendarEntity) > 0;
    }

    @Override
    public List<ScheduleCalendarEntity> getScheduleList() {
        return scheduleCalendarMapper.selectList(null);
    }

    @Override
    public List<ScheduleCalendarDTO> getScheduleCalendarDTOList() {
        List<ScheduleCalendarEntity> scheduleList = getScheduleList();
        return scheduleList.stream().map(scheduleCalendarEntity -> {
            List<ScheduleOperationEntity> scheduleOperationList = scheduleOperationService.getScheduleOperationList(scheduleCalendarEntity.getScheduleId());

            ScheduleOperationGroup scheduleOperationGroup = new ScheduleOperationGroup();
            scheduleOperationList.forEach(scheduleOperationEntity -> {
                switch (scheduleOperationEntity.getOperationStatus()) {
                    case "finish":
                        scheduleOperationGroup.getFinish().add(scheduleOperationEntity.getOperationDate());
                        break;
                    case "cancel":
                        scheduleOperationGroup.getCancel().add(scheduleOperationEntity.getOperationDate());
                        break;
                    case "delete":
                        scheduleOperationGroup.getDelete().add(scheduleOperationEntity.getOperationDate());
                        break;
                }
            });

            return new ScheduleCalendarDTO(scheduleCalendarEntity, scheduleOperationGroup);
        }).toList();
    }
}
