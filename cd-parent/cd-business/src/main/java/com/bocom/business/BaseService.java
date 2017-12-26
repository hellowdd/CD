package com.bocom.business;

import com.bocom.dto.session.SessionUserInfo;

/*****
 * 类名称：BaseService
 * 类描述：基础实现
 * 创建人：donghongguang
 * 创建时间：2017年3月27日 下午1:26:22
 * 修改人：
 * 修改时间：
 * @version 1.0.0
 */
public interface BaseService
{
    /*****
     * 功能：保存日志
     * 创建人：donghongguang
     * 创建时间：2017年3月27日 下午1:27:36
     * @param 
     * @return 
     * @version 1.0.0
     */
    public void saveLog(String opertionType,String content);
    /*****
     * 功能：保存日志
     * 创建人：donghongguang
     * 创建时间：2017年3月27日 下午1:27:36
     * @param 
     * @return 
     * @version 1.0.0
     */
    public void saveLog(String opertionType, String content, SessionUserInfo userInfo);
}
