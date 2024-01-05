package com.bill.backend.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bill.backend.modules.entity.Bill;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface BillService extends IService<Bill> {
    Boolean addBill(Bill bill);

    Integer updateBill(Bill bill);

    Integer deleteBill(Long id);

    IPage<Bill> findByUserIdAndDataTime(Page<Bill> page, Bill bill);

    Bill getByIdAndUserId(Long id, Long userId);

    List<Map<String, Object>> getIncomeAndExpenseByMonth(String dataTime, Long userId);
    List<Map<String, Object>> getIncomeAndExpenseByYear(String dataTime, Long userId);

}
