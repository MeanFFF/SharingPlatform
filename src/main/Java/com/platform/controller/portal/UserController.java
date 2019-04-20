package com.platform.controller.portal;


import com.platform.common.Const;
import com.platform.common.ServerResponse;
import com.platform.dao.UserMapper;
import com.platform.pojo.User;
import com.platform.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.mail.Session;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private IUserService iUserService;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session){
        //service -> mybatis -> dao
        ServerResponse<User> response = iUserService.login(username, password);
        if(response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER, response.getData());
        }
        return response;
    }

    @RequestMapping(value = "check_valid", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> checkValid(String str, String type){
        return iUserService.checkValid(str, type);

    }

    @RequestMapping(value = "get_user_info", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> getUserInfo(HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user != null){
            return ServerResponse.createBySuccess(user);
        }
        return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
    }

    @RequestMapping(value = "update_information", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> update_information(HttpSession session, User user){
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        //登录状态下才能修改信息
        if(currentUser == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }

        //防止越权问题
        user.setId(currentUser.getId());
        user.setUsername(currentUser.getUsername());
        ServerResponse<User> response = iUserService.updateInformation(user);
        if(response.isSuccess()){
            response.getData().setUsername(currentUser.getUsername());
            session.setAttribute(Const.CURRENT_USER, response.getData());
        }
        return response;
    }

    @RequestMapping(value = "reset_pwd", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse resetPwd(HttpSession session){
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if(currentUser == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return iUserService.resetPassword(currentUser);
    }

    @RequestMapping(value = "change")
    @ResponseBody
    public ServerResponse changePwd(String uuid){
        return iUserService.changePwd(uuid);
    }

    @RequestMapping(value = "changing_pwd")
    @ResponseBody
    public ServerResponse changingPwd(String uuid, @RequestParam(value = "newPwd") String newPwd){
        if(StringUtils.isBlank(newPwd)){
            return ServerResponse.createByErrorMessage("密码不能为空");
        }
        return iUserService.changingPwd(uuid, newPwd);
    }

}
