package com.platform.dao;

import com.platform.pojo.Category;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class CategoryMapperTest {
    @Autowired
    public CategoryMapper categoryMapper;

    @Test
    public void add(){
        Category category = new Category(1, 1, "name", true, 1, new Date(), new Date());
        categoryMapper.insert(category);
    }
}