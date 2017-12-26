package com.bocom.web.controller.widget;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bocom.business.OperationLogBusiness;
import com.bocom.domain.OperationLog;
import com.bocom.util.ResponseVo;
import com.bocom.util.ResponseVoUtil;

/*****
 * 类名称：OperationLogController
 * 类描述：操作日志控制类
 * 创建人：donghongguang
 * 创建时间：2017年4月12日 下午5:31:22
 * 修改人：
 * 修改时间：
 * @version 1.0.0
 */
@Controller
@RequestMapping("/manager/operationLog")
public class OperationLogController
{
    @Autowired
    private OperationLogBusiness operationLogBusiness;
    
    /*****
     * 功能：查找日志信息
     * 创建人：donghongguang
     * 创建时间：2017年3月22日 下午3:10:24
     * @param
     * @return
     * @version 1.0.0
     */
    @RequestMapping("/getLogInfoList")
    @ResponseBody
    public ResponseVo getLogInfoList(@RequestParam(required = false) Integer pageNum,
            @RequestParam(required = false) Integer pageSize)
    {
        OperationLog operationLog = new OperationLog();
        pageSize = pageSize == null ?20:pageSize;
        operationLog.setPageSize(pageSize == null ?20:pageSize);
        operationLog.setPageStart(pageNum == null ?0:(pageNum - 1) * pageSize);
        Map<String, Object> logMap = new HashMap<String, Object>();
        logMap.put("logList", operationLogBusiness.getOperationLog(operationLog));
        logMap.put("logCount", operationLogBusiness.getOperationLogCount(operationLog));
        
        return ResponseVoUtil.success(logMap);
    }
}
