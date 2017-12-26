package com.bocom.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FileType {
    
    public final static Map<String, String> FILE_TYPE_MAP = new HashMap<String, String>();     
    
    private FileType(){}     
    static{     
        getAllFileType(); //初始化文件类型信息     
    }     
         
    /**   
     * Discription:[getAllFileType,常见文件头信息] 
     */     
    private static void getAllFileType()     
    {     //1、文档   2、图片  3、音视频   4、压缩文件
        FILE_TYPE_MAP.put("doc", "1");     
        FILE_TYPE_MAP.put("docx", "1");     
        FILE_TYPE_MAP.put("xls", "1");     
        FILE_TYPE_MAP.put("xlsx", "1");     
        FILE_TYPE_MAP.put("ppt", "1");     
        FILE_TYPE_MAP.put("pptx", "1");     
        FILE_TYPE_MAP.put("txt", "1");     
        FILE_TYPE_MAP.put("sql", "1");     
        FILE_TYPE_MAP.put("pdf", "1");     
        FILE_TYPE_MAP.put("wps", "1");     
        FILE_TYPE_MAP.put("rtf", "1");     
        FILE_TYPE_MAP.put("html", "1");  
        
        FILE_TYPE_MAP.put("bmp", "2");     
        FILE_TYPE_MAP.put("gif", "2");     
        FILE_TYPE_MAP.put("jpg", "2");     
        FILE_TYPE_MAP.put("jpeg", "2");     
        FILE_TYPE_MAP.put("pic", "2");     
        FILE_TYPE_MAP.put("png", "2");     
        FILE_TYPE_MAP.put("tif", "2");     
        FILE_TYPE_MAP.put("svg", "2");     
        FILE_TYPE_MAP.put("psd", "2");  
        
        FILE_TYPE_MAP.put("mp3", "3");     
        FILE_TYPE_MAP.put("mp4", "3");     
        FILE_TYPE_MAP.put("avi", "3");     
        FILE_TYPE_MAP.put("wma", "3");     
        FILE_TYPE_MAP.put("wav", "3");     
        FILE_TYPE_MAP.put("mid", "3");     
        FILE_TYPE_MAP.put("rm", "3");     
        FILE_TYPE_MAP.put("mov", "3");  
        FILE_TYPE_MAP.put("amr", "3");  
        FILE_TYPE_MAP.put("ram", "3");  
        FILE_TYPE_MAP.put("swf", "3");  
        FILE_TYPE_MAP.put("mpg", "3");  
        
        FILE_TYPE_MAP.put("zip", "4");     
        FILE_TYPE_MAP.put("rar", "4");     
        FILE_TYPE_MAP.put("7z", "4");     
        FILE_TYPE_MAP.put("cab", "4");     
        FILE_TYPE_MAP.put("iso", "4");     
         
    }                       
    
    /**
     * 根据制定文件的文件头判断其文件类型
     * @param filePaht
     * @return
     */
    public static String getFileType(String extension){
        String res = null;
        try {
            //这种方法在字典的头代码不够位数的时候可以用但是速度相对慢一点
            Iterator<String> keyIter = FILE_TYPE_MAP.keySet().iterator();
            while(keyIter.hasNext()){
                String key = keyIter.next();
                if(key.toLowerCase().startsWith(extension.toLowerCase()) || extension.toLowerCase().startsWith(key.toLowerCase())){
                    res = FILE_TYPE_MAP.get(key);
                    break;
                }
            }
            if(res == null){
                res="0";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public static void main(String[] args) throws Exception {
        
        String type = getFileType("WMV");
        System.out.println("WMV : "+type);
        
        type = getFileType("zip");
        System.out.println("wav : "+type);
                
    }
}