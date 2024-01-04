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
    public Integer updateBill(Bill bill, Long id) {
        UpdateWrapper<Bill> billUpdateWrapper = new UpdateWrapper<>();
        billUpdateWrapper.eq("user_Id", id)
                .eq("id", bill.getId());
        return billMapper.update(bill, billUpdateWrapper);

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
            billQueryWrapper.like("data_time", bill.getDataTime() + "-%");
        }
        return billMapper.selectPage(page, billQueryWrapper);
    }
}
