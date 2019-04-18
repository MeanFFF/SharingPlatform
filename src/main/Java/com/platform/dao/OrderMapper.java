package com.platform.dao;

import com.platform.pojo.Order;

import java.util.List;

public interface OrderMapper {
    int insert(Order record);

    int insertSelective(Order record);

    List<Order> selectList();

    Order selectByOrderNo(Integer orderNo);
}