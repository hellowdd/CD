package com.bocom.business.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bocom.business.WidgetTypeBusiness;
import com.bocom.domain.ApplyShare;
import com.bocom.domain.WidgetType;
import com.bocom.dto.session.SessionUserInfo;
import com.bocom.service.WidgetTypeService;
import com.bocom.service.user.UserRestService;
import com.bocom.util.ResponseVo;
import com.bocom.util.ResponseVoUtil;

/*****
 * 类名称：WidgetTypeBusinessImpl
 * 类描述：空间分类业务处理类
 * 创建人：donghongguang
 * 创建时间：2017年5月16日 上午10:05:14
 * 修改人：
 * 修改时间：
 * @version 1.0.0
 */
@Component
public class WidgetTypeBusinessImpl extends BaseSerivceImpl implements WidgetTypeBusiness
{
    private Logger                 logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private HttpServletRequest     request;
    @Autowired
    private WidgetTypeService widgetTypeService;
    @Autowired
    private UserRestService userRestService;
    
    /*****
     * 功能：获取控件类型信息
     * 创建人：donghongguang
     * 创建时间：2017年5月15日 下午4:06:08
     * @param 
     * @return 
     * @version 1.0.0
     */
    @Override
    public List<WidgetType> getWidgetTypeInfo(WidgetType widgetType){
        logger.info("WidgetTypeBusinessImpl getWidgetTypeInfo begin ...");
        List<WidgetType> widgetTypeList = null;
        /**
         * 获取当前登录账户，
         * 获取共有类型
         * 获取个人类型
         */
        try
        {
            widgetType.setIsAll("1");//先查找共有类型
            widgetTypeList = widgetTypeService.getWidgetTypeInfoByUser(widgetType);
         // 获取当前用户session
            SessionUserInfo session = (SessionUserInfo) request.getSession().getAttribute("sessionUserInfo");
            widgetType.setCreateUserid(session.getUserId());
            
            //获取个人类型
            widgetType.setIsAll("0");//个人类型
            
            List<WidgetType> widgetTypeMyList = widgetTypeService.getWidgetTypeInfoByUser(widgetType);
            widgetTypeList.addAll(widgetTypeMyList);
            
        }
        catch (Exception e)
        {
            logger.error("WidgetTypeBusinessImpl getWidgetTypeInfo error ..." + e);
        }
        return widgetTypeList;
    }
    
    /*****
     * 功能：创建控件类型信息
     * 创建人：donghongguang
     * 创建时间：2017年5月15日 下午4:07:39
     * @param 
     * @return 
     * @version 1.0.0
     */
    @Override
    public ResponseVo createWidgetType(WidgetType widgetType){
        logger.info("WidgetTypeBusinessImpl createWidgetType begin ...");
        try
        {
            // 获取当前用户session
            SessionUserInfo session = (SessionUserInfo) request.getSession().getAttribute("sessionUserInfo");
            
            widgetType.setCreateUserid(session.getUserId());
            widgetType.setCreateUsername(session.getUserName());
            widgetType.setCreateTime(new Date());
            widgetType.setIsAll("0");//个人类型
            //查询是否有同名类型，有则不创建，没有则新建
            WidgetType typeResult = widgetTypeService.getWidgetTypeInfoById(widgetType);
            if(!(null != typeResult && typeResult.getId() != null)){
              //创建一条控件类型
                int result = widgetTypeService.addWidgetType(widgetType);
                if(result>0){
                   super.saveLog("创建控件类型", "创建控件类型"+widgetType.getWidgetTypeName());
                   return ResponseVoUtil.success(null);
                }
            }
            else{
                return ResponseVoUtil.fail("您存在同名类型", null);
            }
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.error("WidgetTypeBusinessImpl createWidgetType error ..." + e);
        }
        logger.info("WidgetTypeBusinessImpl createWidgetType end ...");
        return ResponseVoUtil.fail("创建失败", null);
    }
    
    /*****
     * 功能：删除控件类型信息
     * 创建人：donghongguang
     * 创建时间：2017年5月15日 下午4:08:12
     * @param 
     * @return 
     * @version 1.0.0
     */
    @Override
    public Map removeWidgetType(WidgetType widgetType){
        logger.info("WidgetTypeBusinessImpl removeWidgetType begin ...");
        Map<String, Object> removeMap = new HashMap<>();
        /**
         * 1.查询是否有此数据
         * 2.个人数据允许删除
         */
        try
        {
            // 获取当前用户session
            SessionUserInfo session = (SessionUserInfo) request.getSession().getAttribute("sessionUserInfo");
            // 1.查询是否有此数据
            widgetType.setCreateUserid(session.getUserId());
            widgetType.setIsAll("0");
            WidgetType widgetTypeInfo = widgetTypeService.getWidgetTypeInfoById(widgetType);
            if(widgetTypeInfo != null){
                //删除
                if(widgetTypeService.removeWidgetType(widgetTypeInfo) > 0){
                    removeMap.put("success", true);
                    super.saveLog("删除控件类型", "删除控件类型" + widgetTypeInfo.getWidgetTypeName());
                }else                {
                    // 未成功
                    removeMap.put("success", false);
                    removeMap.put("message", "删除未成功");
                }
            }else{
             // 无此数据
                removeMap.put("success", false);
                removeMap.put("message", "无此类型或者您无权删除");
            }
            
        }
        catch (Exception e)
        {
            removeMap.put("success", false);
            removeMap.put("message", "操作失败");
        }
        logger.info("WidgetTypeBusinessImpl removeWidgetType end ...");
        return removeMap;
    }
    
