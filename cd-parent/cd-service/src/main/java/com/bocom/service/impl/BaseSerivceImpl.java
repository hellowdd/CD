package com.bocom.service.impl;

import com.bocom.service.BaseService;
import com.bocom.domain.OperationLog;
import com.bocom.dto.session.SessionUserInfo;
import com.bocom.service.OperationLogService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;


/*****
 * 类名称：BaseSerivceImpl
 * 类描述：基础实现
 * 创建人：donghongguang
 * 创建时间：2017年3月27日 下午1:26:03
 * 修改人：
 * 修改时间：
 * @version 1.0.0
 */
public class BaseSerivceImpl implements BaseService
{
    @Autowired
    private OperationLogService operationLogService;
    @Autowired
    protected HttpServletRequest request;

    /*****
     * 功能：保存日志
     * 创建人：donghongguang
     * 创建时间：2017年3月27日 下午1:27:36
     * @param 
     * @return 
     * @version 1.0.0
     */
    public void saveLog(String opertionType, String content)
    {
//        OperationLog operationLog = new OperationLog();
        HttpSession session = request.getSession();
        SessionUserInfo userInfo = (SessionUserInfo) session.getAttribute("sessionUserInfo");
//        operationLog.setOperateContent(content);
//        operationLog.setOperateTime(new Date());
//        operationLog.setOperateType(opertionType);
//        //赋值用户信息
//        operationLog.setOrgId(userInfo.getOrgCode());
//        operationLog.setOrgName(userInfo.getOrgName());
//        operationLog.setUserid(userInfo.getUserId());
//        operationLog.setUsername(userInfo.getUserName());
//        
//        operationLogService.addOperationLog(operationLog);
        this.saveLog(opertionType, content, userInfo);
    }
    
    /*****
     * 功能：保存日志
     * 创建人：donghongguang
     * 创建时间：2017年3月27日 下午1:27:36
     * @param 
     * @return 
     * @version 1.0.0
     */
    public void saveLog(String opertionType, String content,SessionUserInfo userInfo)
    {
        OperationLog operationLog = new OperationLog();
        
        operationLog.setOperateContent(content);
        operationLog.setOperateTime(new Date());
        operationLog.setOperateType(opertionType);
        //赋值用户信息
        operationLog.setOrgId(userInfo.getOrgCode());
        operationLog.setOrgName(userInfo.getOrgName());
        operationLog.setUserid(userInfo.getUserId());
//        operationLog.setUsername(userInfo.getUserName());
        operationLog.setUsername(userInfo.getPoliceName());
        
        operationLogService.addOperationLog(operationLog);
    }
    
}
