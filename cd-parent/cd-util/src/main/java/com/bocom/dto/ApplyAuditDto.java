package com.bocom.dto;

import com.bocom.domain.ApplyAudit;

public class ApplyAuditDto extends ApplyAudit
{
    private String applyReason;
    private Long applySapce;
    private String applySpaceType;
    private String applyTargetId;
    private String applyTargetName;
    public String getApplyReason()
    {
        return applyReason;
    }
    public void setApplyReason(String applyReason)
    {
        this.applyReason = applyReason;
    }
    public Long getApplySapce()
    {
        return applySapce;
    }
    public void setApplySapce(Long applySapce)
    {
        this.applySapce = applySapce;
    }
    public String getApplySpaceType()
    {
        return applySpaceType;
    }
    public void setApplySpaceType(String applySpaceType)
    {
        this.applySpaceType = applySpaceType;
    }
    public String getApplyTargetId()
    {
        return applyTargetId;
    }
    public void setApplyTargetId(String applyTargetId)
    {
        this.applyTargetId = applyTargetId;
    }
    public String getApplyTargetName()
    {
        return applyTargetName;
    }
    public void setApplyTargetName(String applyTargetName)
    {
        this.applyTargetName = applyTargetName;
    }
    
    
}
