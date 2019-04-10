package com.platform.dao;

import com.platform.pojo.OrderItem;

public interface OrderItemMapper {
    int insert(OrderItem record);

    int insertSelective(OrderItem record);
}