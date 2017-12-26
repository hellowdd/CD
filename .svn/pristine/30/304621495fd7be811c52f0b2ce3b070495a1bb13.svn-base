package com.bocom.business.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bocom.business.WidgetInfoBusiness;
import com.bocom.domain.DirectoryWidget;
import com.bocom.domain.SpaceManage;
import com.bocom.domain.WidgetExtendInfo;
import com.bocom.domain.WidgetInfo;
import com.bocom.dto.session.SessionUserInfo;
import com.bocom.service.DirectoryWidgetService;
import com.bocom.service.SpaceManageService;
import com.bocom.service.WidgetInfoService;
import com.bocom.util.DateUtil;
import com.bocom.util.FastDfsUtil;
import com.bocom.util.FileType;
import com.bocom.util.FormatUtils;
import com.bocom.util.ResponseVo;
import com.bocom.util.ResponseVoUtil;

/*****
 * 类名称：WidgetInfoBusinessImpl
 * 类描述：文件相关信息业务处理类
 * 创建人：donghongguang
 * 创建时间：2017年3月22日 下午4:12:45
 * 修改人：
 * 修改时间：
 * </pre>
 * 
 * @version 1.0.0
 */
@Component
public class WidgetInfoBusinessImpl extends BaseSerivceImpl implements WidgetInfoBusiness
{
    private Logger                 logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private WidgetInfoService      widgetInfoService;
    @Autowired
    private DirectoryWidgetService directoryWidgetService;
    @Autowired
    private SpaceManageService spaceManageService;
    
    @Value("${fastDFS.http.url}")
    private String                 httpUrl;
    
    /****
     * @see com.bocom.business.WidgetInfoBusiness#uploadDeployFile(java.util.Map)
     *      <pre>
     * 文件上传
     * 创建人：donghongguang
     * 创建时间：2017年3月22日 下午5:11:53
     * </pre>
     * @version 1.0.0
     */
    @Override
    public ResponseVo uploadWidgetInfoBySession(Map map)
    {
        /**
         * 从session中获取user信息
         * 赋值user信息到map
         * 调用上传方法
         */
        // 在session中记录此时的文件上传状态为上传中
        HttpSession session = request.getSession();
        SessionUserInfo userInfo = (SessionUserInfo) session.getAttribute("sessionUserInfo");
        // 获取用户ID，即为上传人Id
        // int uploadUserId = userInfo.getUserId();
        // String userName = userInfo.getPoliceName();
        // map.put("userId", uploadUserId);
        // map.put("userName", userName);
        ResponseVo response = uploadWidgetInfo(map, userInfo);
        // super.saveLog("上传文件", "上传文件"+map.get("fileName"));
        return response;
    }
    
