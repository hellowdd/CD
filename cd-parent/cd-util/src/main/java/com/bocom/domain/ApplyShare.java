package com.bocom.domain;

/*****
 * 类名称：ApplyShare
 * 类描述：申请共享控件
 * 创建人：donghongguang
 * 创建时间：2017年4月11日 上午10:22:34
 * 修改人：
 * 修改时间：
 * 
 * @version 1.0.0
 */
public class ApplyShare extends BaseBean
{
    
    /** id */
    private java.lang.Integer id;
    /** 控件id */
    private java.lang.Integer widgetId;
    /** 共享范围 */
    private java.lang.String  shareRange;
    
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
    
    /** 获取控件id */
    public java.lang.Integer getWidgetId()
    {
        return this.widgetId;
    }
    
    /** 设置控件id */
    public void setWidgetId(java.lang.Integer value)
    {
        this.widgetId = value;
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

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("ApplyShare [id=");
        builder.append(id);
        builder.append(", widgetId=");
        builder.append(widgetId);
        builder.append(", shareRange=");
        builder.append(shareRange);
        builder.append("]");
        return builder.toString();
    }
    
}
