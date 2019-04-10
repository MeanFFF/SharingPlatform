package com.platform.dao;

import com.platform.pojo.Review;

public interface ReviewMapper {
    int insert(Review record);

    int insertSelective(Review record);
}