package com.platform.controller.backend;

import com.platform.common.Const;
import com.platform.common.ResponseCode;
import com.platform.common.ServerResponse;
import com.platform.pojo.User;
import com.platform.service.IFileService;
import com.platform.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/manage/file/")
public class FileManagerController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IFileService iFileService;

    //获取文件列表
    @RequestMapping("list")
    @ResponseBody
    public ServerResponse getList(HttpSession session, @RequestParam(value = "pageNum", defaultValue="1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            return iFileService.getFileList(pageNum,pageSize);
        }else{
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    // 搜索文件
    @RequestMapping("search")
    @ResponseBody
    public ServerResponse fileSearch(HttpSession session, Integer fileId, String fileName, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            return iFileService.getSearchFileList(fileId, fileName, pageNum, pageSize);
        }else{
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    // 上传???
    @RequestMapping("upload")
    @ResponseBody
    public ServerResponse upload(HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            //TODO 增加上传逻辑
            return null;
        }else{
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    // 获取详细信息
    @RequestMapping("detail")
    @ResponseBody
    public ServerResponse getDetail(HttpSession session, Integer fileId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            return iFileService.getFileDetail(fileId);
        }else{
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    // 设置文件的状态(1-在售 2-删除)
    @RequestMapping("set_file_status")
    @ResponseBody
    public ServerResponse setSaleStatus(HttpSession session,Integer fileId, Integer status){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){

            return iFileService.setFileStatus(fileId, status);
        }else{
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }
}
