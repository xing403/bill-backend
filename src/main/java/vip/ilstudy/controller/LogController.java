package vip.ilstudy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vip.ilstudy.entity.LogEntity;
import vip.ilstudy.entity.ResultEntity;
import vip.ilstudy.entity.TablePageEntity;
import vip.ilstudy.service.LogService;
import vip.ilstudy.utils.ResultUtils;

@RestController
@RequestMapping("/log")
public class LogController extends BaseController {

    @Autowired
    private LogService logService;

    @GetMapping("list")
    public ResultEntity<TablePageEntity<LogEntity>> getLogList(@RequestParam("pageNum") Long pageNum, @RequestParam("pageSize") Long pageSize) {
        IPage<LogEntity> logListPage = logService.getLogListPage(pageNum, pageSize);
        return ResultUtils.success(new TablePageEntity<>(logListPage));
    }
}
