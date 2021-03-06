package com.bocom.business.impl;

import com.bocom.business.SpaceManageBusiness;
import com.bocom.domain.ApplyAudit;
import com.bocom.domain.ApplySpace;
import com.bocom.domain.SpaceManage;
import com.bocom.dto.ApplyAuditDto;
import com.bocom.dto.session.SessionUserInfo;
import com.bocom.service.ApplyAuditService;
import com.bocom.service.ApplySpaceService;
import com.bocom.service.SpaceManageService;
import com.bocom.util.FormatUtils;
import com.bocom.util.ResponseVo;
import com.bocom.util.ResponseVoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/*****
 * 类名称：SpaceManageBusinessImpl
 * 类描述：空间管理业务类
 * 创建人：donghongguang
 * 创建时间：2017年4月10日 下午3:35:44
 * 修改人：
 * 修改时间：
 * @version 1.0.0
 */
@Component
public class SpaceManageBusinessImpl extends BaseSerivceImpl implements SpaceManageBusiness
{
    private Logger                 logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SpaceManageService spaceManageService;
    @Autowired
    private ApplySpaceService applySpaceService;
    @Autowired
    private ApplyAuditService applyAuditService;
    
    /*****
     * 功能：申请空间
     * 创建人：donghongguang
     * 创建时间：2017年4月10日 下午3:38:30
     * @param 
     * @return 
     * @version 1.0.0
     */
    @Override
    public ResponseVo applySpace(Map<String, Object> map)
    {
        /**
         * 1.获得用户信息
         * 2.转化为b单位的空间大小
         * 3.添加申请空间信息
         */
        logger.info("SpaceManageBusinessImpl applySpace begin ...");
        try
        {
            // 在session中记录此时的文件上传状态为上传中
            HttpSession session = request.getSession();
            SessionUserInfo userInfo = (SessionUserInfo) session.getAttribute("sessionUserInfo");
            int userId = userInfo.getUserId();
            
            String spaceUnit = (String) map.get("spaceUnit");
            Long spaceSize = (Long) map.get("spaceSize");
            //转换为字节大小
            spaceSize = FormatUtils.kb2bytes(spaceSize,spaceUnit);
            
            //添加申请空间信息
            ApplySpace applySpace = new ApplySpace();
            applySpace.setApplySapce(spaceSize);
            applySpace.setApplyTargetId(Integer.toString(userId));
            applySpace.setApplyTargetName(userInfo.getPoliceName());
            applySpace.setApplyTime(new Date());
            applySpace.setApplySpaceType("0");//申请类型  暂定 个人
            applySpace.setApplyUserid(userId);
            applySpace.setApplyReason((String) map.get("applyReason"));
            int result = applySpaceService.addApplySpace(applySpace);
            if(result>0){
                super.saveLog("申请空间","申请空间：" + spaceSize + spaceUnit, userInfo);
                return ResponseVoUtil.success(null);
            }
                
        }
        catch (Exception e)
        {
            logger.error("SpaceManageBusinessImpl applySpace error ..." + e);
        }
        logger.info("SpaceManageBusinessImpl applySpace end ...");
        return ResponseVoUtil.fail("申请失败", null);
    }

    /*****
     * 功能：审核空间
     * 创建人：donghongguang
     * 创建时间：2017年4月10日 下午3:38:30
     * @param 
     * @return 
     * @version 1.0.0
     */
    @Override
    public ResponseVo auditSpace(Map<String, Object> map)
    {
        /**
         * 1.获得用户信息
         * 2.查找待审核的信息
         * 3.获取待申请空间和 上一级剩余空间，空间不够则不能审核通过，
         * 4.同意则 增加空间信息，不同意则不增加
         * 4.更新审核表信息
         */
        logger.info("SpaceManageBusinessImpl applySpace begin ...");
        try
        {
            // 在session中记录此时的文件上传状态为上传中
            HttpSession session = request.getSession();
            SessionUserInfo userInfo = (SessionUserInfo) session.getAttribute("sessionUserInfo");
            int userId = userInfo.getUserId();
            
            //查找待审核的信息
            int applyId = (int) map.get("applyId");
            ApplyAudit applyAudit = new ApplyAudit();
            applyAudit.setId(applyId);
            applyAudit.setApplyType("0");
            applyAudit.setAuditStatus("0");
            ApplyAuditDto applyAuditDto = applyAuditService.getApplyAudit(applyAudit);
            if(null != applyAuditDto && applyAuditDto.getApplyId() != null){
                //获取待申请空间和 上一级剩余空间，空间不够则不能审核通过
                
                String applyResult = (String) map.get("applyResult");
                String remartk = (String) map.get("content");
                applyAudit.setAduditResult(applyResult);
                applyAudit.setApplyUserid(applyAuditDto.getApplyUserid());
                applyAudit.setAuditStatus("1");//已审核
                applyAudit.setAuditTime(new Date());
                applyAudit.setAuditUserId(userId);
                applyAudit.setAuditUsername(userInfo.getPoliceName());
                applyAudit.setRemark(remartk);
                int result = applyAuditService.updateApplyAudit(applyAudit);
                if(result >0){
                    super.saveLog("审核空间","审核空间：" + applyId + applyAuditDto.getApplyTargetName(), userInfo);
                    return ResponseVoUtil.success(null);
                }
                //同意则 增加空间信息，不同意则不增加
                
            }else{
                return ResponseVoUtil.fail("无审核信息", null);
            }
            
            
        }
        catch (Exception e)
        {
            logger.error("SpaceManageBusinessImpl applySpace error ..." + e);
        }
        logger.info("SpaceManageBusinessImpl applySpace end ...");
        return ResponseVoUtil.fail("审核失败", null);
    }

    /*****
     * 功能：获取个人空间信息
     * 创建人：donghongguang
     * 创建时间：2017年4月10日 下午3:38:30
     * @param 
     * @return 
     * @version 1.0.0
     */
    @Override
    public ResponseVo getSpaceInfo(Map<String, Object> map)
    {
       /**
        * 1.获得用户信息
        * 2.查找空间信息
        */
        try
        {
            // 在session中记录此时的文件上传状态为上传中
            HttpSession session = request.getSession();
            SessionUserInfo userInfo = (SessionUserInfo) session.getAttribute("sessionUserInfo");
            int userId = userInfo.getUserId();
            
            SpaceManage spaceManage = new SpaceManage();
            spaceManage.setUserId(userId);
            spaceManage.setUserType("0");//0个人，1组织
            spaceManage = spaceManageService.getSpaceMange(spaceManage);
            if(spaceManage != null && spaceManage.getId() != null){
                Map<String, Object> resultMap = new HashMap<>();
                //返回的空间信息，（处理为G/M/K的形式）
                resultMap.put("spaceTotal", FormatUtils.bytes2kb(spaceManage.getSpaceTotal()));
                resultMap.put("spaceUse", FormatUtils.bytes2kb(spaceManage.getSpaceUse()));
                resultMap.put("spaceRest", FormatUtils.bytes2kb(spaceManage.getSpaceRest()));
                resultMap.put("spaceTotalByte", spaceManage.getSpaceTotal());
                resultMap.put("spaceUseByte", spaceManage.getSpaceUse());
                resultMap.put("spaceRestByte", spaceManage.getSpaceRest());
                return ResponseVoUtil.success(resultMap);
            }else{
                return ResponseVoUtil.success("无空间信息");
            }
            
        }
        catch (Exception e)
        {
            logger.error("系统异常{}", e);
            return ResponseVoUtil.fail("系统异常", null);
        }
    }
    
}
