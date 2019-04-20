package com.platform.Utils;

import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

import static org.junit.Assert.*;

public class FTPUtilTest {

    @Test
    public void TestFTP() {

    }

    /**
     * 测试文件下载
     */
    @Test
    public void downloadTest() {
        try {
            String localpath = "G:/ftpdownload/";
            String filename = "53077d28-d272-48ec-9510-5781bca9c0d8.exe";
            File file = new File(localpath + filename);
            if(file.exists()){
                System.out.println("文件已存在");
            }else{
                boolean d = FTPUtil.downloadFile("idm.exe", filename, localpath);
                System.out.println(d);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void test(){
        System.out.println(new Date());
    }

}