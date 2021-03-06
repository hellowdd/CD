package com.bocom.web.controller.widget;

import com.bocom.business.WidgetInfoBusiness;
import com.bocom.dto.session.SessionUserInfo;
import com.bocom.service.WidgetInfoService;
import com.bocom.util.FileUtil;
import com.bocom.util.ResponseVo;
import com.bocom.util.ResponseVoUtil;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/*****
 * <pre>
 * 类名称：WidgetController
 * 类描述：控件相关控制类
 * 创建人：donghongguang
 * 创建时间：2017年3月22日 上午10:29:01
 * 修改人：
 * 修改时间：
 * </pre>
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping("/manager/widget")
public class WidgetController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HttpServletRequest request; // 这里可以获取到request

    @Autowired
    private WidgetInfoBusiness widgetInfoBusiness;

    @Autowired
    private WidgetInfoService widgetInfoService;

    /*****
     * 功能：获取上传的控件列表 创建人：donghongguang 创建时间：2017年3月22日 下午3:10:24
     *
     * @param
     * @return
     * @version 1.0.0
     * @throws InterruptedException
     */
    @RequestMapping("/getWidgetList")
    @ResponseBody
    public Map getWidgetInfoList(
            @RequestParam(required = false) Integer directoryId,
            @RequestParam(required = false) Integer pageNum,
            @RequestParam(required = false) Integer pageSize)
            throws InterruptedException {

        Map<String, Object> widgetInfoMap = new HashMap<String, Object>();
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            // 系统编码
            map.put("directoryId", directoryId);
            map.put("pageStart", (pageNum - 1) * pageSize);
            map.put("pageSize", pageSize);
            // widgetInfoBusiness.getWidgetInfoList(null);
            widgetInfoMap.put("success", true);
            // 调用business获取控件列表
            widgetInfoMap
                    .put("data", widgetInfoBusiness.getWidgetInfoList(map));
            widgetInfoMap.put("count",
                    widgetInfoBusiness.getWidgetInfoListCount(map));
        } catch (Exception e) {
            logger.error("系统异常{}", e);
            widgetInfoMap.put("success", false);
            widgetInfoMap.put("message", "异常:".concat(e.getMessage()));
            return widgetInfoMap;
        }

        return widgetInfoMap;
    }

    /*****
     * 功能：上传控件 创建人：donghongguang 创建时间：2017年3月22日 下午3:13:31
     *
     * @param directoryId
     *            保存路径
     * @param content
     *            说明
     * @return
     * @version 1.0.0
     */
    @RequestMapping("/uploadWidget")
    @ResponseBody
    public ResponseVo uploadWidgetInfo(
            @RequestParam("inputfile") MultipartFile file,
            @RequestParam("directoryId") String directoryId,
            @RequestParam("content") String content,
            @RequestParam("widgetName") String widgetName,
            HttpServletRequest request) {
        // 修改为上传成功重定向
        try {
            logger.info("进入uploadWidgetInfo方法");
            Map<String, String> failMap = new HashMap<String, String>();
            CommonsMultipartFile cf = (CommonsMultipartFile) file;
            DiskFileItem fi = (DiskFileItem) cf.getFileItem();
            File f = fi.getStoreLocation();
            byte[] fileByte = file.getBytes();
            String fileName = file.getOriginalFilename();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("fileName", fileName);
            logger.info("文件名为:" + fileName);
            map.put("fileByte", fileByte);
            map.put("directoryId", directoryId);
            map.put("remarks", content);
            map.put("file", file);
            return widgetInfoService.uploadFile(map);
        } catch (Exception e) {
            logger.info("异常：" + e.getMessage());
            return ResponseVoUtil.fail("异常:".concat(e.getMessage()), e);
        }

    }


    /*****
     * 功能：更新控件 创建人：donghongguang 创建时间：2017年3月22日 下午3:13:31
     *
     * @param directoryId
     *            保存路径
     * @param content
     *            说明
     * @return
     * @version 1.0.0
     */
    @RequestMapping("/updateWidget")
    @ResponseBody
    public ResponseVo undateWidget(
            @RequestParam("inputfile") MultipartFile file,
            @RequestParam("widgetId") String widgetId,
            @RequestParam("content") String content,
            @RequestParam(required = false) String widgetName,
            HttpServletRequest request) {

        try {
            logger.info("进入updateWidget方法");
            HttpSession session = request.getSession();
            SessionUserInfo userInfo = (SessionUserInfo) session
                    .getAttribute("sessionUserInfo");

            Map<String, String> failMap = new HashMap<String, String>();
            if (!file.isEmpty()) {
                byte[] fileByte = file.getBytes();
                String fileName = file.getOriginalFilename();
                int fileSize = new Long(file.getSize()).intValue(); // file.getSize()
                // + "";
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("fileName", fileName);
                logger.info("文件名为:" + fileName);
                map.put("widgetSize", fileSize);
                map.put("fileByte", fileByte);
                map.put("widgetId", widgetId);
                map.put("remarks", content);
                if (org.apache.commons.lang.StringUtils.isNotEmpty(widgetName)) {
                    map.put("widgetNameShow", widgetName);
                }
                ResponseVo response = widgetInfoBusiness.updateWidgetInfo(
                        map, userInfo);
                return response;
            } else {
                // 上传文件为空
                failMap.put("type", "2");
                return ResponseVoUtil.fail("上传控件为空", failMap);
            }

        } catch (Exception e) {
            logger.info("异常：" + e.getMessage());
            return ResponseVoUtil.fail("异常:".concat(e.getMessage()), e);
        }
    }

    /*****
     * 功能：更新控件基本信息 创建人：donghongguang 创建时间：2017年3月22日 下午3:13:31
     *
     * @param directoryId
     *            保存路径
     * @param content
     *            说明
     * @return
     * @version 1.0.0
     */
    @RequestMapping("/updateWidgetInfoBase")
    @ResponseBody
    public ResponseVo updateWidgetInfoBase(
            @RequestParam(value = "widgetId", required = true) Integer widgetId,
            @RequestParam(value = "directoryId", required = true) Integer directoryId,
            @RequestParam(value = "widgetNameShow", required = false) String widgetNameShow,
            @RequestParam(value = "widgetTypeId", required = false) Integer widgetTypeId,
            @RequestParam(value = "remarks", required = false) String remarks,
            HttpServletRequest request) {

        try {
            logger.info("进入updateWidgetInfoBase方法");
            HttpSession session = request.getSession();
            SessionUserInfo userInfo = (SessionUserInfo) session
                    .getAttribute("sessionUserInfo");

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("widgetId", widgetId);
            map.put("directoryId", directoryId);
            //map.put("widgetNameShow", widgetNameShow);
            //map.put("widgetTypeId", widgetTypeId);
            //map.put("remarks", remarks);
            ResponseVo response = widgetInfoBusiness.updateWidgetInfoBase(map,
                    userInfo);
            return response;
        } catch (Exception e) {
            logger.info("异常：" + e.getMessage());
            return ResponseVoUtil.fail("异常:".concat(e.getMessage()), e);
        }
    }

    /*****
     * 功能：下载控件 创建人：donghongguang 创建时间：2017年4月5日 上午9:48:53
     *
     * @param
     * @return
     * @version 1.0.0
     */
    @RequestMapping("/downloadWidget")
    public void downloadWidgetInfo(
            @RequestParam(value = "widgetId", required = true) Integer widgetId,
            @RequestParam(value = "widgetName", required = false) String widgetName,
            HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            SessionUserInfo userInfo = (SessionUserInfo) session
                    .getAttribute("sessionUserInfo");
            Map map = new HashMap();
            map.put("widgetId", widgetId);
            ResponseVo vo = widgetInfoBusiness
                    .downloadWidgetInfo(map, userInfo);
            if (vo.getSuccess()) {
                Map mapResult = (Map) vo.getData();

                // 获取文件的二进制数组
                byte[] buffer = (byte[]) mapResult.get("fileByte");

                OutputStream toClient = new BufferedOutputStream(
                        response.getOutputStream());
                // 设置response的Header
                response.setContentType("application/x-msdownload");
                // 如果输出的是中文名的文件，在此处就要用URLEncoder.encode方法进行处理
                response.setHeader(
                        "Content-Disposition",
                        "attachment;filename="
                                + URLEncoder.encode(widgetName, "UTF-8"));
                toClient.write(buffer);
                toClient.flush();
                toClient.close();
            } else {
                response.setContentType("text/html; charset=UTF-8"); // 转码
                PrintWriter out = response.getWriter();
                out.flush();
                out.println("<script>");
                out.println("alert('" + vo.getMessage() + "');");
                // out.println("history.back();");
                out.println("</script>");
            }

        } catch (Exception e) {
            logger.error("系统异常{}", e);
        }
    }

    /*****
     * 功能：删除控件 创建人：donghongguang 创建时间：2017年4月5日 上午9:49:44
     *
     * @param
     * @return
     * @version 1.0.0
     */
    @RequestMapping("/deleteWidget")
    @ResponseBody
    public ResponseVo deleteWidget(
            @RequestParam("widgetPath") String widgetPath,
            @RequestParam("widgetId") String widgetId) {
        try {
            if (widgetInfoBusiness.deleteWidget(widgetPath, widgetId) > 0) {
                return ResponseVoUtil.success("");
            } else {
                return ResponseVoUtil.fail("请重新尝试！", "");
            }

        } catch (Exception e) {
            logger.error("系统异常{}", e);
            return ResponseVoUtil.fail("异常:".concat(e.getMessage()), e);
        }

    }

    /*****
     * 功能：共享控件 创建人：donghongguang 创建时间：2017年3月22日 下午3:10:24
     *
     * @param
     * @return
     * @version 1.0.0
     */
    @RequestMapping("/shareWidgetInfo")
    @ResponseBody
    public ResponseVo shareWidgetInfo(
            @RequestParam(value = "widgetId", required = true) Integer widgetId,
            @RequestParam("shareRange") String shareRange) {
        try {
            Map map = new HashMap();
            map.put("widgetId", widgetId);
            map.put("shareRange", shareRange);
            return widgetInfoBusiness.shareWidgetInfo(map);

        } catch (Exception e) {
            logger.error("系统异常{}", e);
            return ResponseVoUtil.fail("异常:".concat(e.getMessage()), e);
        }

    }

    /*****
     * 功能：取消共享控件 创建人：donghongguang 创建时间：2017年3月22日 下午3:10:24
     *
     * @param
     * @return
     * @version 1.0.0
     */
    @RequestMapping("/unshareWidgetInfo")
    @ResponseBody
    public ResponseVo unshareWidgetInfo(
            @RequestParam(value = "widgetId", required = false) Integer widgetId) {
        try {
            Map map = new HashMap();
            map.put("widgetId", widgetId);
            return widgetInfoBusiness.unshareWidgetInfo(map);

        } catch (Exception e) {
            logger.error("系统异常{}", e);
            return ResponseVoUtil.fail("异常:".concat(e.getMessage()), e);
        }

    }

    /*****
     * 功能：获取共享的控件列表 创建人：donghongguang 创建时间：2017年3月22日 下午3:10:24
     *
     * @param
     * @return
     * @version 1.0.0
     */
    @RequestMapping("/getWidgetShareList")
    @ResponseBody
    public Map getWidgetInfoShareList(
            @RequestParam(required = false) Integer pageNum,
            @RequestParam(required = false) Integer pageSize) {
        Map<String, Object> widgetInfoMap = new HashMap<String, Object>();
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            // 系统编码
            map.put("pageStart", (pageNum - 1) * pageSize);
            map.put("pageSize", pageSize);

            // 调用business获取控件列表4166.66
            widgetInfoMap.put("data",
                    widgetInfoBusiness.getWidgetInfoShareList(map));
            widgetInfoMap.put("count",
                    widgetInfoBusiness.getWidgetInfoShareListCount(map));
            widgetInfoMap.put("success", true);
        } catch (Exception e) {
            logger.error("系统异常{}", e);
            widgetInfoMap.put("success", false);
            widgetInfoMap.put("message", "异常:".concat(e.getMessage()));
            return widgetInfoMap;
        }

        return widgetInfoMap;
    }

    /*****
     * 功能：企业上传控件统计排名 创建人：donghongguang 创建时间：2017年5月19日 下午3:10:24
     *
     * @param
     * @return
     * @version 1.0.0
     * @throws InterruptedException
     */
    @RequestMapping("/getWidgetNum")
    @ResponseBody
    public Map getWidgetNum(@RequestParam(required = false) Integer pageNum,
                            @RequestParam(required = false) Integer pageSize) {
        Map<String, Object> widgetInfoMap = new HashMap<String, Object>();
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            if (null == pageNum)
                pageNum = 1;
            if (null == pageSize)
                pageSize = Integer.MAX_VALUE;
            map.put("pageStart", (pageNum - 1) * pageSize);
            map.put("pageSize", pageSize);
            widgetInfoMap.put("success", true);
            // 调用business获取列表
            widgetInfoMap.put("data", widgetInfoBusiness.getWidgetNum(map));
            // widgetInfoMap.put("count",
            // widgetInfoBusiness.getWidgetInfoListCount(map));
        } catch (Exception e) {
            logger.error("系统异常{}", e);
            widgetInfoMap.put("success", false);
            widgetInfoMap.put("message", "异常:".concat(e.getMessage()));
            return widgetInfoMap;
        }
        return widgetInfoMap;
    }

    /*****
     * 功能：企业控件总发布次数统计排名 创建人：donghongguang 创建时间：2017年5月19日 下午3:10:24
     *
     * @param
     * @return
     * @version 1.0.0
     * @throws InterruptedException
     */
    @RequestMapping("/getWidgetAllReleaseNum")
    @ResponseBody
    public Map getWidgetAllReleaseNum(
            @RequestParam(required = false) Integer pageNum,
            @RequestParam(required = false) Integer pageSize) {
        Map<String, Object> widgetInfoMap = new HashMap<String, Object>();
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            if (null == pageNum)
                pageNum = 1;
            if (null == pageSize)
                pageSize = Integer.MAX_VALUE;
            map.put("pageStart", (pageNum - 1) * pageSize);
            map.put("pageSize", pageSize);
            widgetInfoMap.put("success", true);
            // 调用business获取列表
            widgetInfoMap.put("data",
                    widgetInfoBusiness.getWidgetAllReleaseNum(map));
            // widgetInfoMap.put("count",
            // widgetInfoBusiness.getWidgetInfoListCount(map));
        } catch (Exception e) {
            logger.error("系统异常{}", e);
            widgetInfoMap.put("success", false);
            widgetInfoMap.put("message", "异常:".concat(e.getMessage()));
            return widgetInfoMap;
        }
        return widgetInfoMap;
    }

    /*****
     * 功能：企业控件发布下载次数统计排名 创建人：donghongguang 创建时间：2017年5月19日 下午3:10:24
     *
     * @param
     * @return
     * @version 1.0.0
     * @throws InterruptedException
     */
    @RequestMapping("/getWidgetReleaseNum")
    @ResponseBody
    public Map getWidgetReleaseNum(
            @RequestParam(required = false) Integer uploadUserid,
            @RequestParam(required = false) Integer pageNum,
            @RequestParam(required = false) Integer pageSize) {
        Map<String, Object> widgetInfoMap = new HashMap<String, Object>();
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            if (null == pageNum)
                pageNum = 1;
            if (null == pageSize)
                pageSize = Integer.MAX_VALUE;
            map.put("pageStart", (pageNum - 1) * pageSize);
            map.put("uploadUserid", uploadUserid);
            map.put("pageSize", pageSize);
            widgetInfoMap.put("success", true);
            // 调用business获取列表
            widgetInfoMap.put("data",
                    widgetInfoBusiness.getWidgetReleaseNum(map));
            widgetInfoMap.put("count",
                    widgetInfoBusiness.getWidgetReleaseNumCount(map));
        } catch (Exception e) {
            logger.error("系统异常{}", e);
            widgetInfoMap.put("success", false);
            widgetInfoMap.put("message", "异常:".concat(e.getMessage()));
            return widgetInfoMap;
        }
        return widgetInfoMap;
    }

    /*****
     * 功能：按类型统计 控件量 创建人：donghongguang 创建时间：2017年5月19日 下午3:10:24
     *
     * @param
     * @return
     * @version 1.0.0
     * @throws InterruptedException
     */
    @RequestMapping("/getWidgetNumByType")
    @ResponseBody
    public Map getWidgetNumByType(
            @RequestParam(required = false) Integer pageNum,
            @RequestParam(required = false) Integer pageSize) {
        Map<String, Object> widgetInfoMap = new HashMap<String, Object>();
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            if (null == pageNum)
                pageNum = 1;
            if (null == pageSize)
                pageSize = Integer.MAX_VALUE;
            map.put("pageStart", (pageNum - 1) * pageSize);
            map.put("pageSize", pageSize);
            widgetInfoMap.put("success", true);
            // 调用business获取列表
            widgetInfoMap.put("data",
                    widgetInfoBusiness.getWidgetNumByType(map));
            // widgetInfoMap.put("count",
            // widgetInfoBusiness.getWidgetInfoListCount(map));
        } catch (Exception e) {
            logger.error("系统异常{}", e);
            widgetInfoMap.put("success", false);
            widgetInfoMap.put("message", "异常:".concat(e.getMessage()));
            return widgetInfoMap;
        }
        return widgetInfoMap;
    }
}
