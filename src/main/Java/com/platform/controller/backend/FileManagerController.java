package com.platform.controller.backend;

import com.platform.Utils.FTPUtil;
import com.platform.common.Const;
import com.platform.common.ResponseCode;
import com.platform.common.ServerResponse;
import com.platform.pojo.User;
import com.platform.service.IFileService;
import com.platform.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

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
    public ServerResponse getList(HttpSession session, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iFileService.getFileList(pageNum, pageSize);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    // 搜索文件
    @RequestMapping("search")
    @ResponseBody
    public ServerResponse fileSearch(HttpSession session,
                                     Integer fileId,
                                     String fileName,
                                     @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                     @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iFileService.getSearchFileList(fileId, fileName, pageNum, pageSize);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    /**
     * 未审核列表
     * @param session
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("get_unchecked_list")
    @ResponseBody
    public ServerResponse fileSearch(HttpSession session, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iFileService.getUncheckedFileList(pageNum, pageSize);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }


    /**
     * 用户上传文件
     * @param session       会话
     * @param file          文件
     * @param fileName      文件名
     * @param categoryId    分类名
     * @param detail        详细描述
     * @param price         价格
     * @param request       用于获得中转文件夹的路径
     * @return
     */
    @RequestMapping("upload")
    @ResponseBody
    public ServerResponse upload(HttpSession session,
                                 @RequestParam(value = "upload_file", required = false) MultipartFile file,
                                 @RequestParam(value = "file_name", required = false) String fileName,
                                 @RequestParam(value = "category_id", required = false) Integer categoryId,
                                 @RequestParam(value = "detail", required = false) String detail,
                                 @RequestParam(value = "price", required = false) Integer price,
                                 HttpServletRequest request) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录");
        }
        if(file.isEmpty()){
            return ServerResponse.createByErrorMessage("文件不能为空");
        }
        if(StringUtils.isBlank(fileName)){
            return ServerResponse.createByErrorMessage("文件名不能为空");
        }
        if(categoryId == null){
            return ServerResponse.createByErrorMessage("分类不能为空");
        }
        if(detail == null){
            return ServerResponse.createByErrorMessage("描述不能为空");
        }
        if(price == null){
            return ServerResponse.createByErrorMessage("价格不能为空");
        }

        return iFileService.addFile(request, file, fileName, categoryId, detail, price);

    }

    // TODO 用户下载逻辑 要封装起来,达到要求才能够暴露下载地址!!!
    @RequestMapping(value = "download", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse downloadFile(HttpSession session, Integer fileId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录");
        }
        try {
            String localpath = "G:/ftpdownload/";
            /**
             * 先根据fileId从redis缓存中去真实文件名
             * 如果redis中不存在,再从数据库中取
             */
            ServerResponse response = iFileService.getFileDetail(fileId);
            com.platform.pojo.File thisFile = (com.platform.pojo.File) response.getData();
            String filename = thisFile.getName();
            String fileAddress = thisFile.getAddress();
            File file = new File(localpath + filename);
            if(file.exists()){
                return ServerResponse.createBySuccess("文件已存在");
            }else{
                boolean d = FTPUtil.downloadFile(filename, fileAddress, localpath);
                if(d == true){
                    return ServerResponse.createBySuccess("文件下载成功");
                }else {
                    return ServerResponse.createByErrorMessage("文件下载失败");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return ServerResponse.createByErrorMessage("文件下载失败");
    }

    // 获取详细信息
    @RequestMapping("detail")
    @ResponseBody
    public ServerResponse getDetail(HttpSession session, Integer fileId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iFileService.getFileDetail(fileId);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    // 设置文件的状态(0-还未审核 1-审核通过 2-审核未通过 3-删除)
    @RequestMapping("set_file_status")
    @ResponseBody
    public ServerResponse setSaleStatus(HttpSession session, Integer fileId, Integer status) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {

            return iFileService.setFileStatus(fileId, status);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }
}
