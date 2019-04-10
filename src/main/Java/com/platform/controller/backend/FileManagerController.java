package com.platform.controller.backend;

import com.platform.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manage/file/")
public class FileManagerController {

    @Autowired
    private IUserService iUserService;

    public void getList(){

    }

    public void fileSave(){

    }

    public void fileSearch(){

    }





}
