package vip.ilstudy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vip.ilstudy.entity.BillEntity;
import vip.ilstudy.mapper.BillMapper;
import vip.ilstudy.service.BillService;

@Service
public class BillServiceImpl extends ServiceImpl<BillMapper, BillEntity> implements BillService {

    @Autowired
    private BillMapper billMapper;

    @Override
    public BillEntity getBillById(Long billId) {
        return billMapper.selectById(billId);
    }

    @Override
    public Integer insertBill(BillEntity billEntity) {
        return billMapper.insert(billEntity);
    }

    @Override
    public IPage<BillEntity> getBillListPage(Long pageNum, Long pageSize) {
        IPage<BillEntity> entityPage = new Page<>(pageNum, pageSize);
        QueryWrapper<BillEntity> billEntityQueryWrapper = new QueryWrapper<>();
        return billMapper.selectPage(entityPage, billEntityQueryWrapper);
    }
}
