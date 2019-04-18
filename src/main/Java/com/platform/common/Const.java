package com.platform.common;

public class Const {
    public static final String CURRENT_USER = "currentUser";
    public static final String EMAIL = "email";
    public static final String USERNAME = "username";

    public interface Role{
        int ROLE_CUSTOMER = 1; //普通用户
        int ROLE_ADMIN = 0;//管理员
    }

    public interface FileStatus{
        int UNCHECKED = 0;
        int CHECKED = 1;
        int DELETE = 2;
    }
}
