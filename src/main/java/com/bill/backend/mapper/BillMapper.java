package com.bill.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bill.backend.modules.entity.Bill;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface BillMapper extends BaseMapper<Bill> {
    @Select("select sum(amount) from bill where user_id = #{userId} and type = #{type} and data_time like #{dataTime} and is_delete = 0")
    Double getBillSumByUserIdAndDataTimeAndType(Long userId, Integer type, String dataTime);
}
