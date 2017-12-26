package com.bocom.domain;

/**
 * @version 1.0
 * @since 1.0
 */
public class SpaceManage extends BaseBean {

	/** id */
	private java.lang.Integer id;
	/** 用户类型（个人、组织） */
	private java.lang.String userType;
	/** 用户id */
	private java.lang.Integer userId;
	/** 空间总大小（B单位） */
	private java.lang.Long spaceTotal;
	/** 已用空间（B单位） */
	private java.lang.Long spaceUse;
	/** 剩余空间（B单位） */
	private java.lang.Long spaceRest;

	/** 获取id */
	public java.lang.Integer getId() {
		return this.id;
	}

	/** 设置id */
	public void setId(java.lang.Integer value) {
		this.id = value;
	}

	/** 获取用户类型（个人、组织） */
	public java.lang.String getUserType() {
		return this.userType;
	}

	/** 设置用户类型（个人、组织） */
	public void setUserType(java.lang.String value) {
		this.userType = value;
	}

	/** 获取用户id */
	public java.lang.Integer getUserId() {
		return this.userId;
	}

	/** 设置用户id */
	public void setUserId(java.lang.Integer value) {
		this.userId = value;
	}

	/** 获取空间总大小（B单位） */
	public java.lang.Long getSpaceTotal() {
		return this.spaceTotal;
	}

	/** 设置空间总大小（B单位） */
	public void setSpaceTotal(java.lang.Long value) {
		this.spaceTotal = value;
	}

	/** 获取已用空间（B单位） */
	public java.lang.Long getSpaceUse() {
		return this.spaceUse;
	}

	/** 设置已用空间（B单位） */
	public void setSpaceUse(java.lang.Long value) {
		this.spaceUse = value;
	}

	/** 获取剩余空间（B单位） */
	public java.lang.Long getSpaceRest() {
		return this.spaceRest;
	}

	/** 设置剩余空间（B单位） */
	public void setSpaceRest(java.lang.Long value) {
		this.spaceRest = value;
	}

	@Override
	public String toString() {
		return "SapceMange [id=" + id + ", userType=" + userType + ", userId="
				+ userId + ", spaceTotal=" + spaceTotal + ", spaceUse="
				+ spaceUse + ", spaceRest=" + spaceRest + "]";
	}
	

}
