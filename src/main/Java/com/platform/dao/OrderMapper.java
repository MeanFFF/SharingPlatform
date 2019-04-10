package com.platform.dao;

import com.platform.pojo.Order;

public interface OrderMapper {
    int insert(Order record);

    int insertSelective(Order record);
}