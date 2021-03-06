package com.bocom.dao;

import com.bocom.domain.WidgetInfo;

import java.util.List;
import java.util.Map;


/*****
 * <pre>
 * 类名称：TwidgetInfoDao
 * 类描述：数据访问
 * 创建人：donghongguang
 * 创建时间：2017年3月23日 上午9:41:26
 * 修改人：
 * 修改时间：
 * </pre>
 * @version 1.0.0
 */
public interface WidgetInfoDao
{
    /** 查询上传的控件列表 */
    public List<Map> queryWidgetInfoList(Map map);
    
    /** 查询上传的控件列表总数 */
    public int queryWidgetInfoListCount(Map map);
    
    /** 查询所有上传的控件列表 */
    public List<WidgetInfo> selectAll(WidgetInfo widgetInfo);
    
    public int insert(WidgetInfo widgetInfo);
    
    public int updateWidgetInfo(WidgetInfo widgetInfo);
    
    /** 查询上传的控件信息 */
    public WidgetInfo selectWidgetInfo(WidgetInfo widgetInfo);
    
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
     * 查询总共的上传文件大小
     * @return
     */
    public Long queryWidgetSize();


    /**
     * 查询各个文件类型的总量，各个类型的文件占用空间大小
     * @return
     */
    public List<Map> queryWidgetType();

    public List<Map> queryMD5(Map map);

    public int insertWidget(WidgetInfo widgetInfo);
}
