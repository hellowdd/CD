package com.bocom.domain;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;

/*****
 * <pre>
 * 类名称：BaseBean
 * 类描述：基础bean，其它bean必须继承该bean
 * 创建人：donghongguang
 * 创建时间：2017年3月23日 下午2:07:52
 * 修改人：
 * 修改时间：
 * </pre>
 * @version 1.0.0
 */
public class BaseBean
{
    
    private String            pageNo           = "1";
    
    private String            pageSize         = "10";
    
    private String            locale;
    
    /*****
     * @version 1.0.0
     * @return locale
     */
    public Locale getLocale()
    {
        if (StringUtils.isBlank(locale))
        {
            locale = "zh_CN";
        }
        locale = locale.trim();
        Locale getLocale = org.springframework.util.StringUtils.parseLocaleString(locale);
        return getLocale;
    }
    
    /*****
     * @param locale
     * @version 1.0.0
     */
    public void setLocale(String locale)
    {
        this.locale = locale;
    }
    
    public String getPageNo()
    {
        return pageNo;
    }
    
    public void setPageNo(String pageNo)
    {
        this.pageNo = pageNo;
    }
    
    public String getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(String pageSize)
    {
        this.pageSize = pageSize;
    }
    
    
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("BaseBean [pageNo=");
        builder.append(pageNo);
        builder.append(", pageSize=");
        builder.append(pageSize);
        builder.append("]");
        return builder.toString();
    }
}