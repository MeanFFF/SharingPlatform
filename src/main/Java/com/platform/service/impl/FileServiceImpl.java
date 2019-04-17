package com.platform.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.platform.common.ServerResponse;
import com.platform.dao.FileMapper;
import com.platform.pojo.File;
import com.platform.service.IFileService;
import com.platform.vo.FileListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileServiceImpl implements IFileService {

    @Autowired
    private FileMapper fileMapper;

    public ServerResponse<PageInfo> getProductList(int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);

        List<File> fileList = fileMapper.selectList();

        List<FileListVo> fileListVoList = Lists.newArrayList();

        for(File fileItem : fileList){
            FileListVo fileListVo = assembleFileListVo(fileItem);
            fileListVoList.add(fileListVo);
        }
        PageInfo pageResult = new PageInfo(fileList);
        pageResult.setList(fileListVoList);
        return ServerResponse.createBySuccess(pageResult);

    }

    public FileListVo assembleFileListVo(File file){
        FileListVo fileListVo = new FileListVo();
        fileListVo.setId(file.getId());
        fileListVo.setName(file.getName());
        fileListVo.setAddress(file.getAddress());
        fileListVo.setCategoryId(file.getCategoryId());
        fileListVo.setPrice(file.getPrice());
        fileListVo.setStatus(file.getStatus());
        return fileListVo;
    }

}
