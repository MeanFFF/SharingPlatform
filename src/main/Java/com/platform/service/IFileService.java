package com.platform.service;

import com.github.pagehelper.PageInfo;
import com.platform.common.ServerResponse;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface IFileService {
    ServerResponse<PageInfo> getFileList(int pageNum, int pageSize);

    ServerResponse getSearchFileList(Integer fileId, String fileName, int pageNum, int pageSize);

    ServerResponse setFileStatus(Integer fileId, Integer status);

    ServerResponse getFileDetail(Integer fileId);

    String uploadFile(MultipartFile file, String path);

    ServerResponse addFile(HttpServletRequest request, MultipartFile file, Integer categoryId, String detail, Integer price);

    ServerResponse<PageInfo> getUncheckedFileList(int pageNum, int pageSize);
}
