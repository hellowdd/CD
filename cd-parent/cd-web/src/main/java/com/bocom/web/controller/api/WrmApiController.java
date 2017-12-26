package com.bocom.web.controller.api;

import com.bocom.business.WidgetInfoBusiness;
import com.bocom.dto.Office;
import com.bocom.dto.api.UserInfoPAPDto;
import com.bocom.dto.session.SessionUserInfo;
import com.bocom.dto.session.SessionUserInfoDto;
import com.bocom.service.user.UserRestService;
import com.bocom.util.Json2Map;
import com.bocom.util.JsonUtil;
import com.bocom.util.ResponseBaseUtil;
import com.bocom.util.ResponseVo;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@Controller
//@RequestMapping("/manager/api/wrm")
@RequestMapping("/api/wrm")
public class WrmApiController {

    private static final Logger logger = LoggerFactory
            .getLogger(WrmApiController.class);

    @Autowired
    private WidgetInfoBusiness widgetInfoBusiness;
    @Autowired
    private UserRestService userRestService;
    @Value("${project.appid}")
    private String appid;
    @Value("${project.app.version}")
    private String appVersion;


    /**
     * 上传文件，暴露给其他系统
     */
    @RequestMapping("/uploadWidget")
    @ResponseBody
    public String unloadWidget(@RequestBody Map map) {
        try {
            map.put("directoryId", "-1");
            byte[] b = getBASE64Decode(map.get("fileByte").toString());
            map.put("fileByte", b);
            ResponseVo vo = widgetInfoBusiness.uploadWidgetInfoBySession(map);
            return getBASE64(JsonUtil.toJSon(vo));
        } catch (Exception e) {
            logger.error("系统异常{}", e);
            return ResponseBaseUtil.fail("系统异常:".concat(e.getMessage()), e);
        }
    }

    /**
     * 上传文件，暴露给其他系统
     */
    @RequestMapping("/uploadWidgetByUser")
    @ResponseBody
    public String unloadWidgetByUser(@RequestBody Map map) {
        try {
            map.put("directoryId", "-1");
            String userName = (String) map.get("userName");
            SessionUserInfo sessionUserInfo = userRestService.getUserInfoFromPAP(new UserInfoPAPDto("PAC", "1.0.0",
                    userName, ""));
            if (null != sessionUserInfo && sessionUserInfo.getUserId() != null) {
//                map.put("userId", sessionUserInfo.getUserId());
//                map.put("userName", userName);
                byte[] b = getBASE64Decode(map.get("fileByte").toString());
                map.put("fileByte", b);
                ResponseVo vo = widgetInfoBusiness.uploadWidgetInfo(map, sessionUserInfo);
                return getBASE64(JsonUtil.toJSon(vo));
            } else {
                return ResponseBaseUtil.fail("无此用户:", null);
            }

            //return ResponseBaseUtil.fail("卸载失败", "");
        } catch (Exception e) {
            logger.error("系统异常{}", e);
            return ResponseBaseUtil.fail("系统异常:".concat(e.getMessage()), e);
        }
    }

    /**
     * 下载文件，暴露给其他系统
     */
    @RequestMapping("/downloadWidget")
    @ResponseBody
    public String downloadWidget(@RequestBody Map map) {
        try {
            String userName = (String) map.get("userName");
            SessionUserInfo sessionUserInfo = userRestService.getUserInfoFromPAP(new UserInfoPAPDto("WRM", "0.0.1",
                    userName, ""));
            if (null != sessionUserInfo && sessionUserInfo.getUserId() != null) {
                map.put("from", "interface");
                ResponseVo vo = widgetInfoBusiness.downloadWidgetInfo(map, sessionUserInfo);
                //getBASE64(JsonUtil.toJSon(vo));
                Map mapResult = (Map) vo.getData();
                byte[] b = (byte[]) mapResult.get("fileByte");
                Base64 base = new Base64();
                String bb = base.encodeAsString(b);
                mapResult.put("fileByte", bb);
                vo.setData(mapResult);
                return getBASE64(JsonUtil.toJSon(vo));
            } else {
                return ResponseBaseUtil.fail("无此用户:", null);
            }
        } catch (Exception e) {
            logger.error("系统异常{}", e);
            return ResponseBaseUtil.fail("下载失败:".concat(e.getMessage()), e);
        }
    }

