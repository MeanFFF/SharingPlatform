package Utils;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;



import java.io.*;
import java.net.SocketException;


public class FTPUtil {
    /*TODO try-catch-finally -> try(resources)-catch*/
    public static FTPClient ftpClient = null;
    public static FTPClient getFTPClient(String ftpHost, int ftpPort, String ftpUserName,
                                    String ftpPassword) {
        ftpClient = new FTPClient();
        ftpClient.setControlEncoding("utf-8");
        ftpClient.setDataTimeout(60000);       //设置传输超时时间为60秒
        ftpClient.setConnectTimeout(60000);       //连接超时为60秒
        try {
            ftpClient.connect(ftpHost, ftpPort);// 连接FTP服务器
            ftpClient.login(ftpUserName, ftpPassword);// 登陆FTP服务器
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                System.out.println("未连接到FTP，用户名或密码错误。");
                ftpClient.disconnect();
            }
        } catch (SocketException e) {
            e.printStackTrace();
            //throw new SocketException("FTP的IP地址可能错误，请正确配置。");
        } catch (IOException e) {
            e.printStackTrace();
            //throw new SocketException("FTP的端口错误,请正确配置。");
        }
        return ftpClient;
    }


    /**
     * 上传文件
     * @param pathName ftp服务保存地址
     * @param fileName 上传到ftp的文件名
     *  @param originfilename 待上传文件的名称（绝对地址） *
     * @return
     */
    public static boolean uploadFile(FTPClient ftpClient, String pathName, String fileName,String originfilename) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(originfilename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return uploadFile(ftpClient,pathName,fileName,inputStream);
    }
    /**
     * 上传文件
     * @param realPathName ftp服务保存地址
     * @param uuidName 上传到ftp的文件名
     * @param inputStream 输入文件流
     * @return
     */
    public static boolean uploadFile(FTPClient client,String realPathName, String uuidName,InputStream inputStream){
        boolean flag=false;
        try{
            ftpClient=client;
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
//            createDirecroty(realPathName);
            ftpClient.makeDirectory(realPathName);
            ftpClient.changeWorkingDirectory(realPathName);
            ftpClient.storeFile(uuidName, inputStream);
            inputStream.close();
            ftpClient.logout();
            flag=true;
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("上传文件失败");
        }finally{
            if(ftpClient.isConnected()){
                try{
                    ftpClient.disconnect();
                }catch(IOException e){
                    e.printStackTrace();
                    //throw new Exception("上传文件失败");
                }
            }

        }
        return flag;
    }

    //判断ftp服务器是否有文件存在
    public static boolean existFile(String path) throws IOException {
        boolean flag = false;
        FTPFile[] ftpFileArr = ftpClient.listFiles(path);
        //FTPFile[] ftpFileArr = getDefaultFtpClient().listFiles(path);
        if (ftpFileArr.length > 0) {
            flag = true;
        }
        return flag;
    }

    //创建目录
//    public static boolean makeDirectory(String dir) {
//        boolean flag = true;
//        try {
////            flag = getDefaultFtpClient().makeDirectory(dir);
//            flag = ftpClient.makeDirectory(dir);
//            if (flag) {
//                System.out.println("创建文件夹" + dir + " 成功！");
//
//            } else {
//                System.out.println("创建文件夹" + dir + " 失败！");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return flag;
//    }

    public static boolean downloadFile(FTPClient ftpClient, String pathName, String fileName){
        FileOutputStream fos = null;
        try {
            /*TODO 这里可以弄一个选择下载文件夹*/
             fos = new FileOutputStream(new File("G:\\file\\" + fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return downloadFile(ftpClient, pathName, fileName, fos);
    }

    /** * 下载文件 *
     * @param pathName FTP服务器文件目录 *
     * @param fileName 文件名称 *
    //* @param localpath 下载后的文件路径 *
     * @return */
    public static boolean downloadFile(FTPClient client,String pathName, String fileName, OutputStream os){
        boolean flag = false;
        //OutputStream os=null;
        try {
            System.out.println("开始下载文件");
            ftpClient=client;
            //切换FTP目录
            ftpClient.changeWorkingDirectory(pathName);
            FTPFile[] ftpFiles = ftpClient.listFiles();
            for(FTPFile file : ftpFiles){
                if(fileName.equalsIgnoreCase(file.getName())){
                    //File localFile = new File(localpath + "/" + file.getName());
                    //os = new FileOutputStream(localFile);
                    ftpClient.retrieveFile(file.getName(), os);
                    os.close();
                }
            }
            ftpClient.logout();
            flag = true;
            System.out.println("下载文件成功");
        } catch (Exception e) {
            e.printStackTrace();
            //throw new Exception("下载文件失败");
        } finally{
            if(ftpClient.isConnected()){
                try{
                    ftpClient.disconnect();
                }catch(IOException e){
                    e.printStackTrace();
                    //throw new Exception("下载文件失败");
                }
            }
            if(null != os){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    //throw new Exception("下载文件失败");
                }
            }
        }
        return flag;
    }

}