package vip.ilstudy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import vip.ilstudy.entity.BillEntity;
import vip.ilstudy.entity.dto.BillStatisticEntity;

import java.util.List;

@Mapper
public interface BillMapper extends BaseMapper<BillEntity> {

    List<BillStatisticEntity> getBillStatistic(String username);
}
