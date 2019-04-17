package com.platform.service;

import com.github.pagehelper.PageInfo;
import com.platform.common.ServerResponse;

public interface IFileService {
    ServerResponse<PageInfo> getFileList(int pageNum, int pageSize);

    ServerResponse getSearchFileList(Integer fileId, String fileName, int pageNum, int pageSize);

    ServerResponse setFileStatus(Integer fileId, Integer status);

    ServerResponse getFileDetail(Integer fileId);
}
