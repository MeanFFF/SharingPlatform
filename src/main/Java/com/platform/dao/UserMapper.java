package com.platform.dao;

import com.platform.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    int insert(User record);

    int insertSelective(User record);

    int checkUsername(String username);

    User selectLogin(@Param("username") String username, @Param("password") String password);

    int checkEmail(String email);

    int checkEmailByUserId(String email, Integer id);

    int updateByPrimaryKeySelective(User user);

    List<User> selectList();
}