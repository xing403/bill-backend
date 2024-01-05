package com.bill.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bill.backend.mapper.BillMapper;
import com.bill.backend.modules.entity.Bill;
import com.bill.backend.service.BillService;
import com.bill.backend.utils.RedisCacheUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
public class BillServiceImpl extends ServiceImpl<BillMapper, Bill> implements BillService {
    @Resource
    private BillMapper billMapper;
    @Resource
    private RedisCacheUtils redisCacheUtils;
    @Resource
    private HttpServletRequest request;

    @Override
    public Boolean addBill(Bill bill) {
        return this.save(bill);
    }

    @Override
    public Integer updateBill(Bill bill) {
        return billMapper.updateById(bill);
    }

    @Override
    public Integer deleteBill(Long id) {
        return null;
    }

    @Override
    public IPage<Bill> findByUserIdAndDataTime(Page<Bill> page, Bill bill) {
        QueryWrapper<Bill> billQueryWrapper = new QueryWrapper<>();
        if (bill.getUserId() != null) {
            billQueryWrapper.eq("user_Id", bill.getUserId());
        }
        if (bill.getDataTime() != null) {
            billQueryWrapper.like("data_time", bill.getDataTime());
        }
        return billMapper.selectPage(page, billQueryWrapper);
    }

    @Override
    public Bill getByIdAndUserId(Long id, Long userId) {
        return billMapper.selectOne(new QueryWrapper<Bill>().eq("id", id).eq("user_Id", userId));
    }

    @Override
    public List<Map<String, Object>> getIncomeAndExpenseByMonth(String dataTime, Long userId) {
        return billMapper.getIncomeAndExpenseByMonth(dataTime + "-%", userId);
    }
}
