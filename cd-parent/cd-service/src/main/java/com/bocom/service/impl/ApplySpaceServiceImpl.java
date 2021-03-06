package com.bocom.service.impl;

import com.bocom.dao.ApplyAuditDao;
import com.bocom.dao.ApplySpaceDao;
import com.bocom.domain.ApplyAudit;
import com.bocom.domain.ApplySpace;
import com.bocom.service.ApplySpaceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/*****
 * 类名称：ApplySpaceServiceImpl
 * 类描述：申请空间服务实现类
 * 创建人：donghongguang
 * 创建时间：2017年4月11日 上午9:25:21
 * 修改人：
 * 修改时间：
 * @version 1.0.0
 */
@Service
public class ApplySpaceServiceImpl implements ApplySpaceService {

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ApplySpaceDao applySpaceDao;
    @Autowired
    private ApplyAuditDao applyAuditDao;


    /**** 
     * @see com.bocom.service.ApplySpaceService#addApplySpace(com.bocom.domain.ApplySpace)
     * 创建人：donghongguang
     * 创建时间：2017年4月11日 下午1:54:47
     * @version 1.0.0
     */
    @Override
    public int addApplySpace(ApplySpace applySpace) {
        /**
         * 1 申请表
         * 2.审核表
         */
        //往申请表填写信息
        applySpaceDao.insert(applySpace);
        if (applySpace.getId() != null) {
            //往审核表填写信息
            ApplyAudit applyAudit = new ApplyAudit();
            applyAudit.setAuditStatus("0");
            applyAudit.setApplyId(applySpace.getId());
            applyAudit.setApplyTime(new Date());
            applyAudit.setApplyType("0");//表示申请空间
            applyAudit.setApplyUserid(applySpace.getApplyUserid());
            int result = applyAuditDao.insert(applyAudit);
            return result;

        }
        return 0;
    }

    @Override
    public int delete(ApplySpace applySpace) {
        return 0;
    }

    @Override
    public ApplySpace getApplySpace(ApplySpace applySpace) {
        return null;
    }

    @Override
    public List<ApplySpace> getApplySpaceList(ApplySpace applySpace) {
        return null;
    }

    @Override
    public int updateApplySpace(ApplySpace applySpace) {
        return 0;
    }


}
