package com.bocom.domain;


/*****
 * <pre>
 * 类名称：WidgetInfo
 * 类描述：控件信息实体类
 * 创建人：donghongguang
 * 创建时间：2017年3月23日 上午9:54:23
 * 修改人：
 * 修改时间：
 * </pre>
 * @version 1.0.0
 */
public class WidgetInfo extends BaseBean
{
    
    /** id */
    private java.lang.Integer id;
    /** 件控名称 */
    private java.lang.String  widgetName;
    /** 件控显示名称 */
    private java.lang.String  widgetNameShow;
    /** 件控保存地址 */
    private java.lang.String  storagePath;
    /** 件控大小 */
    private java.lang.Integer widgetSize;
    /** 件控版本 */
    private java.lang.String  widgetVersion;
    /** 源来 */
    private java.lang.String  widgetFrom;
    /** 件控扩展名 */
    private java.lang.String  widgetExtension;
    /** 传上时间 */
    private java.util.Date    uploadTime;
    /** 传上人id */
    private java.lang.Integer uploadUserid;
    /** 传上人名称 */
    private java.lang.String  uploadUsername;
    /** 新人更id */
    private java.lang.Integer updateUserid;
    /** 新人更名称 */
    private java.lang.String  updateUsername;
    /** 更新时间 */
    private java.util.Date    updateTime;
    /** md5 */
    private java.lang.String  md5;
    /** 载下次数 */
    private java.lang.Integer downloadTimes;
    /** 注备说明 */
    private java.lang.String  remarks;
    /** 共享范围 */
    private java.lang.String  shareRange;
    /** 否是删除 */
    private java.lang.Integer isDelete;
    /** 是否物理删除 */
    private java.lang.Integer isDeletePhysically;
    
    private Integer directoryId;
    private Integer widgetTypeId;
    private Integer appType;
    
    
    public Integer getDirectoryId()
    {
        return directoryId;
    }

    public void setDirectoryId(Integer directoryId)
    {
        this.directoryId = directoryId;
    }

    /** 获取id */
    public java.lang.Integer getId()
    {
        return this.id;
    }
    
    /** 设置id */
    public void setId(java.lang.Integer value)
    {
        this.id = value;
    }
    
    /** 获取件控名称 */
    public java.lang.String getWidgetName()
    {
        return this.widgetName;
    }
    
    /** 设置件控名称 */
    public void setWidgetName(java.lang.String value)
    {
        this.widgetName = value;
    }
    
    /** 获取件控显示名称 */
    public java.lang.String getWidgetNameShow()
    {
        return this.widgetNameShow;
    }
    
    /** 设置件控显示名称 */
    public void setWidgetNameShow(java.lang.String value)
    {
        this.widgetNameShow = value;
    }
    
    /** 获取件控保存地址 */
    public java.lang.String getStoragePath()
    {
        return this.storagePath;
    }
    
    /** 设置件控保存地址 */
    public void setStoragePath(java.lang.String value)
    {
        this.storagePath = value;
    }
    
    /** 获取件控大小 */
    public java.lang.Integer getWidgetSize()
    {
        return this.widgetSize;
    }
    
    /** 设置件控大小 */
    public void setWidgetSize(java.lang.Integer value)
    {
        this.widgetSize = value;
    }
    
    /** 获取件控版本 */
    public java.lang.String getWidgetVersion()
    {
        return this.widgetVersion;
    }
    
    /** 设置件控版本 */
    public void setWidgetVersion(java.lang.String value)
    {
        this.widgetVersion = value;
    }
    
    /** 获取源来 */
    public java.lang.String getWidgetFrom()
    {
        return this.widgetFrom;
    }
    
    /** 设置源来 */
    public void setWidgetFrom(java.lang.String value)
    {
        this.widgetFrom = value;
    }
    
    /** 获取件控扩展名 */
    public java.lang.String getWidgetExtension()
    {
        return this.widgetExtension;
    }
    
    /** 设置件控扩展名 */
    public void setWidgetExtension(java.lang.String value)
    {
        this.widgetExtension = value;
    }
    
    /** 获取传上时间 */
    public java.util.Date getUploadTime()
    {
        return this.uploadTime;
    }
    
    /** 设置传上时间 */
    public void setUploadTime(java.util.Date value)
    {
        this.uploadTime = value;
    }
    
    /** 获取传上人id */
    public java.lang.Integer getUploadUserid()
    {
        return this.uploadUserid;
    }
    
    /** 设置传上人id */
    public void setUploadUserid(java.lang.Integer value)
    {
        this.uploadUserid = value;
    }
    
