package com.platform.dao;

import com.platform.pojo.User;

public interface UserMapper {
    int insert(User record);

    int insertSelective(User record);
}