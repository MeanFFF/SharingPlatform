package com.platform.service;

import com.github.pagehelper.PageInfo;
import com.platform.common.ServerResponse;
import com.platform.pojo.User;


public interface IUserService {
    ServerResponse<User> login(String username, String password);
    ServerResponse<String> checkValid(String str, String type);

    ServerResponse<User> updateInformation(User user);

    ServerResponse checkAdminRole(User user);

    ServerResponse<PageInfo> getList(int pageNum, int pageSize);

    ServerResponse resetPassword(User currentUser);

    ServerResponse changePwd(String uuid);

    ServerResponse changingPwd(String uuid, String newPwd);
}
