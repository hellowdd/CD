package com.bocom.web.controller.widget;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bocom.business.DirectoryWidgetBusiness;
import com.bocom.domain.DirectoryWidget;
import com.bocom.util.ResponseVo;
import com.bocom.util.ResponseVoUtil;

/*****
 * 类名称：DirectoryWidgetController
 * 类描述：控件目录业务控制类
 * 创建人：donghongguang
 * 创建时间：2017年4月12日 下午5:31:15
 * 修改人：
 * 修改时间：
 * @version 1.0.0
 */
@Controller
@RequestMapping("/manager/widgetDirectory")
public class DirectoryWidgetController
{
    @Autowired
    private DirectoryWidgetBusiness directoryWidgetBusiness;
    
    /*****
     * 功能：获取目录信息
     * 创建人：donghongguang
     * 创建时间：2017年3月22日 下午3:10:24
     * @param
     * @return
     * @version 1.0.0
     */
    @RequestMapping("/getDirectoryInfo")
    @ResponseBody
    public ResponseVo getDirectoryInfo(@RequestParam(value="directoryId",required = false) Integer directoryId,@RequestParam(value="directoryName",required = false) String directoryName)
    {
        DirectoryWidget directoryWidget = new DirectoryWidget();
        directoryWidget.setDirectorName(directoryName); 
        directoryWidget.setId(directoryId);
        return ResponseVoUtil.success(directoryWidgetBusiness.getSubDirectory(directoryWidget));
        
    }
    
    /*****
     * 功能：修改目录
     * 创建人：donghongguang
     * 创建时间：2017年3月22日 下午3:10:24
     * @param
     * @return
     * @version 1.0.0
     */
    @RequestMapping("/updateDirectoryInfo")
    @ResponseBody
    public ResponseVo updateDirectoryInfo(@RequestParam(value="directoryId",required = true) Integer directoryId,@RequestParam(value="parentDirectoryId",required = true) Integer parentDirectoryId,@RequestParam(value="directoryName",required = true) String directoryName)
    {
        DirectoryWidget directoryWidget = new DirectoryWidget();
        directoryWidget.setParentDirectoryId(parentDirectoryId);
        directoryWidget.setDirectorName(directoryName); 
        directoryWidget.setId(directoryId);
        return directoryWidgetBusiness.updateDirectory(directoryWidget);
    }
    
    /*****
     * 功能：创建目录
     * 创建人：donghongguang
     * 创建时间：2017年3月22日 下午3:10:24
     * @param
     * @return
     * @version 1.0.0
     */
    @RequestMapping("/creatDirectoryInfo")
    @ResponseBody
    public ResponseVo creatDirectoryInfo(@RequestParam(value="parentDirectoryId",required = true) Integer parentDirectoryId,@RequestParam(value="directoryName",required = true) String directoryName)
    {
        try
        {
            DirectoryWidget directoryWidget = new DirectoryWidget();
            directoryWidget.setParentDirectoryId(parentDirectoryId);
            directoryWidget.setDirectorName(URLDecoder.decode(directoryName,"utf-8"));
            return directoryWidgetBusiness.creatDirectoryInfo(directoryWidget);
        }
        catch (UnsupportedEncodingException e)
        {
            return ResponseVoUtil.fail(e.getMessage(),e.getMessage());
        }
    }
    
    /*****
     * 功能：删除目录
     * 创建人：donghongguang
     * 创建时间：2017年3月22日 下午3:10:24
     * @param
     * @return
     * @version 1.0.0
     */
    @RequestMapping("/removeDirectoryInfo")
    @ResponseBody
    public Map removeDirectoryInfo(@RequestParam(value="directoryId",required = true) Integer directoryId)
    {
        DirectoryWidget directoryWidget = new DirectoryWidget();
        directoryWidget.setId(directoryId);
        return directoryWidgetBusiness.removeDirecotry(directoryWidget);
    }
}
