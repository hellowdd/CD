<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

  <head>
    <meta charset="UTF-8">
    <title></title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
		<style type="text/css">
			.main{
				padding:20px;
			}
			.txt-red{
				color: red;
			}
		</style>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/form.js"></script>
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
		<form id="widgetForm" class="main">
			<div class="form-group">
				<label for="TypetName">类型名称 <b class="txt-red">*</b></label>
				<input type="text" class="form-control" id="TypetName" name="TypetName" placeholder="类型名称" placeholder="必填，最多32个字符，一个汉字算2个字符" requried=true len="32" tip="必填，最多32个字符，一个汉字算2个字符" />
			</div>
			<div class="form-group">
				<label for="intro">说明</label>
				<textarea type="text" class="form-control" id="intro" name="intro" placeholder="类型名称说明" placeholder="最多200个字符，一个汉字算2个字符" len="200" tip="最多200个字符，一个汉字算2个字符"></textarea>
			</div>
			<button type="button" id="submit" class="btn btn-default" style="float: right;">确定</button>
		</form>
  </body>
  <script type="text/javascript" id="mainJs">		
  	$(document).on("ready",function(){
  		var record = window.parent.frames["mainFrame"].recordSeled;
			
			$("#TypetName").val(record.widgetTypeName);
			var intro = $.trim(record.widgetTypeContent)?record.widgetTypeContent:"";
			$("#intro").val(intro);
			
  		autoCheckForm("widgetForm");
			
			$("#submit").on("click",function(){
				checkForm("widgetForm");
				if(!canSubmit){
					return;
				}
				var paramsObj = {
					id:record.id,
					widgetTypeName:$("#TypetName").val()
				};
				if($.trim($("#intro").val())){
					paramsObj.widgetTypeContent = $("#intro").val();
				}
				$.ajax({
					dataType: "json",
					//type: "POST",
					url: "${pageContext.request.contextPath}/manager/widgetType/updateWidgetTypeInfo",
					data:paramsObj,
					cache: false,
					success: function(res) {
						if(res.success){
							window.parent.layer.alert("修改控件类型成功！",function(i){
								window.parent.layer.close(i);
								window.parent.frames["mainFrame"].query();
								window.parent.layer.close(window.parent.winList.pop());
							});
						}else{
							window.parent.layer.alert("修改控件类型失败！"+res.message);
						}
					},
					error: function() {
						window.parent.layer.alert("修改控件类型失败！");
					}
				});
			});
  	});
  </script>
</html>