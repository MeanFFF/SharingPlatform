package com.platform.service;

import com.github.pagehelper.PageInfo;
import com.platform.common.ServerResponse;

public interface IOrderService {
    ServerResponse<PageInfo> ManageList(int pageNum, int pageSize);

    ServerResponse OrderDetail(Integer orderNo);
}
