package com.bocom.web.controller.widget;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bocom.business.WidgetTypeBusiness;
import com.bocom.domain.ApplyShare;
import com.bocom.domain.WidgetType;
import com.bocom.util.ResponseVo;
import com.bocom.util.ResponseVoUtil;

/*****
 * 类名称：WidgetTypeController
 * 类描述：空间类型控制类
 * 创建人：donghongguang
 * 创建时间：2017年5月15日 上午10:30:55
 * 修改人：
 * 修改时间：
 * 
 * @version 1.0.0
 */
@Controller
@RequestMapping("/manager/widgetType")
public class WidgetTypeController
{
    
    @Autowired
    private HttpServletRequest request;           // 这里可以获取到request
    @Autowired
    private WidgetTypeBusiness widgetTypeBusiness;
    
    /*****
     * 功能：获取类型信息
     * 创建人：donghongguang
     * 创建时间：2017年5月15日 下午3:10:24
     * 
     * @param
     * @return
     * @version 1.0.0
     */
    @RequestMapping("/getWidgetType")
    @ResponseBody
    public ResponseVo getDirectoryInfo()
    {
        WidgetType widgetType = new WidgetType();
        return ResponseVoUtil.success(widgetTypeBusiness.getWidgetTypeInfo(widgetType));
        
    }
    
    /*****
     * 功能：修改类型
     * 创建人：donghongguang
     * 创建时间：2017年5月15日 下午3:10:24
     * 
     * @param
     * @return
     * @version 1.0.0
     */
    @RequestMapping("/updateWidgetTypeInfo")
    @ResponseBody
    public ResponseVo updateWidgetTypeInfo(@RequestParam(value = "id", required = true) Integer id,
            @RequestParam(value = "widgetTypeName", required = true) String widgetTypeName,
            @RequestParam(value = "widgetTypeContent", required = false) String widgetTypeContent)
    {
        try
        {
            WidgetType widgetType = new WidgetType();
            widgetType.setId(id);
            widgetTypeContent = widgetTypeContent == null? "":URLDecoder.decode(widgetTypeContent, "utf-8");
            widgetType.setWidgetTypeContent(widgetTypeContent);
            widgetType.setWidgetTypeName(URLDecoder.decode(widgetTypeName, "utf-8"));
            return widgetTypeBusiness.updateWidgetType(widgetType);
        }
        catch (UnsupportedEncodingException e)
        {
            return ResponseVoUtil.fail(e.getMessage(), e.getMessage());
        }
    }
    
    /*****
     * 功能：创建类型
     * 创建人：donghongguang
     * 创建时间：2017年5月15日 下午3:10:24
     * 
     * @param
     * @return
     * @version 1.0.0
     */
    @RequestMapping("/creatWidgetTypeInfo")
    @ResponseBody
    public ResponseVo creatWidgetTypeInfo(@RequestParam(value = "widgetTypeName", required = true) String widgetTypeName,
            @RequestParam(value = "widgetTypeContent", required = false) String widgetTypeContent)
    {
        try
        {
            WidgetType widgetType = new WidgetType();
            widgetType.setWidgetTypeName(URLDecoder.decode(widgetTypeName, "utf-8"));
            widgetTypeContent = widgetTypeContent == null? null:URLDecoder.decode(widgetTypeContent, "utf-8");
            widgetType.setWidgetTypeContent(widgetTypeContent);
            return widgetTypeBusiness.createWidgetType(widgetType);
        }
        catch (UnsupportedEncodingException e)
        {
            return ResponseVoUtil.fail(e.getMessage(), e.getMessage());
        }
    }
    
    /*****
     * 功能：删除目录
     * 创建人：donghongguang
     * 创建时间：2017年5月15日 下午3:10:24
     * 
     * @param
     * @return
     * @version 1.0.0
     */
    @RequestMapping("/removeWidgetTypeInfo")
    @ResponseBody
    public Map removeWidgetTypeInfo(@RequestParam(value = "widgetTypeId", required = true) String widgetTypeId)
    {
        WidgetType widgetType = new WidgetType();
        widgetType.setId(Integer.valueOf(widgetTypeId));
        return widgetTypeBusiness.removeWidgetType(widgetType);
    }
    
    /*****
     * 功能：赋予权限
     * 创建人：donghongguang
     * 创建时间：2017年6月29日 下午3:10:24
     * 
     * @param
     * @return
     * @version 1.0.0
     */
    @RequestMapping("/shareWidgetToOffice")
    @ResponseBody
    public ResponseVo shareWidgetToOffice(@RequestParam(value = "widgetId", required = true) Integer widgetId,
            @RequestParam(value = "officeId", required = true) String officeId)
    {
        try
        {
            ApplyShare applyShare = new ApplyShare();
            applyShare.setWidgetId(widgetId);
            applyShare.setShareRange(officeId);
            return widgetTypeBusiness.shareWidgetToOffice(applyShare);
        }
        catch (Exception e)
        {
            return ResponseVoUtil.fail(e.getMessage(), e.getMessage());
        }
    }
    /*****
     * 功能：取消赋予权限
     * 创建人：donghongguang
     * 创建时间：2017年6月29日 下午3:10:24
     * 
     * @param
     * @return
     * @version 1.0.0
     */
    @RequestMapping("/unshareWidgetToOffice")
    @ResponseBody
    public Map unshareWidgetToOffice(@RequestParam(value = "widgetId", required = true) Integer widgetId,
            @RequestParam(value = "officeId", required = true) String officeId)
    {
        Map removeMap = new HashMap();
        try
        {
            ApplyShare applyShare = new ApplyShare();
            applyShare.setWidgetId(widgetId);
            applyShare.setShareRange(officeId);
            return widgetTypeBusiness.unshareWidgetToOffice(applyShare);
        }
        catch (Exception e)
        {
            removeMap.put("success", false);
            removeMap.put("message", "操作失败");
        }
        return removeMap;
    }
    /*****
     * 功能：获取拥有的权限
     * 创建人：donghongguang
     * 创建时间：2017年6月29日 下午3:10:24
     * 
     * @param
     * @return
     * @version 1.0.0
     */
    @RequestMapping("/getWidgetOffice")
    @ResponseBody
    public ResponseVo getWidgetOffice(@RequestParam(value = "widgetId", required = true) Integer widgetId)
    {
        ApplyShare applyShare = new ApplyShare();
        applyShare.setWidgetId(widgetId);
        return ResponseVoUtil.success(widgetTypeBusiness.getWidgetOffice(applyShare));
    }
    /*****
     * 功能：获取所有部门
     * 创建人：donghongguang
     * 创建时间：2017年6月29日 下午3:10:24
     * 
     * @param
     * @return
     * @version 1.0.0
     */
    @RequestMapping(value="/getOffice",produces="text/html;charset=UTF-8")
    @ResponseBody
    public String getOffice()
    {
        return widgetTypeBusiness.getOffice();
    }
}