    /*****
     * 功能：查询上传的文件列表
     * 创建人：donghongguang
     * 创建时间：2017年3月23日 上午11:15:28
     * 
     * @param
     * @return
     * @version 1.0.0
     */
    @Override
    public List<Map> getWidgetInfoList(Map map)
    {
        
        String from = (String) map.get("from");
        if(!"interface".equals(from)){
            SessionUserInfo session = (SessionUserInfo) request.getSession().getAttribute("sessionUserInfo");
            if(session != null){
                if (StringUtils.isNotBlank(session.getOrgCode()))
                {
                    map.put("orgCode", session.getOrgCode());
                    map.put("uploadUserid", session.getUserId());
                    map.put("uploadUserName", session.getUserName());
                }
                if(StringUtils.isNotBlank(session.getUserRoles().get(0).getRoleCode()) && session.getUserRoles().get(0).getRoleCode().equals("2")){//管理员用户
                    map.put("uploadUserid", null);
                }else
                map.put("uploadUserid", session.getUserId());
            }
              
        }
        List<Map> list = widgetInfoService.getWidgetInfoList(map);

        // 获得storage的服务器地址
        
        // 修改storage存储路径为全路径，看是否需要这一步骤
        for (Map widgetMap : list)
        {
            String storagePath = (String) widgetMap.get("storage_path");
            // 从配置文件中获取nginx的地址 拼接全路径
            widgetMap.put("storagePath", httpUrl + File.separator + storagePath);
            String type = FileType.getFileType((String)widgetMap.get("widget_extension"));
            widgetMap.put("fileType",type);
            
        }
        return list;
    }
    /*****
     * 功能：查询上传的文件列表数量
     * 创建人：donghongguang
     * 创建时间：2017年3月23日 上午11:15:28
     * @param 
     * @return 
     * @version 1.0.0
     */
    public int getWidgetInfoListCount(Map map){
        String from = (String) map.get("from");
        if(!"interface".equals(from)){
            SessionUserInfo session = (SessionUserInfo) request.getSession().getAttribute("sessionUserInfo");
            if(session != null){
                if (StringUtils.isNotBlank(session.getOrgCode()))
                {
                    map.put("orgCode", session.getOrgCode());
                    map.put("uploadUserid", session.getUserId());
                    map.put("uploadUserName", session.getUserName());
                }
                if(StringUtils.isNotBlank(session.getUserRoles().get(0).getRoleCode()) && session.getUserRoles().get(0).getRoleCode().equals("2")){//管理员用户
                    map.put("uploadUserid", null);
                }else
                map.put("uploadUserid", session.getUserId());
            } 
        }
    	
        int count = widgetInfoService.getWidgetInfoListCount(map);
        return count;
    }
    
    /*****
     * 功能：获取共享文件列表
     * 创建人：donghongguang
     * 创建时间：2017年3月23日 上午11:30:35
     * 
     * @param
     * @return
     * @version 1.0.0
     */
    @Override
    public List<Map> getWidgetInfoShareList(Map map)
    {
        SessionUserInfo session = (SessionUserInfo) request.getSession().getAttribute("sessionUserInfo");
        if (session != null && StringUtils.isNotBlank(session.getOrgCode()))
        {
//            map.put("orgCode", session.getOrgCode());
//            map.put("uploadUserid", session.getUserId());
//            map.put("uploadUserName", session.getUserName());
        }
//        map.put("uploadUserid", 75);
        map.put("isShare", 1);//表示共享
        List<Map> list = widgetInfoService.getWidgetInfoList(map);

        // 获得storage的服务器地址
        
        // 修改storage存储路径为全路径，看是否需要这一步骤
        for (Map widgetMap : list)
        {
            String storagePath = (String) widgetMap.get("storage_path");
            // 从配置文件中获取nginx的地址 拼接全路径
            widgetMap.put("storagePath", httpUrl + File.separator + storagePath);
        }
        return list;
    }
    /*****
     * 功能：查询共享的文件列表
     * 创建人：donghongguang
     * 创建时间：2017年3月23日 上午11:15:28
     * @param 
     * @return 
     * @version 1.0.0
     */
    @Override
    public int getWidgetInfoShareListCount(Map map){
        SessionUserInfo session = (SessionUserInfo) request.getSession().getAttribute("sessionUserInfo");
        /*if (session != null && StringUtils.isNotBlank(session.getOrgCode()))
        {
            map.put("orgCode", session.getOrgCode());
            map.put("uploadUserid", session.getUserId());
            map.put("uploadUserName", session.getUserName());
        }*/
//        map.put("uploadUserid", 75);
        map.put("isShare", 1);//表示共享
        int count = widgetInfoService.getWidgetInfoListCount(map);
        return count;
    }
    
