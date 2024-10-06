package vip.ilstudy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.ilstudy.entity.LogEntity;

public interface LogService extends IService<LogEntity> {
    IPage<LogEntity> getLogListPage(Long pageNum, Long pageSize);

    Boolean insertLogEntity(LogEntity logEntity);
}
