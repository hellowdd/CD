package com.bocom.web.controller.widget;

import com.bocom.service.WidgetInfoService;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.*;

@Controller
@RequestMapping("/api/file")
public class FileController {

    @Autowired
    private WidgetInfoService widgetInfoService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    String savePath = Thread.currentThread().getContextClassLoader().getResource("/").getPath();

    @PostMapping("/checkFile")
    @ResponseBody
    public Map checkFile(HttpServletRequest request, HttpServletResponse response) {
        try {
            logger.info("savepath==============》" + savePath);
            String fileMd5 = request.getParameter("fileMd5");
            String chunk = request.getParameter("chunk");
            String chunkSize = request.getParameter("chunkSize");

            File checkFile = new File(savePath + "/" + fileMd5 + "/" + chunk);

            response.setContentType("text/html;charset=utf-8");
            //检查文件是否存在，且大小是否一致
            if (checkFile.exists() && checkFile.length() == Integer.parseInt(chunkSize)) {
                //上传过
                response.getWriter().write("{\"ifExist\":1}");
            } else {
                //没有上传过
                response.getWriter().write("{\"ifExist\":0}");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @PostMapping("/mergeChunks")
    @ResponseBody
    public Map mergeChunks(HttpServletRequest request, HttpServletResponse response) {
        try {
            String directoryId = request.getParameter("directoryId");
            String content = request.getParameter("content");
            String widgetName = request.getParameter("widgetName");
            String fileMd5 = request.getParameter("fileMd5");
            //读取目录里的所有文件
            File f = new File(savePath + "/" + fileMd5);
            File[] fileArray = f.listFiles(new FileFilter() {
                //排除目录只要文件
                @Override
                public boolean accept(File pathname) {
                    // TODO Auto-generated method stub
                    if (pathname.isDirectory()) {
                        return false;
                    }
                    return true;
                }
            });

            List<File> fileList = new ArrayList<File>(Arrays.asList(fileArray));
            Collections.sort(fileList, new Comparator<File>() {
                @Override
                public int compare(File o1, File o2) {
                    // TODO Auto-generated method stub
                    if (Integer.parseInt(o1.getName()) < Integer.parseInt(o2.getName())) {
                        return -1;
                    }
                    return 1;
                }
            });

            File outputFile = new File(savePath + "/" + fileMd5 + "/" + request.getParameter("name"));
            //创建文件
            outputFile.createNewFile();
            //输出流
            FileChannel outChnnel = new FileOutputStream(outputFile).getChannel();
            //合并
            FileChannel inChannel;
            for (File file : fileList) {
                inChannel = new FileInputStream(file).getChannel();
                inChannel.transferTo(0, inChannel.size(), outChnnel);
                inChannel.close();
                //删除分片
                file.delete();
            }
            outChnnel.close();
            Map map = new HashMap();
            map.put("fileName", request.getParameter("name"));
            map.put("directoryId", directoryId);
            map.put("remarks", content);
            map.put("file", outputFile);
            map.put("widgetName", widgetName);
            map.put("fileMd5", fileMd5);
            widgetInfoService.uploadFile(map);
            //清除文件夹
            File tempFile = new File(savePath + "/" + fileMd5);
            if (tempFile.isDirectory() && tempFile.exists()) {
                tempFile.delete();
            }
            Map map1 = new HashMap();
            map1.put("succcess", true);
            return map;
        } catch (Exception e) {
            logger.info("合并文件出错{}", e);
            return null;
        }
    }

    @PostMapping("/uploadVideo")
    @ResponseBody
    public Map uploadVideo(HttpServletRequest request) {
        try {
            String chunk = request.getParameter("chunk");
            String fileMd5 = request.getParameter("fileMd5");
            //    logger.info(request.getParameter("chunk"));
            MultipartRequest re = (MultipartRequest) request;
            MultipartFile mf = re.getFile("file");
            File file = new File(savePath + "/" + fileMd5);
            if (!file.exists()) {
                file.mkdirs();
            }
            File chunkFile = new File(savePath + "/" + fileMd5 + "/" + chunk);
            FileUtils.copyInputStreamToFile(mf.getInputStream(), chunkFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
