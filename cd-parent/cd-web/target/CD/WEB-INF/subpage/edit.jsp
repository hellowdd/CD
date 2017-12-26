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
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/ajaxfileupload.js"></script>
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
			<!-- <div class="form-group" style="display:none;">
		    <label for="userName">控件ID</label>
	      <input type="text" class="form-control" id="id" name="id" requried=true disabled="true" />
		  </div> -->
			<div class="form-group">
		    <label for="widget_name">控件名称</label>
	      <input type="text" class="form-control" id="widget_name" name="widget_name" placeholder="必填，最多255个字符，一个汉字算2个字符" requried=true len="255" tip="必填，最多255个字符，一个汉字算2个字符" />
	    </div>
			<div class="form-group">
				<label for="widgetVersion">控件类别 <b class="txt-red">*</b></label>
				<select id="widgetType" class="selectpicker" data-width="100%"></select>
			</div>
		  <div class="form-group">
		    <label for="remarks">说明</label>
		    <textarea class="form-control" id="remarks" name="remarks" placeholder="最多1024个字符，一个汉字算2个字符." len="1024" tip="最多1024个字符，一个汉字算2个字符."></textarea>
		  </div>
		  <button type="button" id="submit" class="btn btn-default" style="float: right; margin: 20px 0;">提交</button>
		</form>
	</body>
	<script type="text/javascript">
		$(document).on("ready",function(){			
			autoCheckForm("myform")
			
			var record = window.parent.frames["mainFrame"].recordSeled;
			var remark = record.remarks == "undefined"?"":record.remarks;
			$("#id").val(record.id);
     	$("#widget_name").val(record.widget_name_show);
     	$("#remarks").val(remark);
			
			//获取控件类别
			$.ajax({
	      dataType: "json",
	      //type: "POST",
	      url: "${pageContext.request.contextPath}/manager/widgetType/getWidgetType",
	      cache: false,
	      success: function(res) {
	        if(res.success){
						var innerStr = "";
						for(var i=0;i<res.data.length;i++){
							innerStr += '<option value="'+res.data[i].id+'">'+res.data[i].widgetTypeName+'</option>';
						}
						$("#widgetType").html(innerStr);
						$("#widgetType").selectpicker('refresh');
						$('#widgetType').selectpicker('val', record.widget_type_id);
	        }else{
	        	window.parent.layer.alert(res.message);
	        }
	      },
	      error: function() {
	        window.parent.layer.alert("获取目录失败！");
	      }
	    });
			
			$("#submit").on("click",function(){
				checkForm("myform");
				if(!canSubmit){
					return;
				}
  			$.ajax({ 
	  			type: "POST", 
	  			url: "${pageContext.request.contextPath}/manager/widget/updateWidgetInfoBase", 
	  			data: {
						widgetId:record.id,
						directoryId:record.directory_id,
						widgetNameShow:$("#widget_name").val(),
						widgetTypeId:$('#widgetType').selectpicker('val'),
						remarks:$.trim($("#remarks").val())
						
					}, 
	  			success:function (res) { 
		  			if(res.success){
		  					window.parent.layer.alert("修改成功",function(i){
		  						window.parent.layer.close(i);
		  						window.parent.layer.close(window.parent.winList.pop());
		  						window.parent.frames["mainFrame"].query(window.parent.frames["mainFrame"].pageNum);
		  					}); 
		  			} 
	  			}, 
	  			error: function (data, status, e) {
	  				window.parent.layer.alert("修改失败"); 
	  			} 
  			});
			});
		  
		});
	</script>
</html>
