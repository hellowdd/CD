package com.bocom.service.user;

import com.bocom.dto.Office;
import com.bocom.dto.api.UserInfoPAPDto;
import com.bocom.dto.session.SessionUserInfo;
import com.bocom.dto.session.SessionUserInfoDto;

public interface UserRestService {
	
	/**
	 * 从pap获取用户信息
	 * @return
	 */
	public SessionUserInfo getUserInfoFromPAP(UserInfoPAPDto dto);
	public String getOfficeFromPAP();
	public Office getOrgInfoFromPAP(SessionUserInfoDto dto);
	
}