    /*****
     * 功能：文件共享
     * 创建人：donghongguang
     * 创建时间：2017年3月23日 上午11:31:29
     * 
     * @param
     * @return
     * @version 1.0.0
     */
    @Override
    public ResponseVo shareWidgetInfo(Map<String ,Object> map)
    {
        /**
         * 1.获得用户信息
         * 2.查找文件
         * 3.共享文件
         */
        try
        {
            HttpSession session = request.getSession();
            SessionUserInfo userInfo = (SessionUserInfo) session.getAttribute("sessionUserInfo");
            
            // 查询是否有此文件
            int widgetId = (int) map.get("widgetId");
            WidgetInfo widget = new WidgetInfo();
            widget.setId(widgetId);
            widget.setUploadUserid(userInfo.getUserId());
            WidgetInfo widgetResult = widgetInfoService.getWidgetInfo(widget);
            if (widgetResult != null && widgetResult.getStoragePath() != null)
            {
                //共享文件,得到文件扩展表，修改共享数据
                WidgetExtendInfo widgetExtendInfo = new WidgetExtendInfo();
                widgetExtendInfo.setWidgetId(widgetId);
                widgetExtendInfo = widgetInfoService.getWidgetExtendInfo(widgetExtendInfo);
                if(widgetExtendInfo.getId() != null){
                    widgetExtendInfo.setIsShare("1");//共享为1
                    widgetExtendInfo.setShareRange((String)map.get("shareRange"));
                    widgetExtendInfo.setShareTime(new Date());
                    //修改扩展信息为共享
                    int result = widgetInfoService.updateWidgetExtendInfo(widgetExtendInfo);
                    if (result > 0)
                    {
                        super.saveLog("共享文件","共享文件：" + widgetResult.getWidgetName() , userInfo);
                        return ResponseVoUtil.success(result);
                    }else{
                        return ResponseVoUtil.fail("共享文件失败", null);
                    }
                }else{
                    return ResponseVoUtil.fail("无此权限共享文件", null);
                }
                
            }else{
                return ResponseVoUtil.fail("无此权限共享文件", null);
            }
        }
        catch (Exception e)
        {
            logger.error("系统异常{}", e);
            
        }
        return ResponseVoUtil.fail("共享文件失败", null);
        
    }
    
    /*****
     * 功能：取消文件共享
     * 创建人：donghongguang
     * 创建时间：2017年3月23日 上午11:31:29
     * @param 
     * @return 
     * @version 1.0.0
     */
    @Override
    public ResponseVo unshareWidgetInfo(Map<String ,Object> map){
        /**
         * 1.获得用户信息
         * 2.查找文件
         * 3.取消共享文件
         */
        try
        {
            HttpSession session = request.getSession();
            SessionUserInfo userInfo = (SessionUserInfo) session.getAttribute("sessionUserInfo");
            
            // 查询是否有此文件
            int widgetId = (int) map.get("widgetId");
            WidgetInfo widget = new WidgetInfo();
            widget.setId(widgetId);
            widget.setUploadUserid(userInfo.getUserId());
            WidgetInfo widgetResult = widgetInfoService.getWidgetInfo(widget);
            if (widgetResult != null && widgetResult.getStoragePath() != null)
            {
              //共享文件,得到文件扩展表，修改共享数据
                WidgetExtendInfo widgetExtendInfo = new WidgetExtendInfo();
                widgetExtendInfo.setWidgetId(widgetId);
                widgetExtendInfo = widgetInfoService.getWidgetExtendInfo(widgetExtendInfo);
                if(widgetExtendInfo.getId() != null){
                    widgetExtendInfo.setIsShare("0");//取消共享为0
                    widgetExtendInfo.setShareRange("");
//                    widgetExtendInfo.setShareTime(new Date());
                    //修改扩展信息为共享
                    int result = widgetInfoService.updateWidgetExtendInfo(widgetExtendInfo);
                    if (result > 0)
                    {
                        super.saveLog("取消共享文件","取消共享文件：" + widgetResult.getWidgetName(), userInfo);
                        return ResponseVoUtil.success(result);
                    }else{
                        return ResponseVoUtil.fail("取消共享文件失败", null);
                    }
                }else{
                    return ResponseVoUtil.fail("无此权限取消共享文件", null);
                }
                
            }else{
                return ResponseVoUtil.fail("无此权限取消共享文件", null);
            }
        }
        catch (Exception e)
        {
            logger.error("系统异常{}", e);
        }
        return ResponseVoUtil.fail("取消共享文件失败", null);
    }
    
