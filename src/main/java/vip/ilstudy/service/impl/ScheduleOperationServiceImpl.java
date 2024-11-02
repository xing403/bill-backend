package vip.ilstudy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vip.ilstudy.entity.scheduleCalendar.ScheduleOperationEntity;
import vip.ilstudy.mapper.ScheduleOperationMapper;
import vip.ilstudy.service.ScheduleOperationService;
import vip.ilstudy.utils.StringUtils;

import java.time.LocalDate;
import java.util.List;

@Service
public class ScheduleOperationServiceImpl extends ServiceImpl<ScheduleOperationMapper, ScheduleOperationEntity> implements ScheduleOperationService {

    @Autowired
    private ScheduleOperationMapper scheduleOperationMapper;

    @Override
    public List<ScheduleOperationEntity> getScheduleOperationList() {
        return scheduleOperationMapper.selectList(null);
    }

    @Override
    public List<ScheduleOperationEntity> getScheduleOperationList(Long ScheduleId) {
        QueryWrapper<ScheduleOperationEntity> scheduleOperationEntityQueryWrapper = new QueryWrapper<>();
        scheduleOperationEntityQueryWrapper.eq("schedule_id", ScheduleId);
        return scheduleOperationMapper.selectList(scheduleOperationEntityQueryWrapper);
    }

    @Override
    public Boolean insertOrUpdateScheduleOperation(ScheduleOperationEntity scheduleOperationEntity) {
        ScheduleOperationEntity scheduleOperationByScheduleIdAndOperationDate = getScheduleOperationByScheduleIdAndOperationDate(scheduleOperationEntity.getScheduleId(), scheduleOperationEntity.getOperationDate());
        if (StringUtils.isNotNull(scheduleOperationByScheduleIdAndOperationDate)) {
            scheduleOperationEntity.setOperationId(scheduleOperationByScheduleIdAndOperationDate.getOperationId());
            return scheduleOperationMapper.updateById(scheduleOperationEntity) > 0;
        } else {
            return scheduleOperationMapper.insert(scheduleOperationEntity) > 0;
        }
    }

    @Override
    public Boolean insertScheduleOperation(ScheduleOperationEntity scheduleOperationEntity) {
        return scheduleOperationMapper.insert(scheduleOperationEntity) > 0;
    }

    @Override
    public Boolean updateScheduleOperation(ScheduleOperationEntity scheduleOperationEntity) {
        return null;
    }

    @Override
    public Boolean deleteScheduleOperationById(Long operationId) {
        return null;
    }

    @Override
    public Boolean deleteScheduleOperationByScheduleIdAndOperationDate(Long scheduleId, LocalDate operationDate) {
        return null;
    }

    @Override
    public ScheduleOperationEntity getScheduleOperationByScheduleIdAndOperationDate(Long scheduleId, LocalDate operationDate) {
        QueryWrapper<ScheduleOperationEntity> scheduleOperationEntityQueryWrapper = new QueryWrapper<>();
        scheduleOperationEntityQueryWrapper.eq("schedule_id", scheduleId).eq("operation_date", operationDate);
        return scheduleOperationMapper.selectOne(scheduleOperationEntityQueryWrapper);
    }
}
