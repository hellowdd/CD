package com.bocom.dao;

import java.util.List;

import com.bocom.domain.ApplyShare;

/*****
 * 类名称：TapplyShare    
 * 类描述：数据访问
 * 创建人：donghongguang
 * 创建时间：2014年12月16日 下午8:29:34
 * 
 * @version 1.0.0
 */
public interface ApplyShareDao
{

    /*****
     * 功能：添加一条权限记录
     * 创建人：donghongguang
     * 创建时间：2017年6月29日 下午1:56:41
     * @param 
     * @return 
     * @version 1.0.0
     */
    public int insert(ApplyShare applyShare);
    
    /** 删除权限 */
    public int deleteWidgetShare(ApplyShare applyShare);
    
    /** 查找某控件赋予权限的部门 */
    public List<ApplyShare> selectAllByWidget(ApplyShare applyShare);
}
