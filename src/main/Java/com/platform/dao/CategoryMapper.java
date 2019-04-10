package com.platform.dao;

import com.platform.pojo.Category;

public interface CategoryMapper {
    int insert(Category record);

    int insertSelective(Category record);
}