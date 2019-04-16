package com.platform.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.platform.common.ServerResponse;
import com.platform.dao.FileMapper;
import com.platform.pojo.File;
import com.platform.service.IFileService;

import java.util.List;

public class FileService implements IFileService {

    public ServerResponse<PageInfo> getProductList(int pageNum, int pageSize){
//        PageHelper.startPage(pageNum, pageSize);
//
//        List<File> fileList = FileMapper.selectList();
//
//        List<FileListVo> fileListVoList = Lists.newArrayList();
//
//        for(File fileItem : fileList){
//            FileListVo fileListVo = assembleFileListVo(fileItem);
//            fileListVoList.add(fileListVo);
//        }
//        PageInfo pageResult = new PageInfo(fileList);
//        pageResult.setList(fileListVoList);
//        return ServerResponse.createBySuccess(pageResult);

        return null;
    }

}
