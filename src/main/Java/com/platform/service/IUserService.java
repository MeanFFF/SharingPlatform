package com.platform.service;

import com.platform.common.ServerResponse;
import com.platform.pojo.User;

public interface IUserService {
    ServerResponse<User> login(String username, String password);
    ServerResponse<String> checkValid(String str, String type);

    ServerResponse<User> updateInformation(User user);

    ServerResponse checkAdminRole(User user);
}