    public static String getBASE64(String s) {
        if (s == null)
            return null;
        return (new sun.misc.BASE64Encoder()).encode(s.getBytes());
    }

    public static byte[] getBASE64Decode(String s) throws IOException {
        if (s == null)
            return null;
        return (new sun.misc.BASE64Decoder()).decodeBuffer(s);
    }

    /**
     * 上传文件，暴露给其他系统
     */
    @RequestMapping("/uploadWidgetByMulti")
    @ResponseBody
    public String unloadWidgetByMulti(MultipartRequest request, String uploadInfo) {
        try {
            //Map map = new HashMap();
            Map map = Json2Map.toHashMap(uploadInfo);
            map.put("directoryId", "-1");
            String userName = (String) map.get("userName");
//            String userName = "huamulan";
            //获取文件信息并保存map
            MultipartFile file = request.getFile("filedata");
            map.put("fileName", file.getOriginalFilename());
            map.put("widgetSize", new Long(file.getSize()).intValue());
            map.put("fileByte", file.getBytes());
//            request.get
            SessionUserInfo sessionUserInfo = userRestService.getUserInfoFromPAP(new UserInfoPAPDto("PAC", "1.0.0",
                    userName, ""));
            if (null != sessionUserInfo && sessionUserInfo.getUserId() != null) {
//                map.put("userId", sessionUserInfo.getUserId());
//                map.put("userName", userName);
                ResponseVo vo = widgetInfoBusiness.uploadWidgetInfo(map, sessionUserInfo);
                return getBASE64(JsonUtil.toJSon(vo));
            } else {
                return ResponseBaseUtil.fail("无此用户:", null);
            }
        } catch (Exception e) {
            logger.error("系统异常{}", e);
            return ResponseBaseUtil.fail("系统异常:".concat(e.getMessage()), e);
        }
    }

    /**
     * 下载文件，暴露给其他系统
     */
    @RequestMapping("/downloadWidgetBySession")
    @ResponseBody
    public void downloadWidget(HttpServletRequest request, HttpServletResponse response) {
        try {
            String userName = (String) request.getAttribute("userName");
            Map map = new HashMap();
            map.put("widgetId", (int) request.getAttribute("userName"));
            SessionUserInfo sessionUserInfo = userRestService.getUserInfoFromPAP(new UserInfoPAPDto("WRM", "0.0.1",
                    userName, ""));
            if (null != sessionUserInfo && sessionUserInfo.getUserId() != null) {

                ResponseVo vo = widgetInfoBusiness.downloadWidgetInfo(map, sessionUserInfo);
                Map mapResult = (Map) vo.getData();
                byte[] b = (byte[]) mapResult.get("fileByte");
                int length = b.length;
                OutputStream os = response.getOutputStream();
                while (length > 0) {
                    os.write(b, 0, length);
                }
//                Base64 base = new Base64();
//                String bb = base.encodeAsString(b);
//                mapResult.put("fileByte", bb);
//                vo.setData(mapResult);
//                return getBASE64(JsonUtil.toJSon(vo));
            } else {
//                return ResponseBaseUtil.fail("无此用户:",null);
            }
        } catch (Exception e) {
            logger.error("系统异常{}", e);
//            return ResponseBaseUtil.fail("卸载失败:".concat(e.getMessage()), e);
        }
    }

    /**
     * 更新控件，暴露给其他系统
     */
    @RequestMapping("/updateWidgetByMulti")
    @ResponseBody
    public String undateWidget(MultipartRequest request, String uploadInfo) {
        try {
            Map map = Json2Map.toHashMap(uploadInfo);
            String userName = (String) map.get("userName");
            //获取文件信息并保存map
            MultipartFile file = request.getFile("filedata");
            map.put("fileName", file.getOriginalFilename());
            map.put("widgetSize", new Long(file.getSize()).intValue());
            map.put("fileByte", file.getBytes());
//            request.get
            SessionUserInfo sessionUserInfo = userRestService.getUserInfoFromPAP(new UserInfoPAPDto("PAC", "1.0.0",
                    userName, ""));
            if (null != sessionUserInfo && sessionUserInfo.getUserId() != null) {
                ResponseVo vo = widgetInfoBusiness.updateWidgetInfo(map, sessionUserInfo);
                return getBASE64(JsonUtil.toJSon(vo));
            } else {
                return ResponseBaseUtil.fail("无此用户:", null);
            }
        } catch (Exception e) {
            logger.error("系统异常{}", e);
            return ResponseBaseUtil.fail("系统异常:".concat(e.getMessage()), e);
        }
    }

