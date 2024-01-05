package com.bill.backend.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import com.bill.backend.modules.entity.User;


@Service
public interface UserService extends IService<User> {

    /**
     * 注册用户
     */
    Boolean register(String username, String password);

    /**
     * 用户登录
     */
    User login(String username, String password);

    /**
     * 用户更新
     */
    Integer update(User user);

    /**
     * 删除用户
     */
    Integer delete(String username);

    /**
     * 用户列表
     *
     */
    IPage<User> list(String auth, Page<User> page);

    User getCurrentUser(String token);

    /**
     * 通过用户名获取用户
     */
    User getByUsername(String username);

    /**
     * 修改用户密码
     */
    Integer updatePassword(String username, String newPassword);

    /**
     * 判断用户是否存在
     */
    Boolean exist(String username);

}
