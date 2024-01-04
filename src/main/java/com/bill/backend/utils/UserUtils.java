package com.bill.backend.utils;

import org.springframework.util.DigestUtils;

public class UserUtils {
    /**
     * 盐值，混淆密码
     */
    private static final String SALT = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";

    public static String encodePassword(String password) {
        return DigestUtils.md5DigestAsHex((SALT + password).getBytes());
    }
}
