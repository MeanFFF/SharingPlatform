package com.platform.dao;

import com.platform.pojo.Category;

import java.util.List;

public interface CategoryMapper {
    int insert(Category record);

    int insertSelective(Category record);

    int updateByPrimaryKeySelective(Category category);

    List<Category> selectCategoryChildrenByParentId(Integer parentId);

    Category selectByPrimaryKey(Integer id);

    int updateStatusByPrimaryKey(Category category);
}