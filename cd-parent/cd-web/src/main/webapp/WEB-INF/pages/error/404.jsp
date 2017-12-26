<%@page import="com.bocom.dto.session.UserRoleInfo"%>
<%@page import="java.util.List"%>
<%@page import="com.bocom.dto.session.SessionUserInfo"%>
<%@page import="org.springframework.util.CollectionUtils"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<title>你访问的地址不存在</title>
	<meta content="width=device-width, initial-scale=1.0" name="viewport" />
	<meta content="你访问的地址不存在" name="description" />
	<meta content="博康" name="author" />
	<style type="text/css">
		.img-container{
			position:absolute;
			left: 50%;
			top:50%;
			margin-left: -182px;
			margin-top: -300px;
			text-align: center;
			color: #555;
		}
		.img-container img{
			display: inline-block;
			margin: 0 0 20px 23px;
		}
	</style>
</head>
<body>
	<div class="img-container">
		<img src="${pageContext.request.contextPath}/img/img-404.png" /><br />
		404:你访问的地址不存在
	</div>
</body>
</html>