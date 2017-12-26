package com.bocom.service;

import java.util.List;

import com.bocom.domain.ApplyShare;
import com.bocom.domain.WidgetType;

public interface WidgetTypeService
{
    
    public List<WidgetType> getAll(WidgetType widgetType);
    
    public int addWidgetType(WidgetType widgetType);
    
    /** 修改类型 */
    public int updateWidgetType(WidgetType widgetType);
    
    /** 删除类型 */
    public int removeWidgetType(WidgetType widgetType);
    
    /** 查找某用户下的类型 */
    public List<WidgetType> getWidgetTypeInfoByUser(WidgetType widgetType);
    
    /** 查找类型信息 */
    public WidgetType getWidgetTypeInfoById(WidgetType widgetType);
    
    /*****
     * 功能：添加一条权限记录
     * 创建人：donghongguang
     * 创建时间：2017年6月29日 下午1:56:41
     * @param 
     * @return 
     * @version 1.0.0
     */
    public int addWidgetShare(ApplyShare applyShare);
    
    /** 删除权限 */
    public int removeWidgetShare(ApplyShare applyShare);
    
    /** 查找某控件赋予权限的部门 */
    public List<ApplyShare> getAllByWidget(ApplyShare applyShare);
}