    /*****
     * 功能：修改控件类型信息
     * 创建人：donghongguang
     * 创建时间：2017年5月15日 下午4:08:47
     * @param 
     * @return 
     * @version 1.0.0
     */
    @Override
    public ResponseVo updateWidgetType(WidgetType widgetType){
        logger.info("WidgetTypeBusinessImpl updateWidgetType begin ...");
        try
        {
            WidgetType widgetTypeQuery = new WidgetType();
            // 获取当前用户session
            SessionUserInfo session = (SessionUserInfo) request.getSession().getAttribute("sessionUserInfo");
            // 1.查询是否有此数据
            widgetType.setCreateUserid(session.getUserId());
            widgetTypeQuery.setCreateUserid(session.getUserId());
            widgetTypeQuery.setId(widgetType.getId());
            widgetType.setIsAll("0");
            WidgetType widgetTypeInfo = widgetTypeService.getWidgetTypeInfoById(widgetTypeQuery);
            if(widgetTypeInfo != null && widgetTypeInfo.getId() != null){
                //查询新的名称是否具有同名类型
                widgetTypeQuery.setId(null);
                widgetTypeQuery.setWidgetTypeName(widgetType.getWidgetTypeName());
                WidgetType widgetTypeInfos = widgetTypeService.getWidgetTypeInfoById(widgetTypeQuery);
                if(widgetTypeInfos != null && widgetTypeInfos.getId() != null && !(widgetTypeInfos.getId().equals(widgetType.getId()))){
                    return ResponseVoUtil.fail("您存在同名类型", null);
                }
                widgetTypeInfo.setUpdateTime(new Date());
                widgetTypeInfo.setWidgetTypeName(widgetType.getWidgetTypeName());
                widgetTypeInfo.setWidgetTypeContent(widgetType.getWidgetTypeContent());
              //修改一条控件目录，名称相同由前台控制
                int result = widgetTypeService.updateWidgetType(widgetTypeInfo);
                if(result>0){
                   super.saveLog("修改控件类型", "修改控件类型"+widgetTypeInfo.getWidgetTypeName());
                   return ResponseVoUtil.success(null);
                }else{
                    return ResponseVoUtil.fail("修改失败", null);
                }
            }else{
             // 无此数据
                return ResponseVoUtil.fail("无此类型或者您无权修改", null);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.error("WidgetTypeBusinessImpl updateWidgetType error ..." + e);
        }
        logger.info("WidgetTypeBusinessImpl updateWidgetType end ...");
        return ResponseVoUtil.fail("修改失败", null);
    }
    
    /*****
     * 功能：创建赋予权限信息
     * 创建人：donghongguang
     * 创建时间：2017年5月15日 下午4:07:39
     * @param 
     * @return 
     * @version 1.0.0
     */
    public ResponseVo shareWidgetToOffice(ApplyShare applyShare){
        logger.info("WidgetTypeBusinessImpl shareWidgetToOffice begin ...");
        try
        {
            // 获取当前用户session
//            SessionUserInfo session = (SessionUserInfo) request.getSession().getAttribute("sessionUserInfo");
            
            //查询是否有同名类型，有则不创建，没有则新建
            List<ApplyShare> listResult = widgetTypeService.getAllByWidget(applyShare);
            if(null != listResult && listResult.size()>0){
                return ResponseVoUtil.fail("请您不要重复赋予权限", null);
            }
            else{
              //创建一条赋权
                int result = widgetTypeService.addWidgetShare(applyShare);
                if(result>0){
//                   super.saveLog("赋予权限", "赋予权限"+applyShare.getWidgetId());
                   return ResponseVoUtil.success(null);
                }
            }
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.error("WidgetTypeBusinessImpl shareWidgetToOffice error ..." + e);
        }
        logger.info("WidgetTypeBusinessImpl shareWidgetToOffice end ...");
        return ResponseVoUtil.fail("赋权失败", null);
    }
    
    /** 删除权限 */
    public Map unshareWidgetToOffice(ApplyShare applyShare){
        logger.info("WidgetTypeBusinessImpl unshareWidgetToOffice begin ...");
        Map<String, Object> removeMap = new HashMap<>();
        /**
         * 1.查询是否有此数据
         * 2.个人数据允许删除
         */
        try
        {
            List<ApplyShare> listResult = widgetTypeService.getAllByWidget(applyShare);
            if(null != listResult && listResult.size()>0){
                //可以删除
                  int result = widgetTypeService.removeWidgetShare(listResult.get(0));
                  if(result>0){
                     removeMap.put("success", true);
                  }else                {
                      // 未成功
                      removeMap.put("success", false);
                      removeMap.put("message", "删除未成功");
                  }
              }
              else{
               // 无此数据
                  removeMap.put("success", false);
                  removeMap.put("message", "无此权限可取消");
              }
        }
        catch (Exception e)
        {
            removeMap.put("success", false);
            removeMap.put("message", "操作失败");
        }
        logger.info("WidgetTypeBusinessImpl unshareWidgetToOffice end ...");
        return removeMap;
    }
    
    /** 查找某控件赋予权限的部门 */
    public List<ApplyShare> getWidgetOffice(ApplyShare applyShare){
        logger.info("WidgetTypeBusinessImpl getWidgetOffice begin ...");
        List<ApplyShare> applyShareList = null;
        /**
         * 获取当前登录账户，
         * 获取共有类型
         * 获取个人类型
         */
        try
        {
            applyShareList = widgetTypeService.getAllByWidget(applyShare);
            
        }
        catch (Exception e)
        {
            logger.error("WidgetTypeBusinessImpl getWidgetOffice error ..." + e);
        }
        return applyShareList;
    }
    
    /** 查找所有的部门 */
    public String getOffice(){
       return userRestService.getOfficeFromPAP();
    }
}
