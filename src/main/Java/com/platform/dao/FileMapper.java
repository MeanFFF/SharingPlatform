package com.platform.dao;

import com.platform.pojo.File;

public interface FileMapper {
    int insert(File record);

    int insertSelective(File record);
}