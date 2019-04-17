package com.platform.controller.backend;

import com.github.pagehelper.PageInfo;
import com.platform.common.Const;
import com.platform.common.ResponseCode;
import com.platform.common.ServerResponse;
import com.platform.pojo.User;
import com.platform.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * 管理员用户控制块
 */
@Controller
@RequestMapping("/manage/user/")
public class UserManageController {

    @Autowired
    private IUserService iUserService;

    /**
     * 管理员登录
     * @param username
     * @param password
     * @param session
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session){
        ServerResponse<User> response = iUserService.login(username, password);
        if(response.isSuccess()){
            User user = response.getData();
            if(user.getRole() == Const.Role.ROLE_ADMIN){
                session.setAttribute(Const.CURRENT_USER, user);
                return response;
            }else{
                return ServerResponse.createByErrorMessage("不是管理员,无法登录");
            }
        }
        return response;
    }

    /**
     * 管理员登出
     * 普通用户也一样的
     * @param session
     * @return
     */
    @RequestMapping(value = "logout", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse logout(HttpSession session){
            session.removeAttribute(Const.CURRENT_USER);
            return ServerResponse.createBySuccess();
    }

    /**
     * 获取普通用户列表
     * @param session
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public ServerResponse<PageInfo> list(HttpSession session, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"还未登录,请登录");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            return iUserService.getList(pageNum, pageSize);
        }else{
            return ServerResponse.createByErrorMessage("没有权限");
        }
    }


}
