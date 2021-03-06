package com.bocom.util;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Expand;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.*;

public class FileUtil {
    private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 解压文件
     *
     * @param filePath
     */
    public static void unZipFiles(String fileName, String destFilePath,
                                  boolean isDelete) throws Exception {
        File zip = new File(fileName);
        ZipFile zipFile = new ZipFile(fileName, "UTF-8");
        Enumeration emu = zipFile.getEntries();
        while (emu.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) emu.nextElement();
            if (entry.isDirectory()) {
                File dir = new File(destFilePath + entry.getName());
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                continue;
            }
            BufferedInputStream bis = new BufferedInputStream(
                    zipFile.getInputStream(entry));

            File file = new File(destFilePath + File.separator
                    + entry.getName());
            File parent = file.getParentFile();
            if (parent != null && (!parent.exists())) {
                parent.mkdirs();
            }
            FileOutputStream fos = new FileOutputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(fos, 1024);
            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = bis.read(buf, 0, 1024)) != -1) {
                fos.write(buf, 0, len);
            }
            bos.flush();
            bos.close();
            bis.close();
        }
        zipFile.close();
        if (isDelete) {
            zip.delete();
        }

    }

    /**
     * 解压缩文件和文件夹
     *
     * @param zipFilepath 需要被解压的zip文件路径
     * @param destDir     将要被解压到哪个文件夹
     * @param deleteFile  是否删除文件
     */
    public static boolean unzip(String zipFilepath, String destDir) {
        File zipFile = new File(zipFilepath);
        if (!zipFile.exists() && logger.isDebugEnabled()) {
            logger.debug("source file or directory " + zipFilepath
                    + " does not exist.");
            return false;
        }
        Project project = new Project();
        Expand expand = new Expand();
        expand.setProject(project);
        expand.setTaskType("unzip");
        expand.setTaskName("unzip");
        expand.setEncoding("utf-8");
        expand.setSrc(new File(zipFilepath));
        expand.setDest(new File(destDir));
        expand.execute();
        if (logger.isDebugEnabled()) {
            logger.debug("Extract file success " + zipFilepath
                    + ", the path is  " + destDir);
        }
        return true;

    }

    // 删除文件夹
    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); // 删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            myFilePath.delete(); // 删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 删除文件下的所有文件
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);// 再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 校验文件夹下的文件是否合法
     *
     * @param filePath
     * @return
     */
    public static boolean validateFile(String filePath) {
        Map<String, String> map = new HashMap<String, String>();
        //标识所有的文件合法的后缀
        map.put("BMP", "BMP");
        map.put("PNG", "PNG");
        map.put("GIF", "GIF");
        map.put("JPG", "JPG");
        map.put("JPEG", "JPEG");
        map.put("HTML", "HTML");
        map.put("CSS", "CSS");
        map.put("JS", "JS");
        map.put("HTM", "HTM");
        List<String> list = new ArrayList<String>();
        //遍历出文件路径下的所有的文件
        list = listFile(new File(filePath), list);
        String str = "";
        for (String fileName : list) {
            //截取获得到的文件类型，并转化为大写
            str = (fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length())).toUpperCase();
            if (null == map.get(str)) {
                return false;
            }
        }
        return true;

    }

    /**
     * 遍历出一个文件夹下的所有的文件名
     *
     * @param file
     * @param map
     * @return
     */
    public static List<String> listFile(File file, List<String> list) {
        if (file.isDirectory()) {
            File[] f = file.listFiles();
            for (File f1 : f) {
                if (f1.isDirectory()) {
                    listFile(f1, list);
                } else {
                    String fileName = f1.getName();
                    list.add(fileName);
                }
            }
        }
        return list;

    }

    public static String getFileMD5(MultipartFile file) throws Exception {
//        if (!file.isFile()) {
//            return null;
//        }
//
//        MessageDigest digest = null;
//        FileInputStream in = null;
//        byte buffer[] = new byte[8192];
//        int len;
//        try {
//            digest = MessageDigest.getInstance("MD5");
//            in = new FileInputStream(file);
//            while ((len = in.read(buffer)) != -1) {
//                digest.update(buffer, 0, len);
//            }
//            BigInteger bigInt = new BigInteger(1, digest.digest());
//            return bigInt.toString(16);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        } finally {
//            try {
//                in.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            } 
//        }
        byte[] uploadBytes = file.getBytes(); 
        MessageDigest md5 = MessageDigest.getInstance("MD5"); 
        byte[] digest = md5.digest(uploadBytes); 
        String hashString = new BigInteger(1, digest).toString(16); 
        return hashString.toUpperCase();
    }

    //获取文件的后缀名
    public static String getFileSuffix(String fileName){
        String fileSuffix="";
        if(fileName.indexOf(".")!=-1){
            return fileSuffix=fileName.substring(fileName.lastIndexOf(".")+1,fileName.length());
        }else{
            return fileSuffix;
        }
    }


}
