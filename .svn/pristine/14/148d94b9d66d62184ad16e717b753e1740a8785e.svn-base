package com.bocom.business.impl;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bocom.business.OperationLogBusiness;
import com.bocom.domain.OperationLog;
import com.bocom.dto.session.SessionUserInfo;
import com.bocom.service.OperationLogService;

/*****
 * 类名称：OperationLogBusinessImpl
 * 类描述：操作日志业务类
 * 创建人：donghongguang
 * 创建时间：2017年3月27日 下午4:22:45
 * 修改人：
 * 修改时间：
 * @version 1.0.0
 */
@Component
public class OperationLogBusinessImpl extends BaseSerivceImpl implements OperationLogBusiness
{

    @Autowired
    private OperationLogService operationLogService;
    /*****
     * 功能：获取操作日志列表
     * 创建人：donghongguang
     * 创建时间：2017年3月27日 下午4:24:58
     * @param 
     * @return 
     * @version 1.0.0
     */
    @Override
    public List<OperationLog> getOperationLog(OperationLog operationLog)
    {
        /**
         * 个人只查个人
         * 组织可以查组织下的
         * 管理员可以查全部的
         */
     // 在session中记录此时的文件上传状态为上传中
        HttpSession session = request.getSession();
        SessionUserInfo userInfo = (SessionUserInfo) session.getAttribute("sessionUserInfo");
        operationLog.setUserid(userInfo.getUserId());
        return operationLogService.selectOperationLog(operationLog);
    }
    
    /*****
     * 功能：获取操作日志列表总数
     * 创建人：donghongguang
     * 创建时间：2017年3月23日 上午11:15:28
     * @param 
     * @return 
     * @version 1.0.0
     */
    public int getOperationLogCount(OperationLog log){
        return operationLogService.getWidgetInfoListCount(log);
    }
    
}
