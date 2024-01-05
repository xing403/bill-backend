package com.bill.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bill.backend.modules.entity.Bill;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface BillMapper extends BaseMapper<Bill> {

    @Select("SELECT data_time, " +
            "SUM(CASE WHEN `type` = 1 THEN `amount` ELSE 0 END) AS income, " +
            "SUM(CASE WHEN `TYPE` = 2 THEN `amount` ELSE 0 END) AS expense, " +
            "SUM(CASE WHEN `TYPE` = 1 THEN `amount` ELSE -1 * amount END) AS daySum " +
            "FROM `bill` WHERE `data_time` LIKE #{dataTime} AND `user_id` = #{userId} AND `is_delete` = 0 GROUP BY `data_time`")
    List<Map<String, Object>> getIncomeAndExpenseByMonth(String dataTime, Long userId);
}
