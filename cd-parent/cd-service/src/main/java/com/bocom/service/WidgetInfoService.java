package com.bocom.service;

import com.bocom.domain.WidgetExtendInfo;
import com.bocom.domain.WidgetInfo;
import com.bocom.util.ResponseVo;
import org.csource.common.MyException;

import java.io.IOException;
import java.util.List;
import java.util.Map;


/*****
 * <pre>
 * 类名称：WidgetInfoService
 * 类描述：
 * 创建人：donghongguang
 * 创建时间：2017年3月23日 上午9:28:34
 * 修改人：
 * 修改时间：
 * </pre>
 * @version 1.0.0
 */
public interface WidgetInfoService
{
    
    /*****
     * 功能：获取控件列表
     * 创建人：donghongguang
     * 创建时间：2017年3月23日 上午9:39:41
     * @param 
     * @return 
     * @version 1.0.0
     */
    public List<Map> getWidgetInfoList(Map request);
    
    /** 查询上传的控件列表总数 */
    public int getWidgetInfoListCount(Map map);
    /*****
     * 功能：插入上传控件信息
     * 创建人：donghongguang
     * 创建时间：2017年3月23日 上午9:39:45
     * @param 
     * @return 
     * @version 1.0.0
     */
    public int addWidgetInfo(WidgetInfo widgetInfo);
    
    /*****
     * 功能：获取控件信息
     * 创建人：donghongguang
     * 创建时间：2017年3月24日 上午11:31:40
     * @param 
     * @return 
     * @version 1.0.0
     */
    public WidgetInfo getWidgetInfo(WidgetInfo widgetInfo);
    
    /*****
     * 功能：获取控件扩展信息
     * 创建人：donghongguang
     * 创建时间：2017年3月24日 上午11:31:40
     * @param 
     * @return 
     * @version 1.0.0
     */
    public WidgetExtendInfo getWidgetExtendInfo(WidgetExtendInfo widgetExtendInfo);
    
    /*****
     * 功能：下载次数加一
     * 创建人：donghongguang
     * 创建时间：2017年3月24日 上午11:31:40
     * @param 
     * @return 
     * @version 1.0.0
     */
    public int updatetWidgetDownloadTimes(WidgetExtendInfo widgetExtendInfo);
    
    /*****
     * 功能：更新控件信息
     * 创建人：donghongguang
     * 创建时间：2017年3月23日 上午9:39:45
     * @param 
     * @return 
     * @version 1.0.0
     */
    public int updateWidgetInfo(WidgetInfo widgetInfo);
    
    /*****
     * 功能：更新控件基本信息
     * 创建人：donghongguang
     * 创建时间：2017年3月23日 上午9:39:45
     * @param 
     * @return 
     * @version 1.0.0
     */
    public int updateWidgetInfoBase(WidgetInfo widgetInfo,WidgetExtendInfo widgetExtendInfo);
    
    /*****
     * 功能：修改控件扩展记录
     * 创建人：donghongguang
     * 创建时间：2017年3月23日 上午9:39:45
     * @param 
     * @return 
     * @version 1.0.0
     */
    public int updateWidgetExtendInfo(WidgetExtendInfo widgetExtendInfo);
    
    /*****
     * 功能：发布次数加一
     * 创建人：donghongguang
     * 创建时间：2017年5月16日 上午11:31:40
     * @param 
     * @return 
     * @version 1.0.0
     */
    public int updatetWidgetReleaseTimes(WidgetExtendInfo widgetExtendInfo);
    
    /*****
     * 功能：企业上传控件统计排名
     * 创建人：donghongguang
     * 创建时间：2017年5月18日 下午1:25:40
     * @param 
     * @return 
     * @version 1.0.0
     */
    public List<Map> queryWidgetNum(Map request);
    
    /*****
     * 功能：企业控件总发布次数统计排名 
     * 创建人：donghongguang
     * 创建时间：2017年5月18日 下午1:25:40
     * @param 
     * @return 
     * @version 1.0.0
     */
    public List<Map> queryWidgetAllReleaseNum(Map request);
    
    /*****
     * 功能：企业控件发布下载次数统计排名
     * 创建人：donghongguang
     * 创建时间：2017年5月18日 下午1:25:40
     * @param 
     * @return 
     * @version 1.0.0
     */
    public List<Map> queryWidgetReleaseNum(Map request);
    public int queryWidgetReleaseNumCount(Map map);
    
    /*****
     * 功能：按类型统计 控件量 
     * 创建人：donghongguang
     * 创建时间：2017年5月18日 下午1:25:40
     * @param 
     * @return 
     * @version 1.0.0
     */
    public List<Map> queryWidgetNumByType(Map request);

    /**
     * 查询总共的上传文件总量
     * @return
     */
    public int queryCountWidget();

    /**
     * 查询总共的上传文件总量大小，以b为单位
     * @return
     */
    public Long queryWidgetSize();



    /**
     * 查询各个文件类型的总量，各个类型的文件占用空间大小
     * @return
     */
    public List<Map> queryWidgetType();


    public ResponseVo uploadFile(Map map) throws IOException, MyException, Exception;

}