    /*****
     * 功能：下载文件
     * 创建人：donghongguang
     * 创建时间：2017年3月23日 下午5:11:20
     * 
     * @param
     * @return
     * @version 1.0.0
     */
    @Override
    public ResponseVo downloadWidgetInfo(Map map, SessionUserInfo sessionUserInfo)
    {
        try
        {
            /**
             * 根据id查找文件，
             * 如果是本人文件可以直接下载，
             * 如果不是本人文件，对比共享范围，如果范围全公开，则都可以下载，否则看是否同一个组织---
             */
            // 查询是否有此文件，并找到此文件下载地址。
            int widgetId = (int) map.get("widgetId");
            WidgetInfo widget = new WidgetInfo();
            widget.setId(widgetId);
            String from = (String) map.get("from");
            String appId = "";
            
            WidgetInfo widgetResult = widgetInfoService.getWidgetInfo(widget);
            if (widgetResult != null && widgetResult.getStoragePath() != null)
            {
                boolean flag = false;
                if("interface".equals(from)){
//                  widget.setUploadUserid(sessionUserInfo.getUserId());
                  appId = (String) map.get("appId");
                  flag = true;
                }else{
                  //首先判断是不是本人下载，本人可以直接下载，否则判断是否共享
                    if(widgetResult.getUploadUserid().equals(sessionUserInfo.getUserId())){
                        flag = true;
                    }else{
                        //查找扩展表
                        WidgetExtendInfo extendInfo = new WidgetExtendInfo();
                        extendInfo.setWidgetId(widgetId);
                        extendInfo = widgetInfoService.getWidgetExtendInfo(extendInfo);
                        if(extendInfo.getIsShare().equals("1")){
                            flag = true;
                        }
                    }
                }
                
                if(flag){
                 // 更新数据库 下载次数加1
                    WidgetExtendInfo widgetExtendInfo = new WidgetExtendInfo();
                    widgetExtendInfo.setWidgetId(widgetId);
                    widgetInfoService.updatetWidgetDownloadTimes(widgetExtendInfo);
                    // 找到路径下载文件为二进制流
                    FastDfsUtil fastUtil = new FastDfsUtil();
                    String stroagePath = widgetResult.getStoragePath();
                    byte[] b = fastUtil.downloadFile(stroagePath);
                    Map<String, Object> mapResult = new HashMap<String, Object>();
                    // Base64 base = new Base64();
                    // String bb = base.encodeAsString(b);
                    mapResult.put("fileByte", b);
                    mapResult.put("widgetName", widgetResult.getWidgetName());
                    mapResult.put("widgetExtension", widgetResult.getWidgetExtension());
                    super.saveLog("下载文件", appId + "下载文件：" + widgetResult.getWidgetName(), sessionUserInfo);
                    return ResponseVoUtil.success(mapResult); 
                }else{
                    return ResponseVoUtil.fail("该文件已取消共享，您无权下载！", null);
                }
                
            }
            else
            {
                return ResponseVoUtil.fail("该文件已被删除，您无法下载！", null);
            }
        }
        catch (Exception e)
        {
            logger.error("系统异常{}", e);
            return ResponseVoUtil.fail("系统异常", null);
        }
        
    }
    
