package com.platform.dao;

import com.platform.pojo.Cart;

public interface CartMapper {
    int insert(Cart record);

    int insertSelective(Cart record);
}