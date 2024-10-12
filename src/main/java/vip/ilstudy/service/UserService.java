package vip.ilstudy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import vip.ilstudy.entity.LoginUserEntity;
import vip.ilstudy.entity.UserEntity;

public interface UserService extends IService<UserEntity> {

    Integer insertUser(LoginUserEntity userEntity);

    UserEntity getUserEntityByUsername(String username) throws Exception;

    Boolean updateUserLoginTimeByUsername(UserEntity userEntity);
}
