package vip.ilstudy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.ilstudy.entity.BillEntity;


public interface BillService extends IService<BillEntity> {
    BillEntity getBillById(Long billId);

    Integer insertBill(BillEntity billEntity);

    IPage<BillEntity> getBillListPage(Long pageNum, Long pageSize);

    Boolean updateBillById(BillEntity billEntity);

    Boolean deleteBillById(Long billId);
}
