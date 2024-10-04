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
import vip.ilstudy.entity.LoginUserEntity;
import vip.ilstudy.mapper.BillMapper;
import vip.ilstudy.service.BillService;
import vip.ilstudy.utils.ServletUtils;

@Service
public class BillServiceImpl extends ServiceImpl<BillMapper, BillEntity> implements BillService {

    Logger log = LoggerFactory.getLogger(BillServiceImpl.class);

    @Autowired
    private BillMapper billMapper;

    @Override
    public BillEntity getBillById(Long billId) {
        QueryWrapper<BillEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("bill_id", billId);
        queryWrapper.eq("create_by", "admin");
        return billMapper.selectOne(queryWrapper);
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
    public Boolean deleteBillById(Long billId) {
        QueryWrapper<BillEntity> billEntityQueryWrapper = new QueryWrapper<>();
        billEntityQueryWrapper.eq("bill_id", billId);
        LoginUserEntity loginUser = ServletUtils.getLoginUser();
        if (loginUser != null) {
            if (!loginUser.isAdmin()) {
                billEntityQueryWrapper.eq("create_by", loginUser.getUsername());
            }
            log.info("{} 删除账单，id:{}", loginUser.getUsername(), billId);
            return billMapper.delete(billEntityQueryWrapper) > 0;
        }
        return false;

    }
}
