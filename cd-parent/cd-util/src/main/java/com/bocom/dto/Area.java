package com.bocom.dto;


/**
 * 区域Entity
 */
public class Area {

	private static final long serialVersionUID = 1L;
//	private Area parent;	// 父级编号
//	private String parentIds; // 所有父级编号
	private String code; 	// 区域编码
//	private String name; 	// 区域名称
	private Integer sort;		// 排序
	private String type; 	// 区域类型（1：国家；2：省份、直辖市；3：地市；4：区县）
    public String getCode()
    {
        return code;
    }
    public void setCode(String code)
    {
        this.code = code;
    }
    public Integer getSort()
    {
        return sort;
    }
    public void setSort(Integer sort)
    {
        this.sort = sort;
    }
    public String getType()
    {
        return type;
    }
    public void setType(String type)
    {
        this.type = type;
    }
	

}