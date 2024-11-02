package vip.ilstudy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vip.ilstudy.entity.ResultEntity;
import vip.ilstudy.entity.scheduleCalendar.ScheduleOperationEntity;
import vip.ilstudy.entity.scheduleCalendar.ScheduleOperationGroup;
import vip.ilstudy.service.ScheduleOperationService;
import vip.ilstudy.utils.ResultUtils;

@RestController
@RequestMapping("schedule-operation")
public class ScheduleOperationController extends BaseController {
    @Autowired
    private ScheduleOperationService scheduleOperationService;


    @PostMapping("")
    public ResultEntity<Boolean> insertOrUpdateScheduleOperation(@RequestBody ScheduleOperationEntity scheduleOperationEntity) {
        Boolean b = scheduleOperationService.insertOrUpdateScheduleOperation(scheduleOperationEntity);
        if (b) {
            return ResultUtils.success();
        }
        return ResultUtils.error("操作失败");
    }

}
