package com.bill.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bill.backend.modules.entity.Bill;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface BillMapper extends BaseMapper<Bill> {
    @Select("select sum(amount) from bill where user_id = #{userId} and type = #{type} and data_time like #{dataTime} and is_delete = 0")
    Double getBillSumByUserIdAndDataTimeAndType(Long userId, Integer type, String dataTime);

    @Select("SELECT data_time, " +
            "SUM(CASE WHEN `type` = 1 THEN `amount` ELSE 0 END) AS income, " +
            "SUM(CASE WHEN `type` = 2 THEN `amount` ELSE 0 END) AS expense " +
            "FROM `bill` WHERE `data_time` LIKE #{dataTime} AND `user_id` = #{userId} AND `is_delete` = 0 GROUP BY `data_time` ORDER BY `data_time` ASC")
    List<Map<String, Object>> getIncomeAndExpenseByMonth(String dataTime, Long userId);

    @Select("SELECT MONTH(data_time) AS `month`, " +
            "SUM(CASE WHEN `type` = 1 THEN `amount` ELSE 0 END) AS income, " +
            "SUM(CASE WHEN `type` = 2 THEN `amount` ELSE 0 END) AS expense " +
            "FROM bill WHERE data_time LIKE #{dataTime} AND `user_id` = #{userId} AND `is_delete` = 0 GROUP BY MONTH(data_time) ORDER BY `month` ASC")
    List<Map<String, Object>> getIncomeAndExpenseByYear(String dataTime, Long userId);
    @Select("SELECT data_time AS `day`, " +
            "SUM(CASE WHEN `type` = 1 THEN `amount` ELSE 0 END) AS income, " +
            "SUM(CASE WHEN `type` = 2 THEN `amount` ELSE 0 END) AS expense " +
            "FROM bill WHERE data_time LIKE #{dataTime} AND `user_id` = #{userId} AND `is_delete` = 0 GROUP BY data_time ORDER BY `day` ASC")
    List<Map<String, Object>> getIncomeAndExpenseByDay(String dataTime, Long userId);
}
