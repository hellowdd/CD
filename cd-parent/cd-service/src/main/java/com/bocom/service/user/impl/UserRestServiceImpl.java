package com.bocom.service.user.impl;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bocom.dto.Office;
import com.bocom.dto.UserOrgRespDto;
import com.bocom.dto.api.UserInfoPAPDto;
import com.bocom.dto.session.OrgRoleInfo;
import com.bocom.dto.session.ResponseDto;
import com.bocom.dto.session.SessionUserInfo;
import com.bocom.dto.session.SessionUserInfoDto;
import com.bocom.dto.session.SessionUserRespDto;
import com.bocom.dto.session.UserRoleInfo;
import com.bocom.service.user.UserRestService;
import com.bocom.util.HttpClientUtil;

@Service
public class UserRestServiceImpl implements UserRestService {

	private static ObjectMapper objectMapper = new ObjectMapper();
	private static Logger LOG = LoggerFactory
			.getLogger(UserRestServiceImpl.class);

	@Value("${rest.user.getUserListByOrgId.url}")
	private String getUserListByOrgIdUrl;

	@Value("${rest.user.getUserInfoFromPAP.url}")
	private String getUserInfoFromPAPUrl;
	@Value("${rest.office.getOfficeFromPAP.url}")
	private String getOfficeFromPAP;
	@Value("${rest.org.queryOrgByUsername.url}")
    private String getOrgByUsernameUrl;

	private SessionUserInfo createBean(SessionUserInfoDto dto) {
		SessionUserInfo data = new SessionUserInfo();
		if (dto != null) {
			data.setUserId(dto.getUserId());
			data.setUserName(dto.getUserName());
			data.setPoliceCode(dto.getPoliceCode());
			data.setPoliceName(dto.getPoliceName());
			data.setUserType(dto.getUserType());
			List<OrgRoleInfo> roleUserInfoList = dto.getOrgRoleUserInfoList();
			List<UserRoleInfo> userRoles = new ArrayList<UserRoleInfo>();
			if (roleUserInfoList != null && roleUserInfoList.size() > 0) {
				OrgRoleInfo info = roleUserInfoList.get(0);
				UserRoleInfo userRole = new UserRoleInfo();
				data.setRoleOrgCode(info.getRoleOrgCode());
				data.setRoleOrgName(info.getRoleOrgName());
				data.setOrgCode(info.getRoleOrgCode());
				data.setOrgName(info.getRoleOrgName());
				data.setParentOrgCode(info.getParentOrgCode());
				data.setParentOrgName(info.getParentOrgName());
				userRole.setRoleCode(info.getRoleCode());
				userRole.setRoleName(info.getRoleName());
				userRoles.add(userRole);
				data.setUserRoles(userRoles);
			}
		}
		return data;
	}

	public static Object getResponseCommonData(String url) throws Exception {
		String data = HttpClientUtil.getBase64(url);
		LOG.info("response data: " + data);
		ResponseDto responseDto = objectMapper.readValue(data,
				ResponseDto.class);
		if (responseDto != null && responseDto.isSuccess()) {
			return responseDto.getData();
		}
		return null;
	}

	@Override
	public SessionUserInfo getUserInfoFromPAP(UserInfoPAPDto paramDto) {
		SessionUserInfo sessionUserInfo = null;
		try {
			String json = objectMapper.writeValueAsString(paramDto);
			 String data = HttpClientUtil.postBase64(getUserInfoFromPAPUrl, json);
			//String data = "{"+"\"data\":{\"orgRoleUserInfoList\":[{\"parentOrgCode\":\"340223000000\",\"parentOrgName\":\"\",\"roleCode\":\"2\",\"roleName\":\"超管\",\"roleOrgCode\":\"340223400000\",\"roleOrgName\":\"安徽省南陵县公安局城西派出所\"}],\"policeCode\":\"wangbin\",\"policeName\":\"wangbin\",\"userId\":27,\"userName\":\"wangbin\"},\"message\":\"接口调用成功\",\"success\":"+true+"}";
			LOG.info("response data: " + data);
			objectMapper.configure(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			SessionUserRespDto responseDto = objectMapper.readValue(data,
					SessionUserRespDto.class);
			sessionUserInfo = createBean(responseDto.getData());
		} catch (Exception e) {
			LOG.error("UserRestServiceImpl getUserInfoFromPAP error", e);
		}
		return sessionUserInfo;
	}
	
	@Override
    public String getOfficeFromPAP(){
//        Office office = null;
	    String data = "";
        try {
//            String json = objectMapper.writeValueAsString(null);
            data = HttpClientUtil
                    .getBase64(getOfficeFromPAP);
            LOG.info("response data: " + data);
//            objectMapper.configure(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//            UserOrgRespDto responseDto = objectMapper.readValue(data,
//                    UserOrgRespDto.class);
//            office = responseDto.getData();
        } catch (Exception e) {
            LOG.error("UserRestServiceImpl getOrgByUsernameUrl error", e);
        }
        return data;
    }
	@Override
    public Office getOrgInfoFromPAP(SessionUserInfoDto dto){
        Office office = null;
        try {
            String json = objectMapper.writeValueAsString(dto);
            String data = HttpClientUtil
                    .postBase64(getOrgByUsernameUrl, json);
            LOG.info("response data: " + data);
            objectMapper.configure(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            UserOrgRespDto responseDto = objectMapper.readValue(data,
                    UserOrgRespDto.class);
            office = responseDto.getData();
        } catch (Exception e) {
            LOG.error("UserRestServiceImpl getOrgByUsernameUrl error", e);
        }
        return office;
    }
}
