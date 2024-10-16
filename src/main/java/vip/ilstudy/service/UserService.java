package vip.ilstudy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.ilstudy.entity.LoginUserEntity;
import vip.ilstudy.entity.UserEntity;

public interface UserService extends IService<UserEntity> {

    Integer insertUser(LoginUserEntity userEntity);

    UserEntity getUserEntityByUsername(String username) throws Exception;
    UserEntity getUserEntityById(Long userId) throws Exception;

    Boolean updateUserLoginTimeByUsername(UserEntity userEntity);

    Boolean enableUserByUserId(Long userId) throws Exception;

    Boolean disableUserByUserId(Long userId) throws Exception;

    IPage<UserEntity> getUserList(Long pageNum, Long pageSize);

    Boolean deleteUserById(Long userId);
    Boolean updateUserByUserName(UserEntity userEntity);

    Boolean deleteUserByUsername(String username);
}
