package com.bocom.dao;

import com.bocom.domain.ApplySpace;

/*****
 * 类名称：TapplySpace    
 * 类描述：数据访问
 * 创建人：donghongguang
 * 创建时间：2017年04月10日 下午8:29:34
 * 
 * @version 1.0.0
 */
public interface ApplySpaceDao
{

    /*****
     * 功能：添加一条申请记录
     * 创建人：donghongguang
     * 创建时间：2017年4月11日 下午1:56:41
     * @param 
     * @return 
     * @version 1.0.0
     */
    public int insert(ApplySpace applySpace);
}
