package com.bill.backend.modules.dao;

import lombok.Data;

@Data
public class UserRequest {
    private String username;
    private String password;
    private String newPassword;
    private String checkPassword;

    private String check;
    private String code;
    private String uuid;
}
