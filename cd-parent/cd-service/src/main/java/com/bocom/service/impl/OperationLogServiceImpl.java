package com.bocom.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bocom.dao.OperationLogDao;
import com.bocom.domain.OperationLog;
import com.bocom.service.OperationLogService;

/*****
 * 类名称：OperationLogServiceImpl
 * 类描述：操作日志
 * 创建人：donghongguang
 * 创建时间：2017年3月27日 上午11:27:04
 * 修改人：
 * 修改时间：
 * @version 1.0.0
 */
@Service
public class OperationLogServiceImpl implements OperationLogService
{
    @Autowired
    private OperationLogDao operationLogDao;

    /*****
     * 功能：插入一条操作记录
     * 创建人：donghongguang
     * 创建时间：2017年3月27日 上午11:34:46
     * @param 
     * @return 
     * @version 1.0.0
     */
    @Override
    public int addOperationLog(OperationLog operationLog)
    {
        return operationLogDao.insert(operationLog);
    }
    /*****
     * 功能：查询日志信息
     * 创建人：donghongguang
     * 创建时间：2017年3月27日 下午3:57:53
     * @param 
     * @return 
     * @version 1.0.0
     */
    @Override
    public List<OperationLog> selectOperationLog(OperationLog operationLog)
    {
        //处理时间问题
        return operationLogDao.selectOperationLog(operationLog);
    }
    /** 查询日志列表总数 */
    public int getWidgetInfoListCount(OperationLog operationLog){
        return operationLogDao.selectOperationLogCount(operationLog);
    }
    
}
