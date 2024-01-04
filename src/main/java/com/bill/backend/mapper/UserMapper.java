package com.bill.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bill.backend.modules.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
