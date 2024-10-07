package vip.ilstudy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vip.ilstudy.entity.BillEntity;
import vip.ilstudy.entity.dto.BillStatisticEntity;
import vip.ilstudy.mapper.BillMapper;
import vip.ilstudy.service.BillService;
import vip.ilstudy.utils.ServletUtils;

import java.util.List;
import java.util.Objects;

@Service
public class BillServiceImpl extends ServiceImpl<BillMapper, BillEntity> implements BillService {

    Logger log = LoggerFactory.getLogger(BillServiceImpl.class);

    @Autowired
    private BillMapper billMapper;

    @Override
    public BillEntity getBillById(Long billId) {
        return billMapper.selectById(billId);
    }

    @Override
    public Integer insertBill(BillEntity billEntity) {
        log.info("新增账单，{}", billEntity.getBillTitle());
        return billMapper.insert(billEntity);
    }

    @Override
    public IPage<BillEntity> getBillListPage(Long pageNum, Long pageSize) {
        IPage<BillEntity> entityPage = new Page<>(pageNum, pageSize);
        QueryWrapper<BillEntity> billEntityQueryWrapper = new QueryWrapper<>();
        return billMapper.selectPage(entityPage, billEntityQueryWrapper);
    }

    @Override
    public Boolean updateBillById(BillEntity billEntity) {
        return billMapper.updateById(billEntity) > 0;
    }

    @Override
    public Boolean deleteBillById(Long billId) {
        return billMapper.deleteById(billId) > 0;
    }

    @Override
    public List<BillStatisticEntity> getBillStatistic() {
        String username = Objects.requireNonNull(ServletUtils.getLoginUser()).getUsername();
        return billMapper.getBillStatistic(username);
    }
}
