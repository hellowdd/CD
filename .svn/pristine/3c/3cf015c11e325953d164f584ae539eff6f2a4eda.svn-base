package com.bocom.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

public class PageUtil {
	private static Integer defaultPageSize = 20;
	private static Integer defaultPageNum = 1;

	public static void setParams(HttpServletRequest request,
			Map<String, Object> params) {
		String pageSize = request.getParameter("pageSize");
		String pageNum = request.getParameter("pageNum");
		params.put("pageSize", StringUtils.isEmpty(pageSize) ? defaultPageSize
				: Integer.valueOf(pageSize));
		params.put("pageNum", StringUtils.isEmpty(pageNum) ? defaultPageNum
				: Integer.valueOf(pageNum));
	}
}
