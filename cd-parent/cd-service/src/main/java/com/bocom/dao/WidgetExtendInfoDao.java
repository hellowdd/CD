package com.bocom.dao;

import com.bocom.domain.WidgetExtendInfo;

import java.util.List;


/*****
 * <pre>
 * 类名称：WidgetExtendInfoDao
 * 类描述：控件扩展数据访问层
 * 创建人：donghongguang
 * 创建时间：2017年3月23日 下午2:45:40
 * 修改人：
 * 修改时间：
 * </pre>
 * @version 1.0.0
 */
public interface WidgetExtendInfoDao
{

    public List<WidgetExtendInfo> selectAll(WidgetExtendInfo widgetExtendInfo);
    
    public int insert(WidgetExtendInfo widgetExtendInfo);
    /** 更新信息 */
    public int updateWidgetExtendInfo(WidgetExtendInfo widgetExtendInfo);
    /** 修改下载次数+1 */
    public int updateWidgetDownloadTimes(WidgetExtendInfo widgetExtendInfo);
    /** 修改发布次数+1 */
    public int updateWidgetReleaseTimes(WidgetExtendInfo widgetExtendInfo);
    /** 获取控件扩展记录 */
    public WidgetExtendInfo selectWidgetExtendInfo(WidgetExtendInfo widgetExtendInfo);
    /** 批量修改分类 */
    public int updateWidgetType(WidgetExtendInfo widgetExtendInfo);

    public int insertSelective(WidgetExtendInfo widgetExtendInfo);
}
