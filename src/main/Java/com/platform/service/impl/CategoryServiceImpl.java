package com.platform.service.impl;

import com.platform.common.ServerResponse;
import com.platform.dao.CategoryMapper;
import com.platform.pojo.Category;
import com.platform.service.ICategoryService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CategoryServiceImpl implements ICategoryService {

    private Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryMapper categoryMapper;

    public ServerResponse addCategory(String categoryName, Integer parentId){
        if(parentId == null || StringUtils.isBlank(categoryName)){
            return ServerResponse.createByErrorMessage("添加品类错误");
        }

        Category category = new Category();
        category.setName(categoryName);
        category.setParentId(parentId);
        category.setStatus(true);

        int rowCount = categoryMapper.insert(category);
        if(rowCount > 0){
            return ServerResponse.createBySuccess("添加品类成功");
        }

        return ServerResponse.createByErrorMessage("添加品类失败");
    }

    public ServerResponse updateCategoryName(Integer categoryId, String categoryName){
        if(categoryId == null || StringUtils.isBlank(categoryName)){
            return ServerResponse.createByErrorMessage("更新品类错误");
        }

        Category category = new Category();
        category.setId(categoryId);
        category.setName(categoryName);

        int rowCount = categoryMapper.updateByPrimaryKeySelective(category);

        if(rowCount > 0){
            return ServerResponse.createBySuccess("更新品类名字成功");
        }

        return ServerResponse.createByErrorMessage("更新品类名字失败");

    }

    public ServerResponse deleteCategoryById(Integer categoryId){
        if(categoryId == null){
            return ServerResponse.createByErrorMessage("删除品类错误");
        }

        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        int rowCount = categoryMapper.updateStatusByPrimaryKey(category);
        if(rowCount > 0){
            return ServerResponse.createBySuccess("删除品类成功");
        }

        return ServerResponse.createByErrorMessage("删除品类失败");
    }

    /**
     * 根据父节点,得到父节点的所有孩子节点
     * @param parentId
     * @return
     */
    public ServerResponse<List<Category>> getChildrenParallelCategory(Integer parentId){
        List<Category> categoryList = categoryMapper.selectCategoryChildrenByParentId(parentId);
        if(CollectionUtils.isEmpty(categoryList)){
            logger.info("未找到当前分类的子分类");
        }
        return ServerResponse.createBySuccess(categoryList);
    }

    public ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId){
        Set<Category> categoryset = new HashSet<>();
        findChildCategory(categoryset, categoryId);

        List<Integer> categoryIdList = new ArrayList<>();
        if(categoryId != null){
            for(Category categoryItem : categoryset){
                categoryIdList.add(categoryItem.getId());
            }
        }

        return ServerResponse.createBySuccess(categoryIdList);

    }

    private Set<Category> findChildCategory(Set<Category> categorySet, Integer categoryId){
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if(category != null){
            categorySet.add(category);
        }
        //mybatis如何没有查到,不会返回null
        List<Category> categoryList = categoryMapper.selectCategoryChildrenByParentId(categoryId);
        for(Category categoryItem : categoryList){
            findChildCategory(categorySet, categoryItem.getId());
        }

        return categorySet;
    }

}