    /*****
     * 功能：上传文件
     * 创建人：donghongguang
     * 创建时间：2017年3月29日 下午2:11:09
     * 
     * @param
     * @return
     * @version 1.0.0
     */
    @Override
    public ResponseVo uploadWidgetInfo(Map map, SessionUserInfo sessionUserInfo)
    {
        /**
         * 从session中获取user信息，
         * 根据filename和version判断是否存在同名文件？
         * 验证权限，验证空间大小是否能够存储 --
         * 如果没有空间，则自动为其附加默认空间大小（目前100M，后期配置） --
         * api的时候目录问题。单独创建一个根目录
         * 上传文件，得到storagePath
         * 保存到文件信息表中
         */
        try
        {
            // 获取用户ID，即为上传人Id
            // int userId = (int) map.get("userId");
            // String userName = (String) map.get("userName");
            int userId = sessionUserInfo.getUserId();
            String userName = sessionUserInfo.getPoliceName();
            String orgName = sessionUserInfo.getOrgName();
            
            FastDfsUtil fastUtil = new FastDfsUtil();
            byte[] b = (byte[]) map.get("fileByte");
            // byte[] b = (byte[]) map.get("fileByte").toString().getBytes();
            // byte[] b = getBASE64Decode(map.get("fileByte").toString());
            String fileName = (String) map.get("fileName");
            String appId = (String) map.get("appId");
            
            WidgetInfo widgetInfo = new WidgetInfo();
            widgetInfo.setWidgetVersion((String) map.get("version"));// 版本号 怎么来？
            widgetInfo.setWidgetName(fileName);
            // 上传人信息
            widgetInfo.setUploadUserid(userId);
//            widgetInfo.setUploadUsername(userName);
            widgetInfo.setUploadUsername(orgName); //修改为上传者为企业名称
            // 查找是否有同名同版本文件
            WidgetInfo widgetResult = widgetInfoService.getWidgetInfo(widgetInfo);
            if (widgetResult != null && widgetResult.getId() != null)
            {
                return ResponseVoUtil.fail("您的上传目录中存在同名同版本文件", null);
            }
            int directoryId = Integer.parseInt((String) map.get("directoryId"));
            // 如果是外部接口传来的参数则，放在固定目录“外部系统”中
            if (directoryId < 0)
            {
                // 根据人来查询有否此目录，如果没有则创建，有则重新赋值directoryId
                DirectoryWidget directoryWidget = new DirectoryWidget();
                directoryWidget.setUserId(userId);
                directoryWidget.setDirectorName("外部系统");
                List<DirectoryWidget> directoryList = directoryWidgetService.getDirectoryInfoByUser(directoryWidget);
                if (directoryList != null && directoryList.size() > 0)
                {
                    directoryId = directoryList.get(0).getId();
                }
                else
                {
                    directoryWidget.setUserName(userName);
                    directoryId = directoryWidgetService.addDirectoryWidget(directoryWidget);
                }
            }
            widgetInfo.setDirectoryId(directoryId);
            //判断空间是否足够
            SpaceManage spaceManage = new SpaceManage();
            spaceManage.setUserType("0");
            spaceManage.setUserId(userId);
            spaceManage = spaceManageService.getSpaceMange(spaceManage);
            if(!(spaceManage != null && spaceManage.getId() != null)){
                //默认分配100M
                spaceManage = new SpaceManage();
                spaceManage.setUserType("0");
                spaceManage.setUserId(userId);
                Long spaceSize = FormatUtils.kb2bytes(5L, "GB");
                spaceManage.setSpaceTotal(spaceSize);
                spaceManage.setSpaceRest(spaceSize);
                spaceManage.setSpaceUse(0L);
                spaceManageService.addSpaceMange(spaceManage);
                
            }
            if(spaceManage.getSpaceTotal()>FormatUtils.add(spaceManage.getSpaceUse(), new Long((int)map.get("widgetSize")))){
             // 上传到fastDFS并获得存储地址
                String filePath = fastUtil.uploadFile(b, fileName);
                logger.error("文件上传的路径：" + filePath);
                
                widgetInfo.setRemarks((String) map.get("remarks"));
                widgetInfo.setStoragePath(filePath);
                
                widgetInfo.setWidgetExtension((String) map.get("widgetExtension"));
                widgetInfo.setWidgetSize((int) map.get("widgetSize"));
                
                widgetInfo.setWidgetNameShow((String) map.get("widgetNameShow"));
                int widgetTypeId = Integer.parseInt((String) map.get("widgetTypeId"));
                widgetInfo.setWidgetTypeId(widgetTypeId);
                int appType = Integer.parseInt((String) map.get("appType"));
                widgetInfo.setAppType(appType);
                // 当前时间为上传时间
                widgetInfo.setUploadTime(DateUtil.getSqlDate());
                
                // 记录消息
                int result = widgetInfoService.addWidgetInfo(widgetInfo);
                if (result > 0)
                {
                    super.saveLog("上传文件", "上传文件：" + fileName, sessionUserInfo);
                    return ResponseVoUtil.success(result);
                }
            }else{
                return ResponseVoUtil.fail("空间不足,请您申请空间", null);
            }
            
            
            
        }
        catch (Exception e)
        {
            logger.error("系统异常{}", e);
            return ResponseVoUtil.fail("上传失败", null);
        }
        return ResponseVoUtil.fail("上传失败", null);
    }
    