    /**
     * 更新控件-纯二进制，暴露给其他系统
     */
    @RequestMapping("/updateWidget")
    @ResponseBody
    public String undateWidget(@RequestBody Map map) {
        try {
            String userName = (String) map.get("userName");
            SessionUserInfo sessionUserInfo = userRestService.getUserInfoFromPAP(new UserInfoPAPDto("PAC", "1.0.0",
                    userName, ""));
            if (null != sessionUserInfo && sessionUserInfo.getUserId() != null) {
                byte[] b = getBASE64Decode(map.get("fileByte").toString());
                map.put("fileByte", b);
                ResponseVo vo = widgetInfoBusiness.updateWidgetInfo(map, sessionUserInfo);
                return getBASE64(JsonUtil.toJSon(vo));
            } else {
                return ResponseBaseUtil.fail("无此用户:", null);
            }
        } catch (Exception e) {
            logger.error("系统异常{}", e);
            return ResponseBaseUtil.fail("系统异常:".concat(e.getMessage()), e);
        }
    }

    /**
     * 控件列表，暴露给其他系统
     */
    @RequestMapping("/getWidgetList")
    @ResponseBody
    public String getWidgetList(@RequestBody Map map) {
        try {
            String userName = (String) map.get("userName");
            //验证是否有此用户
            SessionUserInfo sessionUserInfo = userRestService.getUserInfoFromPAP(new UserInfoPAPDto("PAC", "1.0.0",
                    userName, ""));
            if (null != sessionUserInfo && sessionUserInfo.getUserId() != null) {
                SessionUserInfoDto req = new SessionUserInfoDto();
                req.setUserName(userName);
                Office office = userRestService.getOrgInfoFromPAP(req);
                Map<String, Object> widgetInfoListMap = new HashMap<String, Object>();
                int pageNum = (int) map.get("pageNum");
                int pageSize = (int) map.get("pageSize");
                Integer widgetTypeId = (Integer) map.get("widgetTypeId");
                Integer appType = (Integer) map.get("appType");
                map.put("from", "interface");
                map.put("pageStart", (pageNum - 1) * pageSize);
                map.put("pageSize", pageSize);
                map.put("widgetTypeId", widgetTypeId);
                map.put("appType", appType);
                map.put("shareRange", office.getId());
                widgetInfoListMap.put("data", widgetInfoBusiness.getWidgetInfoList(map));
                widgetInfoListMap.put("count", widgetInfoBusiness.getWidgetInfoListCount(map));

                return ResponseBaseUtil.success(widgetInfoListMap);
            } else {
                return ResponseBaseUtil.fail("无此用户:", null);
            }
        } catch (Exception e) {
            logger.error("系统异常{}", e);
            return ResponseBaseUtil.fail("系统异常:".concat(e.getMessage()), e);
        }
    }

    /**
     * 发布调用接口，暴露给其他系统，
     * 用于外系统发布一次，控件发布次数加1
     */
    @RequestMapping("/addRate")
    @ResponseBody
    public String addRate(@RequestBody Map map) {
        try {
            String userName = (String) map.get("userName");
            SessionUserInfo sessionUserInfo = userRestService.getUserInfoFromPAP(new UserInfoPAPDto("WRM", "0.0.1",
                    userName, ""));
            if (null != sessionUserInfo && sessionUserInfo.getUserId() != null) {
                int widgetId = (int) map.get("widgetId");
                ResponseVo vo = widgetInfoBusiness.addRate(widgetId);
                return getBASE64(JsonUtil.toJSon(vo));
            } else {
                return ResponseBaseUtil.fail("无此用户:", null);
            }
        } catch (Exception e) {
            logger.error("系统异常{}", e);
            return ResponseBaseUtil.fail("系统异常:".concat(e.getMessage()), e);
        }
    }

}
