package com.bocom.service.impl;

import com.bocom.dao.ApplyAuditDao;
import com.bocom.dao.SpaceManageDao;
import com.bocom.domain.ApplyAudit;
import com.bocom.domain.SpaceManage;
import com.bocom.dto.ApplyAuditDto;
import com.bocom.service.ApplyAuditService;
import com.bocom.util.FormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*****
 * 类名称：ApplyAuditServiceImpl
 * 类描述：申请审核服务实现类
 * 创建人：donghongguang
 * 创建时间：2017年4月11日 上午10:08:29
 * 修改人：
 * 修改时间：
 * @version 1.0.0
 */
@Service
public class ApplyAuditServiceImpl implements ApplyAuditService {

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ApplyAuditDao applyAuditDao;
    @Autowired
    private SpaceManageDao spaceManageDao;

    @Override
    public int addApplyAudit(ApplyAudit applyAudit) {
        return 0;
    }

    @Override
    public int delete(ApplyAudit applyAudit) {
        return 0;
    }

    @Override
    public ApplyAuditDto getApplyAudit(ApplyAudit applyAudit) {

        return applyAuditDao.selectApplyAuditInfo(applyAudit);
    }

    /**
     * 获取列表
     *
     * @param applyAudit
     * @return
     */
    @Override
    public List<ApplyAudit> getApplyAuditList(ApplyAudit applyAudit) {
        logger.info("WidgetInfoServiceImpl getWidgetInfoList begin ...");
        List<ApplyAudit> applyAuditList = null;
        try {
            applyAuditList = applyAuditDao.selectApplyAuditList(applyAudit);
        } catch (Exception e) {
            logger.error("WidgetInfoServiceImpl getWidgetInfoList error ...", e);
            throw e;
        }
        logger.info("WidgetInfoServiceImpl getWidgetInfoList exit ...");
        return applyAuditList;
    }

    /**** 主要用于审核
     * @see com.bocom.service.ApplyAuditService#updateApplyAudit(com.bocom.domain.ApplyAudit)
     * 创建人：donghongguang
     * 创建时间：2017年4月12日 下午2:47:00
     * @version 1.0.0
     */
    @Override
    public int updateApplyAudit(ApplyAudit applyAudit) {
        /**
         * 1.判断 同意or不同意，不同意直接update审核表
         * 2.同意则 查找申请信息和 本人空间信息
         * 3.更新空间信息
         * 4.update审核表
         */
        if ("0".equals(applyAudit.getAduditResult())) {
            //更新审核表
            return applyAuditDao.updateApplyAuditInfo(applyAudit);
        } else if ("1".equals(applyAudit.getAduditResult())) {
            //查找申请信息和空间信息
            ApplyAuditDto applyAuditDto = applyAuditDao.selectApplyAuditInfo(applyAudit);
            if (applyAuditDto != null && applyAuditDto.getId() != null) {
                SpaceManage spaceManage = new SpaceManage();
                spaceManage.setUserType(applyAuditDto.getApplySpaceType());
                spaceManage.setUserId(Integer.parseInt(applyAuditDto.getApplyTargetId()));
                spaceManage = spaceManageDao.selectSpaceMangeInfo(spaceManage);
                if (spaceManage.getId() != null) {
                    //计算空间信息
                    Long spaceTotal = spaceManage.getSpaceTotal();
                    Long spaceRest = spaceManage.getSpaceRest();
                    Long applySapce = applyAuditDto.getApplySapce();

                    spaceTotal = FormatUtils.add(spaceTotal, applySapce);
                    spaceRest = FormatUtils.add(spaceRest, applySapce);
                    //更新空间表
                    spaceManage.setSpaceRest(spaceRest);
                    spaceManage.setSpaceTotal(spaceTotal);
                    int resultSpace = spaceManageDao.updateSpaceManage(spaceManage);
                    if (resultSpace > 0) {
                        //更新审核表
                        return applyAuditDao.updateApplyAuditInfo(applyAudit);
                    } else {
                        return 0;
                    }

                }
            }

        }

        return 0;
    }


}
