package com.platform.dao;

import com.platform.pojo.OrderItem;

import java.util.List;

public interface OrderItemMapper {
    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    List<OrderItem> getByOrderNo(Long orderNo);
}