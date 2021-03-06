package com.bocom.business.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bocom.business.DirectoryWidgetBusiness;
import com.bocom.domain.DirectoryWidget;
import com.bocom.domain.SpaceManage;
import com.bocom.dto.session.SessionUserInfo;
import com.bocom.service.DirectoryWidgetService;
import com.bocom.service.SpaceManageService;
import com.bocom.service.WidgetInfoService;
import com.bocom.util.FormatUtils;
import com.bocom.util.ResponseVo;
import com.bocom.util.ResponseVoUtil;

/*****
 * 类名称：DirectoryWidgetBusinessImpl
 * 类描述：文件目录业务处理类
 * 创建人：donghongguang
 * 创建时间：2017年3月24日 下午1:43:13
 * 修改人：
 * 修改时间：
 * 
 * @version 1.0.0
 */
@Component
public class DirectoryWidgetBusinessImpl extends BaseSerivceImpl implements DirectoryWidgetBusiness
{
    private Logger                 logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private HttpServletRequest     request;                                          // 这里可以获取到request
    @Autowired
    private DirectoryWidgetService directoryWidgetService;
    @Autowired
    private WidgetInfoService      widgetInfoService;
    @Autowired
    private SpaceManageService      spaceManageService;
    
    @Override
    public List<DirectoryWidget> getAll(DirectoryWidget directoryWidget)
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    /*****
     * 功能：创建目录
     * 创建人：donghongguang
     * 创建时间：2017年3月24日 下午1:50:43
     * 
     * @param
     * @return
     * @version 1.0.0
     */
    @Override
    public ResponseVo creatDirectoryInfo(DirectoryWidget directoryWidget)
    {
        logger.info("DirectoryWidgetBusinessImpl creatDirectoryInfo begin ...");
        try
        {
            // 获取当前用户session
            SessionUserInfo session = (SessionUserInfo) request.getSession().getAttribute("sessionUserInfo");
            
            //创建自己的空间信息
            SpaceManage spaceManage = new SpaceManage();
            spaceManage.setUserType("0");
            spaceManage.setUserId(session.getUserId());
            SpaceManage spaceManageResult = spaceManageService.getSpaceMange(spaceManage);
            if(!(spaceManageResult != null && spaceManageResult.getId() != null)){
                //默认分配100M
                spaceManage = new SpaceManage();
                spaceManage.setUserType("0");
                spaceManage.setUserId(session.getUserId());
                Long spaceSize = FormatUtils.kb2bytes(500L, "GB");
                spaceManage.setSpaceTotal(spaceSize);
                spaceManage.setSpaceRest(spaceSize);
                spaceManage.setSpaceUse(0L);
                int resultSpace = spaceManageService.addSpaceMange(spaceManage);
                if(! (resultSpace > 0)){
                    return ResponseVoUtil.fail("创建失败", null); 
                }
            }
            directoryWidget.setUserId(session.getUserId());
            directoryWidget.setUserName(session.getPoliceName());
            //创建一条文件目录，名称相同由前台控制
            int result = directoryWidgetService.addDirectoryWidget(directoryWidget);
            if(result>0){
               super.saveLog("创建目录", "创建目录"+directoryWidget.getDirectorName());
               return ResponseVoUtil.success(null);
            }
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.error("DirectoryWidgetBusinessImpl creatDirectoryInfo error ..." + e);
        }
        logger.info("DirectoryWidgetBusinessImpl creatDirectoryInfo end ...");
        return ResponseVoUtil.fail("创建失败", null);
    }
    
