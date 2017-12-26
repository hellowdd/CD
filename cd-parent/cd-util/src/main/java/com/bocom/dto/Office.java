package com.bocom.dto;

import java.util.Date;
import java.util.List;

/**
 * 机构Entity
 * @author jinsiwei
 */
public class Office {

	private static final long serialVersionUID = 1L;
	private long oid;
	private Area area;		// 归属区域
	private String code; 	// 机构编码(目前弃用，采用id作为机构编码)
	private String type; 	// 机构类型（1：第三方公司；2：政府部门；）
	private String grade; 	// 机构等级（1：部级；2：省；3：地级市；4：区县 5： 派出所）
	private String address; // 联系地址
	private String zipCode; // 邮政编码
	private String master; 	// 负责人
	private String masterId; //负责人id
	private String phone; 	// 电话
	private String fax; 	// 传真
	private String email; 	// 邮箱
	private String useable;//是否可用
	/**
     * 实体编号（唯一标识）
     */
    protected String id;


	private Date startTime;
	private Date endTime;

	private String parentId;//上级机构

    public long getOid()
    {
        return oid;
    }

    public void setOid(long oid)
    {
        this.oid = oid;
    }

    public Area getArea()
    {
        return area;
    }

    public void setArea(Area area)
    {
        this.area = area;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getGrade()
    {
        return grade;
    }

    public void setGrade(String grade)
    {
        this.grade = grade;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getZipCode()
    {
        return zipCode;
    }

    public void setZipCode(String zipCode)
    {
        this.zipCode = zipCode;
    }

    public String getMaster()
    {
        return master;
    }

    public void setMaster(String master)
    {
        this.master = master;
    }

    public String getMasterId()
    {
        return masterId;
    }

    public void setMasterId(String masterId)
    {
        this.masterId = masterId;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getFax()
    {
        return fax;
    }

    public void setFax(String fax)
    {
        this.fax = fax;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getUseable()
    {
        return useable;
    }

    public void setUseable(String useable)
    {
        this.useable = useable;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public Date getStartTime()
    {
        return startTime;
    }

    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }

    public Date getEndTime()
    {
        return endTime;
    }

    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }

    public String getParentId()
    {
        return parentId;
    }

    public void setParentId(String parentId)
    {
        this.parentId = parentId;
    }

}