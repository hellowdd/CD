package com.bocom.domain;

public class WidgetType
{
    
    /** 类型id */
    private java.lang.Integer id;
    /** 控件类型名称 */
    private java.lang.String  widgetTypeName;
    /** 控件类型说明 */
    private java.lang.String  widgetTypeContent;
    /** 创建时间 */
    private java.util.Date    createTime;
    /** 创建人 */
    private java.lang.Integer createUserid;
    /** 创建人 */
    private java.lang.String  createUsername;
    /** 更新时间 */
    private java.util.Date    updateTime;
    /** 是否属于共有（0，属于私人，1属于共有不可修改） */
    private java.lang.String  isAll;
    
    private java.lang.String ids;
    
    /** 获取类型id */
    public java.lang.Integer getId()
    {
        return this.id;
    }
    
    /** 设置类型id */
    public void setId(java.lang.Integer value)
    {
        this.id = value;
    }
    
    /** 获取控件类型名称 */
    public java.lang.String getWidgetTypeName()
    {
        return this.widgetTypeName;
    }
    
    /** 设置控件类型名称 */
    public void setWidgetTypeName(java.lang.String value)
    {
        this.widgetTypeName = value;
    }
    
    /** 获取控件类型说明 */
    public java.lang.String getWidgetTypeContent()
    {
        return this.widgetTypeContent;
    }
    
    /** 设置控件类型说明 */
    public void setWidgetTypeContent(java.lang.String value)
    {
        this.widgetTypeContent = value;
    }
    
    /** 获取创建时间 */
    public java.util.Date getCreateTime()
    {
        return this.createTime;
    }
    
    /** 设置创建时间 */
    public void setCreateTime(java.util.Date value)
    {
        this.createTime = value;
    }
    
    /** 获取创建人 */
    public java.lang.Integer getCreateUserid()
    {
        return this.createUserid;
    }
    
    /** 设置创建人 */
    public void setCreateUserid(java.lang.Integer value)
    {
        this.createUserid = value;
    }
    
    /** 获取创建人 */
    public java.lang.String getCreateUsername()
    {
        return this.createUsername;
    }
    
    /** 设置创建人 */
    public void setCreateUsername(java.lang.String value)
    {
        this.createUsername = value;
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
    
    /** 获取是否属于共有（0，属于私人，1属于共有不可修改） */
    public java.lang.String getIsAll()
    {
        return this.isAll;
    }
    
    /** 设置是否属于共有（0，属于私人，1属于共有不可修改） */
    public void setIsAll(java.lang.String value)
    {
        this.isAll = value;
    }

    public java.lang.String getIds()
    {
        return ids;
    }

    public void setIds(java.lang.String ids)
    {
        this.ids = ids;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("WidgetType [id=");
        builder.append(id);
        builder.append(", widgetTypeName=");
        builder.append(widgetTypeName);
        builder.append(", widgetTypeContent=");
        builder.append(widgetTypeContent);
        builder.append(", createTime=");
        builder.append(createTime);
        builder.append(", createUserid=");
        builder.append(createUserid);
        builder.append(", createUsername=");
        builder.append(createUsername);
        builder.append(", updateTime=");
        builder.append(updateTime);
        builder.append(", isAll=");
        builder.append(isAll);
        builder.append("]");
        return builder.toString();
    }
    
    
}
