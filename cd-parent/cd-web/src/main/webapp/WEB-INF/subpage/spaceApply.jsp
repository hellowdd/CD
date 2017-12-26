<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title></title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/sub.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-select.min.css" />
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
		<form class="form-horizontal" id="myForm" style="margin: 20px;">
		  <div class="form-group">
		    <label for="inputEmail3" class="col-xs-3 control-label">申请空间大小</label>
		    <div class="col-xs-6">
		      <input type="text" class="form-control" id="spaceSize" name="spaceSize" placeholder="申请空间大小" requried=true len="12" reg="num" tip="必填，必须是位数字，12位以内" />
		    </div>
		    <div class="col-xs-3">
		      <select id="spaceUnit" class="selectpicker" data-width="100%">
		      	<option value="GB">GB</option>
		      	<option value="MB">MB</option>
	      	</select>
		    </div>
		  </div>
		  <div class="form-group">
		    <label for="applyReason" class="col-xs-3 control-label">申请原因</label>
		    <div class="col-xs-9">
		      <textarea class="form-control" style="height: 120px;" id="applyReason" name="applyReason" placeholder="申请原因" len="512" tip="最多512个字符，一个汉字算2个字符"></textarea>
		    </div>
		  </div>
		  <div class="form-group" style="display: none;">
		    <label for="type" class="col-xs-3 control-label">申请类型</label>
		    <div class="col-xs-7">
		      <input type="text" class="form-control" id="type" name="type" placeholder="申请类型" />
		    </div>
		  </div>
		  <div class="form-group">
		    <div class="col-xs-offset-2 col-xs-10" style="text-align: right;">
		      <button type="button" id="submit" class="btn btn-default">提交申请</button>
		    </div>
		  </div>
		</form>
	</body>
	<script type="text/javascript">
		$(document).on("ready",function(){
			
			autoCheckForm("myForm");
			
		  $("#submit").on("click",function(e){
			  
			  checkForm("myForm");
			  
			  if(!canSubmit){
					return;
				}
			  
			  $.ajax({
			    dataType: "json",
			    type: "POST",
			    url: "${pageContext.request.contextPath}/manager/space/applySpace",
			    data:{
			    	spaceSize:$("#spaceSize").val(),
			    	spaceUnit:$("#spaceUnit").val(),
			    	applyReason:$("#applyReason").val()
			    },
			    cache: false,
			    success: function(res) {
			      if(res.success){
			    	  window.parent.layer.alert("申请空间成功",function(i){
			    		  window.parent.layer.close(i);
			    		  window.parent.layer.close(window.parent.winList.pop());
			    	  });
			      }else{
			      	window.parent.layer.alert("申请空间失败！"+res.message);
			      }
			    },
			    error: function() {
			      window.parent.layer.alert("申请空间失败！");
			    }
			  });
		  });
		  
		});
	</script>
</html>
