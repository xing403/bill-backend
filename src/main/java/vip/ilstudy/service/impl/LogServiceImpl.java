package vip.ilstudy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vip.ilstudy.entity.LogEntity;
import vip.ilstudy.mapper.LogMapper;
import vip.ilstudy.service.LogService;

@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, LogEntity> implements LogService {

    @Autowired
    private LogMapper logMapper;

    @Override
    public IPage<LogEntity> getLogListPage(Long pageNum, Long pageSize) {
        IPage<LogEntity> entityPage = new Page<>(pageNum, pageSize);
        QueryWrapper<LogEntity> billEntityQueryWrapper = new QueryWrapper<>();
        return logMapper.selectPage(entityPage, billEntityQueryWrapper);
    }

    @Override
    public Boolean insertLogEntity(LogEntity logEntity) {
        return logMapper.insert(logEntity) > 0;
    }
}
