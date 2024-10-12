package vip.ilstudy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vip.ilstudy.entity.LoginUserEntity;
import vip.ilstudy.entity.UserEntity;
import vip.ilstudy.mapper.UserMapper;
import vip.ilstudy.service.UserService;
import vip.ilstudy.utils.SecurityUtils;
import vip.ilstudy.utils.UUIDUtils;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

    Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Override
    public Integer insertUser(LoginUserEntity loginUserEntity) {
        QueryWrapper<UserEntity> userEntityQueryWrapper = new QueryWrapper<>();
        userEntityQueryWrapper.eq("username", loginUserEntity.getUsername());
        UserEntity userEntity1 = userMapper.selectOne(userEntityQueryWrapper);
        if (userEntity1 == null) {
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(loginUserEntity.getUsername());
            userEntity.setPassword(SecurityUtils.encryptPassword(loginUserEntity.getPassword()));
            userEntity.setUuid(UUIDUtils.getUUID());
            log.info("新增用户名为 {}", userEntity.getUsername());
            return userMapper.insert(userEntity);
        }
        return 0;
    }

    @Override
    public UserEntity getUserEntityByUsername(String username) throws Exception {
        QueryWrapper<UserEntity> userEntityQueryWrapper = new QueryWrapper<>();
        userEntityQueryWrapper.eq("username", username);
        UserEntity userEntity = userMapper.selectOne(userEntityQueryWrapper);
        if (userEntity == null) {
            throw new Exception("用户名不存在" + username);
        }
        return userEntity;
    }

    @Override
    public Boolean updateUserLoginTimeByUsername(UserEntity userEntity) {
        UpdateWrapper<UserEntity> userEntityUpdateWrapper = new UpdateWrapper<>();
        userEntityUpdateWrapper.eq("username", userEntity.getUsername());
        return userMapper.update(userEntity, userEntityUpdateWrapper) > 0;
    }
}
