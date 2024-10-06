package vip.ilstudy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import vip.ilstudy.entity.LogEntity;

@Mapper
public interface LogMapper extends BaseMapper<LogEntity> {
}
