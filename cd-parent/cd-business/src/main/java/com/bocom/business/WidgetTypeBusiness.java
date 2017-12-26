package com.bocom.business;

import java.util.List;
import java.util.Map;

import com.bocom.domain.ApplyShare;
import com.bocom.domain.WidgetType;
import com.bocom.util.ResponseVo;

/*****
 * 类名称：WidgetTypeBusiness
 * 类描述：
 * 创建人：donghongguang
 * 创建时间：2017年5月15日 下午3:57:58
 * 修改人：
 * 修改时间：
 * @version 1.0.0
 */
public interface WidgetTypeBusiness
{
    
    /*****
     * 功能：获取控件类型信息
     * 创建人：donghongguang
     * 创建时间：2017年5月15日 下午4:06:08
     * @param 
     * @return 
     * @version 1.0.0
     */
    public List<WidgetType> getWidgetTypeInfo(WidgetType widgetType);
    
    /*****
     * 功能：创建控件类型信息
     * 创建人：donghongguang
     * 创建时间：2017年5月15日 下午4:07:39
     * @param 
     * @return 
     * @version 1.0.0
     */
    public ResponseVo createWidgetType(WidgetType widgetType);
    
    /*****
     * 功能：删除控件类型信息
     * 创建人：donghongguang
     * 创建时间：2017年5月15日 下午4:08:12
     * @param 
     * @return 
     * @version 1.0.0
     */
    public Map removeWidgetType(WidgetType widgetType);
    
    /*****
     * 功能：修改控件类型信息
     * 创建人：donghongguang
     * 创建时间：2017年5月15日 下午4:08:47
     * @param 
     * @return 
     * @version 1.0.0
     */
    public ResponseVo updateWidgetType(WidgetType widgetType);
    
    /*****
     * 功能：创建赋予权限信息
     * 创建人：donghongguang
     * 创建时间：2017年5月15日 下午4:07:39
     * @param 
     * @return 
     * @version 1.0.0
     */
    public ResponseVo shareWidgetToOffice(ApplyShare applyShare);
    
    /** 删除权限 */
    public Map unshareWidgetToOffice(ApplyShare applyShare);
    
    /** 查找某控件赋予权限的部门 */
    public List<ApplyShare> getWidgetOffice(ApplyShare applyShare);
    
    /** 查找某控件赋予权限的部门 */
    public String getOffice();
}
