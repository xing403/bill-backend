package com.bill.backend.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bill.backend.service.UserService;
import com.bill.backend.utils.FileUtils;
import com.bill.backend.utils.RedisCacheUtils;
import com.bill.backend.utils.UserUtils;
import org.springframework.stereotype.Service;
import com.bill.backend.mapper.UserMapper;
import com.bill.backend.modules.constant.ErrorCode;
import com.bill.backend.modules.entity.User;
import com.bill.backend.modules.exception.BusinessException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private RedisCacheUtils redisCacheUtils;
    @Resource
    private HttpServletRequest request;

    @Override
    public Boolean register(String username, String password) {

        String encryptPassword = UserUtils.encodePassword(password);
        // 3. 插入数据
        User user = new User();
        user.setUsername(username);
        user.setPassword(encryptPassword);
        boolean saveResult = this.save(user);
        if (!saveResult) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "注册失败，数据库错误");
        }
        return true;
    }

    @Override
    public User login(String username, String password) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", username)
                .eq("password", UserUtils.encodePassword(password));
        return userMapper.selectOne(userQueryWrapper);
    }

    @Override
    public Integer update(User user) {
        if (!this.exist(user.getUsername()))
            return 0;

        if (user.getAvatar() != null && !user.getAvatar().isEmpty())
            user.setAvatar(FileUtils.getPathAfterDomainOrPort(user.getAvatar()));

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.setEntity(user);
        return userMapper.updateById(user);
    }

    @Override
    public Integer delete(String username) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", username);
        return userMapper.delete(userQueryWrapper);
    }


    @Override
    public List<User> list(String auth) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        ArrayList<String> auths = new ArrayList<>();
        auths.add("admin");
        auths.add("user");
        if (auth.equals("root")) {
            auths.add("root");
        }
        userQueryWrapper.in("auth", auths);

        List<User> users = userMapper.selectList(userQueryWrapper);

        users.forEach(user -> {
            if (user.getAvatar() != null && !user.getAvatar().isEmpty()) {
                user.setAvatar(FileUtils.fileCompletePath(request, "", user.getAvatar()));
            }else{
                user.setAvatar("");
            }
        });
        return users;
    }

    @Override
    public User getCurrentUser(String token) {

        User user = JSON.parseObject(JSON.toJSONString(redisCacheUtils.getCache(token)), User.class);
        if (user == null){
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR, "token已过期");
        }
        return user;
    }


    @Override
    public User getByUsername(String username) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", username);
        User user = userMapper.selectOne(userQueryWrapper);
        if (user != null) {
            if (user.getAvatar() != null && !user.getAvatar().isEmpty()) {
                user.setAvatar(FileUtils.fileCompletePath(request, user.getAvatar(), ""));
            } else {
                user.setAvatar("");
            }
        }

        return user;
    }

    @Override
    public Integer updatePassword(String username, String newPassword) {
        User byUsername = this.getByUsername(username);
        if (byUsername == null) {
            return 0;
        }
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();

        userUpdateWrapper.set("password", UserUtils.encodePassword(newPassword))
                        .eq("username", username);

        return userMapper.update(null, userUpdateWrapper);

    }

    @Override
    public Boolean exist(String username) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", username);
        return userMapper.selectOne(userQueryWrapper) != null;
    }


}