    /*****
     * 功能：修改目录 移动目录
     * 创建人：donghongguang
     * 创建时间：2017年3月24日 下午1:51:00
     * 
     * @param
     * @return
     * @version 1.0.0
     */
    @Override
    public ResponseVo updateDirectory(DirectoryWidget directoryWidget)
    {
        logger.info("DirectoryWidgetBusinessImpl updateDirectory begin ...");
        try
        {
            // 获取当前用户session
            SessionUserInfo session = (SessionUserInfo) request.getSession().getAttribute("sessionUserInfo");
            directoryWidget.setUserId(session.getUserId());
            directoryWidget.setUserName(session.getUserName());
            //修改一条文件目录，名称相同由前台控制
            int result = directoryWidgetService.updateDirectory(directoryWidget);
            if(result>0){
               super.saveLog("修改目录", "修改目录"+directoryWidget.getDirectorName());
               return ResponseVoUtil.success(null);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.error("DirectoryWidgetBusinessImpl updateDirectory error ..." + e);
        }
        logger.info("DirectoryWidgetBusinessImpl updateDirectory end ...");
        return ResponseVoUtil.fail("修改失败", null);
    }
    
    /*****
     * 功能：删除目录
     * 创建人：donghongguang
     * 创建时间：2017年3月24日 下午1:51:52
     * 
     * @param
     * @return
     * @version 1.0.0
     */
    @Override
    public Map removeDirecotry(DirectoryWidget directoryWidget)
    {
        logger.info("DirectoryWidgetBusinessImpl removeDirecotry begin ...");
        Map<String, Object> removeMap = new HashMap<>();
        /**
         * 1.查询是否有此数据
         * 2.查找该目录下是否有子目录
         * 3.查找该目录下是否有文件存在
         * 4.都没有则可以删除数据记录，其他情况则不允许删除
         */
        try
        {
            // 获取当前用户session
            SessionUserInfo session = (SessionUserInfo) request.getSession().getAttribute("sessionUserInfo");
            // 1.查询是否有此数据
            DirectoryWidget directoryWidgetInfo = directoryWidgetService.getDirectoryInfoById(directoryWidget);
            if (directoryWidgetInfo != null)
            {
                // 2.查找该目录下是否有子目录
                directoryWidget.setParentDirectoryId(directoryWidget.getId());
                List<DirectoryWidget> directSubList = directoryWidgetService.getSubDirectory(directoryWidget);
                if (!(directSubList != null && directSubList.size() > 0))
                {
                    // 3.查找该目录下是否有文件存在
                    Map<String, Object> request = new HashMap<String, Object>();
                    request.put("directoryId", directoryWidget.getId());
                    List<Map> widgetList = widgetInfoService.getWidgetInfoList(request);
                    if (!(widgetList != null && widgetList.size() > 0))
                    {
                        String directorName = directoryWidgetInfo.getDirectorName();
                        // 4.删除数据记录
                        if (directoryWidgetService.removeDirecotry(directoryWidget) > 0)
                        {
                            removeMap.put("success", true);
                            super.saveLog("删除目录", "删除目录" + directorName);
                            // 是否要删除 回收站的东西。
                        }
                        else
                        {
                            // 未成功
                            removeMap.put("success", false);
                            removeMap.put("message", "删除未成功");
                        }
                    }
                    else
                    {
                        // 有子目录，不可删除
                        removeMap.put("success", false);
                        removeMap.put("message", "目录有文件，不可删除");
                    }
                }
                else
                {
                    // 有子目录，不可删除
                    removeMap.put("success", false);
                    removeMap.put("message", "有子目录，不可删除");
                }
            }
            else
            {
                // 无此数据
                removeMap.put("success", false);
                removeMap.put("message", "无此数据");
            }
        }
        catch (Exception e)
        {
            removeMap.put("success", false);
            removeMap.put("message", "操作失败");
        }
        logger.info("DirectoryWidgetBusinessImpl removeDirecotry end ...");
        return removeMap;
    }
    
    /*****
     * 功能：查找目录下的子目录
     * 创建人：donghongguang
     * 创建时间：2017年3月24日 下午1:52:02
     * 
     * @param
     * @return
     * @version 1.0.0
     */
    @Override
    public List<DirectoryWidget> getSubDirectory(DirectoryWidget directoryWidget)
    {
        logger.info("DirectoryWidgetBusinessImpl getSubDirectory begin ...");
        List<DirectoryWidget> directSubList = null;
        /**
         * 是否查找根目录，
         * 不是根目录则查找子目录
         */
        // 查找该目录下是否有子目录
        try
        {
         // 获取当前用户session
            SessionUserInfo session = (SessionUserInfo) request.getSession().getAttribute("sessionUserInfo");
            directoryWidget.setUserId(session.getUserId());
            directoryWidget.setUserName(session.getPoliceName());
            
            if(directoryWidget.getId() != null && !"".equals(directoryWidget.getId())){
                directoryWidget.setParentDirectoryId(directoryWidget.getId());
                directSubList = directoryWidgetService.getSubDirectory(directoryWidget);
            }else{
                directSubList = directoryWidgetService.getDirectoryInfoByUser(directoryWidget);
            }
            
            
        }
        catch (Exception e)
        {
            logger.error("DirectoryWidgetBusinessImpl getSubDirectory error ..." + e);
        }
        return directSubList;
    }
    
}
