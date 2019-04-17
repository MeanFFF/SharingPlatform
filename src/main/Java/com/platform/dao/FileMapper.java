package com.platform.dao;

import com.platform.pojo.File;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FileMapper {
    int insert(File record);

    int insertSelective(File record);

    List<File> selectList();

    List<File> selectSearchList(@Param("fileId") Integer fileId, @Param("fileName") String fileName);

    int updateByPrimaryKeySelective(File file);

    File selectByIdAndName(@Param("id") Integer id, @Param("name") String name);
}