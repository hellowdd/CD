package com.bocom.enums;

/*****
 * 类名称：ServiceErrorMessage
 * 类描述：服务出错类型
 * 创建人：donghongguang
 * 创建时间：2017年4月14日 下午2:57:47
 * 修改人：
 * 修改时间：
 * @version 1.0.0
 */
public enum ServiceErrorMessage
{
    LOGIN("01", "无"),
    
    NO_AUTHORITY("02", "无权");
    
    private ServiceErrorMessage(String statusCode, String statusName) {
        this.statusCode = statusCode;
        this.statusName = statusName;
    }

    /**状态编码*/
    private String statusCode;

    /**状态名称*/
    private String statusName;
    
    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
    
}
