package com.platform.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.platform.common.ServerResponse;
import com.platform.dao.FileMapper;
import com.platform.pojo.File;
import com.platform.service.IFileService;
import com.platform.vo.FileListVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileServiceImpl implements IFileService {

    @Autowired
    private FileMapper fileMapper;

    // 获取文件列表
    @Override
    public ServerResponse<PageInfo> getFileList(int pageNum, int pageSize){
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

    /**
     * 获取搜索的文件列表
     * 搜索分为用id搜索,用文件名搜索
     * @param fileId
     * @param fileName
     * @param pageNum
     * @param pageSize
     * @return
     */
    //
    @Override
    public ServerResponse getSearchFileList(Integer fileId, String fileName, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        if(StringUtils.isNoneBlank(fileName)){
            fileName = new StringBuilder().append("%").append(fileName).append("%").toString();
        }

        List<File> fileList = fileMapper.selectSearchList(fileId, fileName);
        List<FileListVo> fileListVoList = Lists.newArrayList();

        for(File fileItem : fileList){
            FileListVo fileListVo = assembleFileListVo(fileItem);
            fileListVoList.add(fileListVo);
        }

        PageInfo pageResult = new PageInfo(fileList);
        pageResult.setList(fileListVoList);
        return ServerResponse.createBySuccess(pageResult);
    }

    /**
     *  设置文件状态
     *      1:前台可以显示
     *      2:"删除"
     * @param fileId
     * @param status
     * @return
     */
    @Override
    public ServerResponse setFileStatus(Integer fileId, Integer status) {
        if(fileId == null || status != 1 && status != 2){
            return ServerResponse.createByErrorMessage("设置文件状态错误");
        }
        File file = new File();
        file.setId(fileId);
        file.setStatus(status);
        int rowCount = fileMapper.updateByPrimaryKeySelective(file);
        if(rowCount > 0){
            return ServerResponse.createBySuccess("设置文件状态成功");
        }
        return ServerResponse.createByErrorMessage("设置文件状态失败");
    }

    /**
     * 获取产品的详细信息
     * @param fileId
     * @return
     */
    public ServerResponse getFileDetail(Integer fileId){
        File file = fileMapper.selectByIdAndName(fileId, null);
        if(file == null){
            return ServerResponse.createByErrorMessage("获取文件信息错误");
        }
        return ServerResponse.createBySuccess(file);

    }

    /**
     *  包装File
     *  File => FileVo
     *  这个FileVo是传给列表的
     *  传给Detail的file就不进行包装了,直接传file
     * @param file
     * @return
     */
    public FileListVo assembleFileListVo(File file){
        FileListVo fileListVo = new FileListVo();
        fileListVo.setId(file.getId());
        fileListVo.setName(file.getName());
        fileListVo.setCategoryId(file.getCategoryId());
        fileListVo.setPrice(file.getPrice());
        return fileListVo;
    }


}