    /*****
     * 功能：更新文件
     * 创建人：donghongguang
     * 创建时间：2017年3月29日 下午2:11:09
     * 
     * @param
     * @return
     * @version 1.0.0
     */
    public ResponseVo updateWidgetInfo(Map map, SessionUserInfo sessionUserInfo)
    {
        /**
         * 从session中获取user信息，
         * 根据widgetId 查找文件
         * 验证权限，验证空间大小是否能够存储 --
         * 上传文件，得到storagePath
         * 更新到文件信息表中
         * 删除storage的信息
         */
        try
        {
            // 获取用户ID，即为上传人Id
            int userId = sessionUserInfo.getUserId();
            String userName = sessionUserInfo.getPoliceName();
            
            FastDfsUtil fastUtil = new FastDfsUtil();
            byte[] b = (byte[]) map.get("fileByte");
            String fileName = (String) map.get("fileName");
            String appId = (String) map.get("appId");
            
            // 根据widgetId 查找文件
            int widgetId = Integer.parseInt((String) map.get("widgetId"));
            WidgetInfo widgetInfo = new WidgetInfo();
            widgetInfo.setUploadUserid(userId);
            widgetInfo.setId(widgetId);
            widgetInfo = widgetInfoService.getWidgetInfo(widgetInfo);
            if (null != widgetInfo && widgetInfo.getStoragePath() != null)
            {
                // 获得文件原上传路径，待删除
                String oldFilePath = widgetInfo.getStoragePath();
                // 上传到fastDFS并获得存储地址
                String filePath = fastUtil.uploadFile(b, fileName);
                logger.error("文件上传的路径：" + filePath);
                // 保存信息
                widgetInfo.setStoragePath(filePath);
                widgetInfo.setWidgetVersion((String) map.get("version"));
                widgetInfo.setWidgetSize((int) map.get("widgetSize"));
                widgetInfo.setWidgetExtension((String) map.get("widgetExtension"));
                widgetInfo.setRemarks((String) map.get("remarks"));
                widgetInfo.setWidgetName(fileName);
                widgetInfo.setWidgetNameShow((String) map.get("widgetNameShow"));
                // 当前时间为上传时间
                widgetInfo.setUpdateTime(DateUtil.getSqlDate());
                widgetInfo.setUpdateUserid(userId);
                widgetInfo.setUpdateUsername(userName);
                // 更新上传信息
                int result = widgetInfoService.updateWidgetInfo(widgetInfo);
                
                if (result > 0)
                {
                    // 删除storage上的文件信息
                    fastUtil.deleteFile(oldFilePath);
                    super.saveLog("更新文件", "更新文件：" + fileName, sessionUserInfo);
                    return ResponseVoUtil.success(widgetInfo.getId());
                }
                
            }
            else
            {
                return ResponseVoUtil.fail("升级失败！无此权限升级文件！", null);
            }
            
        }
        catch (Exception e)
        {
            logger.error("系统异常{}", e);
            return ResponseVoUtil.fail("更新失败", null);
        }
        return null;
    }
    
