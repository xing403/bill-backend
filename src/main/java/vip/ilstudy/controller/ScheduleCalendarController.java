package vip.ilstudy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vip.ilstudy.entity.ResultEntity;
import vip.ilstudy.entity.scheduleCalendar.ScheduleCalendarDTO;
import vip.ilstudy.entity.scheduleCalendar.ScheduleCalendarEntity;
import vip.ilstudy.service.ScheduleCalendarService;
import vip.ilstudy.utils.ResultUtils;

import java.util.List;

@RestController
@RequestMapping("schedule-calendar")
public class ScheduleCalendarController extends BaseController {

    @Autowired
    private ScheduleCalendarService scheduleCalendarService;


    @GetMapping("")
    public ResultEntity<List<ScheduleCalendarEntity>> getScheduleCalendarList() {
        List<ScheduleCalendarEntity> scheduleList = scheduleCalendarService.getScheduleList();
        return ResultUtils.success(scheduleList);
    }

    @GetMapping("group")
    public ResultEntity<List<ScheduleCalendarDTO>> getScheduleCalendarDTOList() {
        List<ScheduleCalendarDTO> scheduleCalendarDTOList = scheduleCalendarService.getScheduleCalendarDTOList();
        return ResultUtils.success(scheduleCalendarDTOList);
    }

    @PostMapping("")
    public ResultEntity<Boolean> insertScheduleCalendar(@RequestBody ScheduleCalendarEntity scheduleCalendarEntity) {
        Boolean b = scheduleCalendarService.insertScheduleCalendar(scheduleCalendarEntity);
        if (b) {
            return ResultUtils.success();
        }
        return ResultUtils.error("新增失败");
    }

    @PutMapping("")
    public ResultEntity<Boolean> updateScheduleCalendar(@RequestBody ScheduleCalendarEntity scheduleCalendarEntity) {
        Boolean b = scheduleCalendarService.updateScheduleCalendarByScheduleId(scheduleCalendarEntity);
        if (b) {
            return ResultUtils.success();
        }
        return ResultUtils.error("修改失败");
    }

    @PutMapping("{scheduleId}")
    public ResultEntity<Boolean> deleteScheduleCalendarByScheduleId(@PathVariable("scheduleId") Long scheduleId) {
        Boolean b = scheduleCalendarService.deleteScheduleCalendarByScheduleId(scheduleId);
        if (b) {
            return ResultUtils.success();
        }
        return ResultUtils.error("删除失败");
    }
}
