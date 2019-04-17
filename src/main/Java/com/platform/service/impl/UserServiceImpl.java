package com.platform.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.platform.common.Const;
import com.platform.common.ServerResponse;
import com.platform.dao.UserMapper;
import com.platform.pojo.User;
import com.platform.service.IUserService;
import com.platform.vo.UserListVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ServerResponse<User> login(String username, String password) {
        int resultCount = userMapper.checkUsername(username);
        if(resultCount == 0){
            return ServerResponse. createByErrorMessage("用户名不存在");
        }
        //TODO 密码登录MD5

        User user = userMapper.selectLogin(username, password);
        if(user == null){
            return ServerResponse.createByErrorMessage("密码错误");
        }

        // 登录成功时, 把密码设为空
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess("登录成功", user);
    }

    @Override
    public ServerResponse<String> checkValid(String str, String type){
        if(StringUtils.isNoneBlank(type)){
            if(Const.USERNAME.equals(type)){
                int resultCount = userMapper.checkUsername(str);
                if(resultCount > 0){
                    return ServerResponse.createByErrorMessage("用户名已存在");
                }
            }
            if(Const.EMAIL.equals(type)){
                int resultCount = userMapper.checkEmail(str);
                if(resultCount > 0){
                    return ServerResponse.createByErrorMessage("email已存在");
                }
            }
        }else{
            return ServerResponse.createByErrorMessage("参数错误");
        }
        return ServerResponse.createBySuccessMessage("校验成功");
    }

    @Override
    public ServerResponse<User> updateInformation(User user) {
        int resultCount = userMapper.checkEmailByUserId(user.getEmail(), user.getId());
        if(resultCount > 0){
            return ServerResponse.createByErrorMessage("email已经存在,请更换email再尝试更新");
        }
        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setEmail(user.getEmail());
        updateUser.setPhone(user.getPhone());

        int updateCount = userMapper.updateByPrimaryKeySelective(updateUser);
        if(updateCount > 0){
            return ServerResponse.createBySuccess("更新个人信息成功", user);
        }

        return ServerResponse.createByErrorMessage("更新个人信息失败");
    }

    @Override
    public ServerResponse checkAdminRole(User user){
        if(user != null && user.getRole().intValue() == Const.Role.ROLE_ADMIN){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }

    public ServerResponse<PageInfo> getList(int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);

        //从数据库中获得用户列表
        List<User> userList = userMapper.selectList();

        // userVo:你想要传给前端的包装数据
        // 创建一个userVo的list
        List<UserListVo> userListVoList = Lists.newArrayList();

        // 将用户列表中的user => userVo
        for(User userItem: userList){
            if(!checkAdminRole(userItem).isSuccess()){
                UserListVo userListVo = assembleUserListVo(userItem);
                userListVoList.add(userListVo);
            }
        }

        PageInfo pageResult = new PageInfo(userList);
        pageResult.setList(userListVoList);
        return ServerResponse.createBySuccess(pageResult);
    }

    private UserListVo assembleUserListVo(User user) {
        UserListVo userListVo = new UserListVo();
        userListVo.setId(user.getId());
        userListVo.setUsername(user.getUsername());
        userListVo.setEmail(user.getEmail());
        userListVo.setPhone(user.getPhone());
        userListVo.setCreateTime(user.getCreateTime());
        userListVo.setRole(user.getRole());
        return userListVo;
    }


}