    /*****
     * 功能：更新文件基本信息
     * 创建人：donghongguang
     * 创建时间：2017年3月29日 下午2:11:09
     * 
     * @param
     * @return
     * @version 1.0.0
     */
    public ResponseVo updateWidgetInfoBase(Map map, SessionUserInfo sessionUserInfo)
    {
        /**
         * 从session中获取user信息，
         * 根据widgetId 查找文件
         * 更新到文件信息表中
         */
        try
        {
            // 获取用户ID，即为上传人Id
            int userId = sessionUserInfo.getUserId();
            String userName = sessionUserInfo.getPoliceName();
            
            // 根据widgetId 查找文件
            int widgetId = (int) map.get("widgetId");
            //int widgetTypeId = (int) map.get("widgetTypeId");
            WidgetInfo widgetInfo = new WidgetInfo();
            widgetInfo.setUploadUserid(userId);
            widgetInfo.setId(widgetId);
            widgetInfo = widgetInfoService.getWidgetInfo(widgetInfo);
            if (null != widgetInfo && widgetInfo.getStoragePath() != null)
            {
                // 更新文件
                widgetInfo.setWidgetNameShow((String) map.get("widgetNameShow"));
                widgetInfo.setUpdateUserid(userId);
                widgetInfo.setUpdateUsername(userName);
                widgetInfo.setRemarks((String) map.get("remarks"));;
                WidgetExtendInfo widgetExtendInfo = new WidgetExtendInfo();
                
                widgetExtendInfo.setDirectoryId((int)map.get("directoryId"));
                widgetExtendInfo.setWidgetId(widgetId);
               // widgetExtendInfo.setWidgetTypeId(widgetTypeId);
                
                
                // 更新信息
                int result = widgetInfoService.updateWidgetInfoBase(widgetInfo, widgetExtendInfo);
                
                if (result > 0)
                {
                    super.saveLog("更新文件", "更新文件：" + widgetInfo.getWidgetName(), sessionUserInfo);
                    return ResponseVoUtil.success(widgetInfo.getId());
                }
                
            }
            else
            {
                return ResponseVoUtil.fail("升级失败！无此权限升级文件！", null);
            }
            
        }
        catch (Exception e)
        {
            logger.error("系统异常{}", e);
            return ResponseVoUtil.fail("更新失败", null);
        }
        return null;
    }
    
    private static void byte2File(byte[] buf, String filePath, String fileName)
    {
        File dir = new File(filePath);
        if (!dir.exists())
        {
            dir.mkdirs();
        }
        File file = new File(filePath + File.separator + fileName);
        try (FileOutputStream fos = new FileOutputStream(file);)
        {
            fos.write(buf);
        }
        catch (Exception e)
        {
            
        }
    }

