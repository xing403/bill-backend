package vip.ilstudy.entity.scheduleCalendar;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class ScheduleOperationGroup {
    private List<LocalDate> finish = new ArrayList<>();
    private List<LocalDate> cancel = new ArrayList<>();
    private List<LocalDate> delete = new ArrayList<>();
}
