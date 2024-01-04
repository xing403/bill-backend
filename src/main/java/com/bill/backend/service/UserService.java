package com.bill.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import com.bill.backend.modules.entity.User;

import java.util.List;

@Service
public interface UserService extends IService<User> {

    /**
     * 注册用户
     *
     * @param username 用户名
     * @param password 用户密码
     * @return 注册结果
     */
    Boolean register(String username, String password);

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录的用户
     */
    User login(String username, String password);

    /**
     * 用户更新
     *
     * @param user
     * @return
     */
    Integer update(User user);

    /**
     * 删除用户
     *
     * @param username
     * @return
     */
    Integer delete(String username);

    /**
     * 用户列表
     *
     * @param auth
     * @return
     */
    List<User> list(String auth);

    User getCurrentUser(String token);

    /**
     * 通过用户名获取用户
     *
     * @param username 用户名
     * @return 返回用户
     */
    User getByUsername(String username);

    /**
     * 修改用户密码
     *
     * @param username 用户名
     * @param newPassword 新密码
     * @return 返回结果
     */
    Integer updatePassword(String username, String newPassword);

    /**
     * 判断用户是否存在
     *
     * @param username 用户名
     * @return 返回结果
     */
    Boolean exist(String username);

}
