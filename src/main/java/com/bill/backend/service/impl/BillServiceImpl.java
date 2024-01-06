package com.bill.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
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
        return billMapper.delete(new UpdateWrapper<Bill>().eq("id", id));
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
        billQueryWrapper.orderByAsc("data_time");
        return billMapper.selectPage(page, billQueryWrapper);
    }

    @Override
    public Bill getByIdAndUserId(Long id, Long userId) {
        return billMapper.selectOne(new QueryWrapper<Bill>().eq("id", id).eq("user_Id", userId));
    }

    @Override
    public Long getBillCountByUserId(Long userId, String dataTime) {
        return billMapper.selectCount(new QueryWrapper<Bill>().eq("user_Id", userId).like("data_time", dataTime));
    }

    @Override
    public Double getBillSumByUserIdAndDataTimeAndType(Long userId, String dataTime, Integer type) {
        Double billSumByUserIdAndDataTimeAndType = billMapper.getBillSumByUserIdAndDataTimeAndType(userId, type, '%' + dataTime + '%');
        return billSumByUserIdAndDataTimeAndType == null ? 0.0 : billSumByUserIdAndDataTimeAndType;
    }
    public List<Map<String, Object>> getIncomeAndExpenseByMonth(String dataTime, Long userId) {
        return billMapper.getIncomeAndExpenseByMonth(dataTime + "-__", userId);
    }

    @Override
    public List<Map<String, Object>> getIncomeAndExpenseByYear(String dataTime, Long userId) {
        return billMapper.getIncomeAndExpenseByYear(dataTime + "-__-__", userId);
    }
    public
    List<Map<String, Object>> getIncomeAndExpenseByDay(String dataTime, Long userId) {
        return billMapper.getIncomeAndExpenseByDay(dataTime + "-__-__", userId);
    }
}
