package com.bocom.service;

import java.util.List;

import com.bocom.domain.ApplyAudit;
import com.bocom.dto.ApplyAuditDto;



/*****
 * 类名称：ApplyAuditService
 * 类描述：审核服务接口
 * 创建人：donghongguang
 * 创建时间：2017年4月11日 上午10:03:10
 * 修改人：
 * 修改时间：
 * @version 1.0.0
 */
public interface ApplyAuditService
{
    /**
     * 添加
     * 
     * @param applyAudit
     * @return
     */
    public int addApplyAudit(ApplyAudit applyAudit);

    /*****
     * 功能：删除
     * 创建人：donghongguang
     * 创建时间：2017年4月11日 上午10:07:18
     * @param 
     * @return 
     * @version 1.0.0
     */
    public int delete(ApplyAudit applyAudit);

    /**
     * 获取单个
     * 
     * @param applyAudit
     * @return
     */
    public ApplyAuditDto getApplyAudit(ApplyAudit applyAudit);

    /**
     * 获取列表
     * 
     * @param applyAudit
     * @return
     */
    public List<ApplyAudit> getApplyAuditList(ApplyAudit applyAudit);

    /**
     * 更新
     * 
     * @param applyAudit
     * @return
     */
    public int updateApplyAudit(ApplyAudit applyAudit);
   
}
