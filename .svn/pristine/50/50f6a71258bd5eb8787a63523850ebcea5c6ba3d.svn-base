<%@page import="com.bocom.dto.session.UserRoleInfo"%>
<%@page import="java.util.List"%>
<%@ page import="com.bocom.dto.session.SessionUserInfo"%>
<%@page import="org.springframework.util.CollectionUtils"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
	SessionUserInfo sessionObj=(SessionUserInfo)session.getAttribute("sessionUserInfo");

	String userType = sessionObj.getUserType();
%>
<!DOCTYPE html>
<html>

  <head>
    <meta charset="UTF-8">
    <title>云盘管理</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css" />
		<link rel="shortcut icon" href="${pageContext.request.contextPath}/img/logo.ico" />
		
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
		<!--[if lt IE 9]>
			<style type="text/css">
				.layui-layer{
					border: 1px solid #ccc;
				}
			</style>
	      	<script src="${pageContext.request.contextPath}/js/html5shiv.min.js"></script>
	      	<script src="${pageContext.request.contextPath}/js/respond.min.js"></script>
		<![endif]-->
  </head>

  <body>
	<div class="header">
		<div class="logo"></div>
		<div class="user-info">
			<div class="user-txt">
				<span class="user-mame txt-white">${sessionUserInfo.userName }</span><br />
				<span class="user-rol txt-white" id="user">${sessionUserInfo.orgName }</span>&nbsp;<a href="javascript:void(0)" id="btnDrop" class="no-decoration link-white"><i class="glyphicon glyphicon-chevron-down"></i></a>
				
				<ul class="dropdown-menu" style="right: 10px; width: 200px; left: auto;" id="dropdownMenu">
					<li><a href="javascript:void(0);" onclick="loginCasOut()"><i class="icon-key"></i>注销</a></li>
				</ul>
			</div>
			<div class="user-avatar"></div>
		</div>
	</div>
	<div class="sidebar main-nav" id="sidebar">
		<ul>
			<li id="directoryManage" style="display: none" class="active"><a href="#directoryManage">目录管理</a></li>
			<li id="widgetList" style="display: none" ><a href="#widgetList">文件列表</a></li>

			<li id="count" style="display: none"><a href="#count">统计</a></li>
			<li id="logList" style="display: none"><a href="#logList">日志列表</a></li>



			<!--<li><a href="#spaceApplyList">空间申请列表</a></li>-->
			<%--<li id="typeManage"><a href="#typeManage">控件类型管理</a></li>--%>

		</ul>
	</div>
	<div class="main">
		<iframe id="mainFrame" name="mainFrame" frameborder="0" src=""></iframe>
	</div>
	<div class="cover" id="cover"></div>
  </body>
  <script type="text/javascript">
  	var winList = [];
    var roleCode = "${sessionUserInfo.userRoles[0].roleCode }";
    if(roleCode==1){
        $("#widgetList,#directoryManage").show()

	}else{
        $("#widgetList,#directoryManage,#count,#logList").show()
	}
		function loginCasOut(){
			$.ajax({  
				async : false, 
				cache:false,
				contentType: "application/json" ,
				type: 'POST',  
				dataType : "json",  
				url: "${pageContext.request.contextPath}"+"/manager/loginAction/loginCasOut",  
				error: function () {
					alert('请求失败');  
				},  
				success:function(res){ //请求成功后处理函数。    
					if(res.success){
						window.location.href="${casServerUrlPrefix}/logout?service=<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()%>/manager/loginAction/loginCas"; 
					}else{
						layer.alert('退出失败！'); 
					}
				}  
			});
		}
		
  	$(document).on("ready",function(){
  		var activeItem = $("#sidebar").find(".active");

			var linkObj = {
				"widgetList":"${pageContext.request.contextPath}/manager/view/widgetList",
				"shareList":"${pageContext.request.contextPath}/manager/view/shareList",
	//			"spaceUseInfo":"${pageContext.request.contextPath}/manager/view/spaceUseInfo",
				"count":"${pageContext.request.contextPath}/manager/view/count",
				"logList":"${pageContext.request.contextPath}/manager/view/logList",
                "directoryManage":"${pageContext.request.contextPath}/manager/view/directoryManage",
                "typeManage":"${pageContext.request.contextPath}/manager/view/typeManage"
			};

			$("#sidebar").find("a").on("click",function(){
				if($(this).parent().hasClass("active")){
					return;
				}
				activeItem.removeClass("active");
				$(this).addClass("active")
			});
			
			window.onload = function(){
				var subpage = window.location.href.split("#");
				if(subpage.length>1){
					$("#mainFrame").attr("src",linkObj[subpage[1]]);
					if(!$("#"+subpage[1]).hasClass("active")){
						$("#sidebar").find(".active").removeClass("active");
						$("#"+subpage[1]).addClass("active");
					}
				}else{
                    $("#mainFrame").attr("src",linkObj["directoryManage"]);
			}
            }

			
			window.onhashchange = function(){
				var subpage = window.location.href.split("#")[1];
				$("#mainFrame").attr("src",linkObj[subpage]);
				if(!$("#"+subpage).hasClass("active")){
					$("#sidebar").find(".active").removeClass("active");
					$("#"+subpage).addClass("active");
				}
			}
			
			$("#btnDrop").on("click",function(){
	    	$("#dropdownMenu").show();
	    	$("#cover").show();
	    });
	    
	    $("#cover").on("click",function(){
	    	$("#dropdownMenu").hide();
	    	$("#cover").hide();
	    });
  	});
  </script>
</html>