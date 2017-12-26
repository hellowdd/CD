<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%
	String id = request.getParameter("id")==null?"":request.getParameter("id");
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title></title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/sub.css" />
		<link rel="stylesheet" href="../css/bootstrap-select.min.css" />
		<style type="text/css">
			.txt-green{
				color:#4CAE4C;
			}
			.txt-red{
				color: #C9302C;
			}
			.form-horizontal .control-label {
			    padding-top: 7px;
			    margin-bottom: 0;
			    text-align: right;
			}
		</style>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js" ></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js" ></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap-select.min.js" ></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/form.js" ></script>
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
		<form style="margin: 20px;" id="myform">
			<div class="form-group" style="display:none;">
		    <label for="userName">控件ID</label>
		    <div class="input-group">
		      <input type="text" class="form-control" id="id" name="id" requried=true />
		    </div>
		  </div>
			<div class="form-group">
		    <label for="widget_name">控件名称</label>
	      <input type="text" class="form-control" id="widget_name" name="widget_name" placeholder="控件名称" disabled="true">
		    </div>
		  </div>
		  <div class="form-group">
		    <label for="widget_version">控件版本</label>
		    <input type="text" class="form-control" id="widget_version" name="widget_version" placeholder="控件版本" autocomplete="off" requried=true len="16">
		  </div>
		  <div class="form-group">
		    <label for="type">业务类别</label>
		    <input type="text" class="form-control" id="type" name="type" placeholder="业务类别" autocomplete="off" requried=true len="16">
		  </div>
		  <div class="form-group">
		    <label for="content">说明</label>
		    <textarea class="form-control" id="content" name="content" placeholder="说明" requried=true len="22" tip="必填，最多20个字符，一个汉字算2个字符"></textarea>
		  </div>
		  <div class="form-group">
		    <label for="upload_username">上传人</label>
		    <input type="text" class="form-control" id="upload_username" name="upload_username" placeholder="上传人" requried=true len="22" tip="必填，最多20个字符，一个汉字算2个字符" />
		  </div>
		  <div class="form-group">
		    <label for="upload_time">上传时间</label>
		    <input type="text" class="form-control" id="upload_time" name="upload_time" placeholder="上传时间" requried=true len="22" tip="必填，最多20个字符，一个汉字算2个字符" />
		  </div>
		  <div class="form-group">
		    <label>控件压缩包</label>
		    <p>当前使用 <b id="zipInfo"></b></p>
		  </div>
		  <button type="button" id="close" class="btn btn-default" style="float: right; margin: 20px 0;">关闭</button>
		</form>
	</body>
	<script type="text/javascript">
		$(document).on("ready",function(){
			var id = "<%=id%>";
			$.ajax({
        dataType: "json",
        //type: "POST",
        url: "../data/getWidget.json",
        data:{
        	id:id
        },
        cache: false,
        success: function(res) {
          if(res.success){
          	$("#id").val(res.data[0].id);
          	$("#widget_name").val(res.data[0].widget_name);
          	$("#widget_version").val(res.data[0].widget_version);
          	$("#content").val(res.data[0].content);
          	$("#zipInfo").html(res.data[0].fjName);
          }else{
          	window.parent.layer.alert("获取控件信息失败！"+res.msg);
          }
        },
        error: function() {
          window.parent.layer.alert("获取控件信息失败！");
        }
      });
			
			$("#close").on("click",function(){
				window.parent.layer.close(window.parent.winList.pop());
			});
		  
		});
	</script>
</html>