    /** 获取传上人名称 */
    public java.lang.String getUploadUsername()
    {
        return this.uploadUsername;
    }
    
    /** 设置传上人名称 */
    public void setUploadUsername(java.lang.String value)
    {
        this.uploadUsername = value;
    }
    
    /** 获取新人更id */
    public java.lang.Integer getUpdateUserid()
    {
        return this.updateUserid;
    }
    
    /** 设置新人更id */
    public void setUpdateUserid(java.lang.Integer value)
    {
        this.updateUserid = value;
    }
    
    /** 获取新人更名称 */
    public java.lang.String getUpdateUsername()
    {
        return this.updateUsername;
    }
    
    /** 设置新人更名称 */
    public void setUpdateUsername(java.lang.String value)
    {
        this.updateUsername = value;
    }
    
    /** 获取更新时间 */
    public java.util.Date getUpdateTime()
    {
        return this.updateTime;
    }
    
    /** 设置更新时间 */
    public void setUpdateTime(java.util.Date value)
    {
        this.updateTime = value;
    }
    
    /** 获取md5 */
    public java.lang.String getMd5()
    {
        return this.md5;
    }
    
    /** 设置md5 */
    public void setMd5(java.lang.String value)
    {
        this.md5 = value;
    }
    
    /** 获取载下次数 */
    public java.lang.Integer getDownloadTimes()
    {
        return this.downloadTimes;
    }
    
    /** 设置载下次数 */
    public void setDownloadTimes(java.lang.Integer value)
    {
        this.downloadTimes = value;
    }
    
    /** 获取注备说明 */
    public java.lang.String getRemarks()
    {
        return this.remarks;
    }
    
    /** 设置注备说明 */
    public void setRemarks(java.lang.String value)
    {
        this.remarks = value;
    }
    
    /** 获取共享范围 */
    public java.lang.String getShareRange()
    {
        return this.shareRange;
    }
    
    /** 设置共享范围 */
    public void setShareRange(java.lang.String value)
    {
        this.shareRange = value;
    }
    
    /** 获取否是删除 */
    public java.lang.Integer getIsDelete()
    {
        return this.isDelete;
    }
    
    /** 设置否是删除 */
    public void setIsDelete(java.lang.Integer value)
    {
        this.isDelete = value;
    }
    
    /** 获取是否物理删除 */
    public java.lang.Integer getIsDeletePhysically()
    {
        return this.isDeletePhysically;
    }
    
    /** 设置是否物理删除 */
    public void setIsDeletePhysically(java.lang.Integer value)
    {
        this.isDeletePhysically = value;
    }
    
    public Integer getWidgetTypeId()
    {
        return widgetTypeId;
    }

    public void setWidgetTypeId(Integer widgetTypeId)
    {
        this.widgetTypeId = widgetTypeId;
    }

    public Integer getAppType()
    {
        return appType;
    }

    public void setAppType(Integer appType)
    {
        this.appType = appType;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("WidgetInfo [id=");
        builder.append(id);
        builder.append(", widgetName=");
        builder.append(widgetName);
        builder.append(", widgetNameShow=");
        builder.append(widgetNameShow);
        builder.append(", storagePath=");
        builder.append(storagePath);
        builder.append(", widgetSize=");
        builder.append(widgetSize);
        builder.append(", widgetVersion=");
        builder.append(widgetVersion);
        builder.append(", widgetFrom=");
        builder.append(widgetFrom);
        builder.append(", widgetExtension=");
        builder.append(widgetExtension);
        builder.append(", uploadTime=");
        builder.append(uploadTime);
        builder.append(", uploadUserid=");
        builder.append(uploadUserid);
        builder.append(", uploadUsername=");
        builder.append(uploadUsername);
        builder.append(", updateUserid=");
        builder.append(updateUserid);
        builder.append(", updateUsername=");
        builder.append(updateUsername);
        builder.append(", updateTime=");
        builder.append(updateTime);
        builder.append(", md5=");
        builder.append(md5);
        builder.append(", downloadTimes=");
        builder.append(downloadTimes);
        builder.append(", remarks=");
        builder.append(remarks);
        builder.append(", shareRange=");
        builder.append(shareRange);
        builder.append(", isDelete=");
        builder.append(isDelete);
        builder.append(", isDeletePhysically=");
        builder.append(isDeletePhysically);
        builder.append(", directoryId=");
        builder.append(directoryId);
        builder.append(", widgetTypeId=");
        builder.append(widgetTypeId);
        builder.append("]");
        return builder.toString();
    }
    
}
