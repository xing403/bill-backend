package vip.ilstudy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
import vip.ilstudy.utils.ServletUtils;
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
    public UserEntity getUserEntityById(Long userId) throws Exception {
        UserEntity userEntity = userMapper.selectById(userId);
        if (userEntity == null) {
            throw new Exception("用户 " + userId + "不存在");
        }
        return userEntity;
    }

    @Override
    public Boolean updateUserLoginTimeByUsername(UserEntity userEntity) {
        UpdateWrapper<UserEntity> userEntityUpdateWrapper = new UpdateWrapper<>();
        userEntityUpdateWrapper.eq("username", userEntity.getUsername());
        userEntityUpdateWrapper.set("login_time", userEntity.getLoginTime());
        return userMapper.update(null, userEntityUpdateWrapper) > 0;
    }

    @Override
    public Boolean enableUserByUserId(Long userId) throws Exception {
        LoginUserEntity loginUser = ServletUtils.getLoginUser();
        assert loginUser != null;
        if (loginUser.getUserEntity().getId().equals(userId)) {
            log.error("不能启动自己");
            throw new Exception("不能启用自己");
        }
        if(!loginUser.isAdmin()){
            log.error("无权限");
            throw new Exception("无权限");
        }
        UpdateWrapper<UserEntity> userEntityUpdateWrapper = new UpdateWrapper<>();
        userEntityUpdateWrapper.eq("id", userId);
        userEntityUpdateWrapper.set("locked", "0");
        return userMapper.update(userEntityUpdateWrapper) > 0;
    }

    @Override
    public Boolean disableUserByUserId(Long userId) throws Exception {
        LoginUserEntity loginUser = ServletUtils.getLoginUser();
        UserEntity userEntity = loginUser.getUserEntity();
        assert userEntity != null;
        if (userEntity.getId().equals(userId)) {
            log.error("不能停用自己");
            throw new Exception("不能停用自己");
        }
        if(!loginUser.isAdmin()){
            log.error("无权限");
            throw new Exception("无权限");
        }
        UpdateWrapper<UserEntity> userEntityUpdateWrapper = new UpdateWrapper<>();
        userEntityUpdateWrapper.eq("id", userId);
        userEntityUpdateWrapper.set("locked", "1");
        return userMapper.update(userEntityUpdateWrapper) > 0;
    }

    @Override
    public IPage<UserEntity> getUserList(Long pageNum, Long pageSize) {
        Page<UserEntity> userEntityPage = new Page<>(pageNum, pageSize);
        return userMapper.selectPage(userEntityPage, null);
    }

    @Override
    public Boolean deleteUserById(Long userId) {
        return userMapper.deleteById(userId) > 0;
    }

    @Override
    public Boolean updateUserByUserName(UserEntity userEntity) {
        UpdateWrapper<UserEntity> userEntityUpdateWrapper = new UpdateWrapper<>();
        userEntityUpdateWrapper.eq("username", userEntity.getUsername());
        return userMapper.update(userEntity, userEntityUpdateWrapper) > 0;
    }

    @Override
    public Boolean deleteUserByUsername(String username) {
        QueryWrapper<UserEntity> userEntityQueryWrapper = new QueryWrapper<>();
        userEntityQueryWrapper.eq("username", username);
        return userMapper.delete(userEntityQueryWrapper) > 0;
    }
}