    /*****
     * 功能：删除文件
     * 创建人：donghongguang
     * 创建时间：2017年4月5日 上午10:00:33
     * @param 
     * @return 
     * @version 1.0.0
     */
    @Override
    public int deleteWidget(String widgetPath, String widgetId)
    {
        /**
         * 1.验证删除的文件存在，切属于本人操作
         * 2.更新数据库标志is_delete
         */
        HttpSession session = request.getSession();
        SessionUserInfo userInfo = (SessionUserInfo) session.getAttribute("sessionUserInfo");
        // 获取用户ID
        int userId = userInfo.getUserId();
//        String userName = userInfo.getPoliceName();
        
        // 根据widgetId 查找文件
        int id = Integer.parseInt(widgetId);
        WidgetInfo widgetInfo = new WidgetInfo();
        widgetInfo.setUploadUserid(userId);
        widgetInfo.setId(id);
        widgetInfo = widgetInfoService.getWidgetInfo(widgetInfo);
        if (null != widgetInfo && widgetInfo.getStoragePath() != null)
        {
            //更新删除标志
            widgetInfo.setIsDelete(1);//1表示删除
            int i = widgetInfoService.updateWidgetInfo(widgetInfo);
            super.saveLog("删除文件","删除文件：" + widgetInfo.getWidgetName(), userInfo);
            return i;
        }
        return 0;
    }
    /*****
     * 功能：发布调用 增加发布次数
     * 创建人：donghongguang
     * 创建时间：2017年5月16日 下午3:53:12
     * @param 
     * @return 
     * @version 1.0.0
     */
    @Override
    public ResponseVo addRate(int widgetId){
     // 更新数据库 下载次数加1
        WidgetExtendInfo widgetExtendInfo = new WidgetExtendInfo();
        widgetExtendInfo.setWidgetId(widgetId);
        int result = widgetInfoService.updatetWidgetReleaseTimes(widgetExtendInfo);
        if(result > 0){
            return ResponseVoUtil.success("");
        }
        return ResponseVoUtil.fail("发布次数失败", null);
    }
    
    /*****
     * 功能：企业上传文件统计排名
     * 创建人：donghongguang
     * 创建时间：2017年5月18日 下午1:25:40
     * @param 
     * @return 
     * @version 1.0.0
     */
    @Override
    public List<Map> getWidgetNum(Map map){
        SessionUserInfo session = (SessionUserInfo) request.getSession().getAttribute("sessionUserInfo");
        if (session != null )
        {
//                map.put("orgCode", session.getOrgCode());
//            map.put("uploadUserid", session.getUserId());
            List<Map> list = widgetInfoService.queryWidgetNum(map);
            return list;
        }
        return null;
    }
    
    /*****
     * 功能：企业文件总发布次数统计排名 
     * 创建人：donghongguang
     * 创建时间：2017年5月18日 下午1:25:40
     * @param 
     * @return 
     * @version 1.0.0
     */
    @Override
    public List<Map> getWidgetAllReleaseNum(Map map){
        SessionUserInfo session = (SessionUserInfo) request.getSession().getAttribute("sessionUserInfo");
        if (session != null )
        {
            List<Map> list = widgetInfoService.queryWidgetAllReleaseNum(map);
            return list;
        }
        return null;
        
    }
    
    
    /*****
     * 功能：企业文件发布下载次数统计排名
     * 创建人：donghongguang
     * 创建时间：2017年5月18日 下午1:25:40
     * @param 
     * @return 
     * @version 1.0.0
     */
    @Override
    public List<Map> getWidgetReleaseNum(Map map){
        SessionUserInfo session = (SessionUserInfo) request.getSession().getAttribute("sessionUserInfo");
        if (session != null )
        {
            List<Map> list = widgetInfoService.queryWidgetReleaseNum(map);
            return list;
        }
        return null;
    }
    @Override
    public int getWidgetReleaseNumCount(Map map){
        SessionUserInfo session = (SessionUserInfo) request.getSession().getAttribute("sessionUserInfo");
        if(session != null){
            int count = widgetInfoService.getWidgetInfoListCount(map);
            return count;
        } 
        return 0;
        
    }
    
    /*****
     * 功能：按类型统计 文件量 
     * 创建人：donghongguang
     * 创建时间：2017年5月18日 下午1:25:40
     * @param 
     * @return 
     * @version 1.0.0
     */
    @Override
    public List<Map> getWidgetNumByType(Map map){
        SessionUserInfo session = (SessionUserInfo) request.getSession().getAttribute("sessionUserInfo");
        if (session != null )
        {
            List<Map> list = widgetInfoService.queryWidgetNumByType(map);
            return list;
        }
        return null;
        
    }

}
