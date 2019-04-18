package com.platform.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.platform.Utils.FTPUtil;
import com.platform.common.Const;
import com.platform.common.ServerResponse;
import com.platform.dao.FileMapper;
import com.platform.pojo.File;
import com.platform.service.IFileService;
import com.platform.vo.FileListVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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

    public ServerResponse<PageInfo> getUncheckedFileList(int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);

        List<File> fileList = fileMapper.selectListByStatus(Const.FileStatus.UNCHECKED);

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


    /**
     * 文件 => upload文件夹 => ftp服务器
     * @param file
     * @param path
     * @return
     */
    public String uploadFile(MultipartFile file, String path){
        String fileName = file.getOriginalFilename();

        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".")+1);
        String uploadFileName = UUID.randomUUID().toString() + "." + fileExtensionName;

        System.out.println(fileName + " : " + fileExtensionName + " : " + uploadFileName);

        java.io.File fileDir = new java.io.File(path);
        if(!fileDir.exists()){
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }

        java.io.File targetFile = new java.io.File(path, uploadFileName);

        try {
            file.transferTo(targetFile);

            FTPUtil.uploadFile(Lists.newArrayList(targetFile));

            targetFile.delete();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return targetFile.getName();

    }

    @Override
    public ServerResponse addFile(HttpServletRequest request, MultipartFile file, Integer categoryId, String detail, Integer price) {
        String path = request.getSession().getServletContext().getRealPath("upload");
        String targetFileName = this.uploadFile(file, path);
        File newfile = new File();
        newfile.setName(file.getOriginalFilename());
        newfile.setStatus(Const.FileStatus.UNCHECKED);
        newfile.setAddress(targetFileName);
        newfile.setCategoryId(categoryId);
        newfile.setCreateTime(new Date());
        newfile.setDetail(detail);
        newfile.setPrice(new BigDecimal(price));


        int rowCount = fileMapper.insertSelective(newfile);
        if(rowCount > 0){
            return ServerResponse.createBySuccess("上传文件成功");
        }
        return ServerResponse.createByErrorMessage("上传文件失败");
    }

//    /**
//     * 添加文件
//     * @param newfile
//     * @return
//     */
//    @Override
//    public ServerResponse addFile(File newfile) {
//        int rowCount = fileMapper.insertSelective(newfile);
//        if(rowCount > 0){
//            return ServerResponse.createBySuccess("上传文件成功");
//        }
//        return ServerResponse.createByErrorMessage("上传文件失败");
//    }


}
