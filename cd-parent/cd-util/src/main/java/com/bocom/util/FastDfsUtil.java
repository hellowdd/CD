package com.bocom.util;

import org.apache.commons.lang.RandomStringUtils;
import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 通过fastDFS来管理文件
 *
 * @ClassName: FileUtil
 * @Description: TODO
 * @author: 韦冬冬
 * @date: 2016年11月25日 上午10:56:19
 */
public class FastDfsUtil {
    private static final Logger logger = LoggerFactory
            .getLogger(FastDfsUtil.class);
    // private static String conf_filename = "G:/workspace/aadm/app/aadm-util/target/classes/fdfs_client.conf";
    private static String conf_filename = Thread.currentThread().getContextClassLoader().getResource("/")
            .getPath()
            + "conf" + File.separator + "fdfs_client.conf";
    private static TrackerClient tracker = null;
    private static StorageServer storageServer = null;
    private static TrackerServer trackerServer = null;

    {
        try {

            ClientGlobal.init(conf_filename);
            TrackerClient tracker = new TrackerClient();
            trackerServer = tracker.getConnection();
            StorageClient1 client = new StorageClient1(trackerServer,
                    storageServer);
            /**
             * 发送一次测试请求
             */
            if (ProtoCommon.activeTest(trackerServer.getSocket())) {
                logger.debug("init FastDfsServer success ");
            }

        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (MyException e) {
            logger.error(e.getMessage());
        }

    }

//	public FastDfsUtil(){
//	    try {
//            ClientGlobal.init(conf_filename);
//            TrackerClient tracker = new TrackerClient();
//            trackerServer = tracker.getConnection();
//            StorageClient1 client = new StorageClient1(trackerServer,
//                    storageServer);
//            /** 
//            * 发送一次测试请求 
//            */ 
//            if (ProtoCommon.activeTest(trackerServer.getSocket())) { 
//            logger.debug("init FastDfsServer success "); 
//            } 
//
//        } catch (FileNotFoundException e) {
//            logger.error(e.getMessage());
//        } catch (IOException e) {
//            logger.error(e.getMessage());
//        } catch (MyException e) {
//            logger.error(e.getMessage());
//        } 
//	}

    /**
     * 上传文件
     *
     * @param fileName 文件路径
     * @throws MyException
     * @throws IOException
     * @Title: uploadFile
     * @Description: TODO
     * @author: 韦冬冬
     * @date: 2016年11月25日 上午10:56:57
     * @return: String[] 文件在服务器上的坐标
     */
    public String uploadFile(byte[] b, String fileName) throws IOException, MyException {

        // 获取文件类型
        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
        StorageClient1 storageClient1 = new StorageClient1(trackerServer,
                storageServer);
        String fileIds = storageClient1.upload_file1(b, fileType, null);
        return fileIds;
    }

    /**
     * 上传文件
     *
     * @param filePath 文件路径
     * @return
     * @Title: uploadFile
     * @Description: TODO
     * @author: 韦冬冬
     * @date: 2016年12月16日 下午4:46:43
     * @return: String 文件在服务器上的虚拟路径
     */
    public String uploadFile(String filePath) {
        try {
            // 获取文件类型
            String fileType = filePath.substring(filePath.lastIndexOf(".") + 1);
            StorageClient1 storageClient1 = new StorageClient1(trackerServer,
                    storageServer);
            String fileIds = storageClient1.upload_file1(filePath, fileType,
                    null);
            return fileIds;
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (MyException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    /**
     * 删除文件
     *
     * @param fileInfo 文件在服务器上的坐标
     * @Title: deleteFile
     * @Description: TODO
     * @author: 韦冬冬
     * @date: 2016年11月25日 上午10:57:46
     * @return: boolean 删除成功 true 删除失败 false
     */
    public boolean deleteFile(String fileInfo) {
        try {
            StorageClient1 storageClient1 = new StorageClient1(trackerServer,
                    storageServer);
            int i = storageClient1.delete_file1(fileInfo);
            logger.info("fastDFS删除文件返回值：" + i);
            if (i == 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    /**
     * 从服务器上下载文件
     *
     * @param filePath 文件在服务器上的坐标
     * @Title: downloadFile
     * @Description: TODO
     * @author: 韦冬冬
     * @date: 2016年11月25日 下午3:47:47
     * @return: 二进制数组
     */
    public byte[] downloadFile(String filePath) {
        try {
            StorageClient1 storageClient1 = new StorageClient1(trackerServer,
                    storageServer);
            byte[] b = storageClient1.download_file1(filePath);
            return b;
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (MyException e) {
            logger.error(e.getMessage());
        }
        return null;

    }

    public boolean downloadFile(String fileInfo, String outFile) {
        FileOutputStream fos = null;
        try {
            StorageClient1 storageClient1 = new StorageClient1(trackerServer,
                    storageServer);
            byte[] b = storageClient1.download_file1(fileInfo);
            fos = new FileOutputStream(outFile);
            fos.write(b);
            return true;
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (MyException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
        return false;

    }

    public static void main(String[] args) {


//		new FastDfsUtil().downloadFile("group1/M01/00/00/CiWVZlhiL4aASyAbAAQgPoS1rDE225.ico", "D:/aa.ico");
//		new FastDfsUtil().downloadFile("group1/M02/01/04/CiWVZljcoMWAXt_mAAAHKe3OGwA814.zip", "D:/widget_169.zip");
    }


}
