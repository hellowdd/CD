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
			<%--<div class="form-group">
		    <label for="widget_name">控件名称</label>
	      <input type="text" class="form-control" id="widget_name" name="widget_name" placeholder="必填，最多255个字符，一个汉字算2个字符" requried=true len="255" tip="必填，最多255个字符，一个汉字算2个字符" />
	    </div>--%>
			<!--div class="form-group">
				<label for="widgetVersion">控件类别 <b class="txt-red">*</b></label>
				<select id="widgetType" class="selectpicker" data-width="100%"></select>
			</div-->
	<%--	  <div class="form-group">
		    <label for="widget_version">控件版本</label>
		    <input type="text" class="form-control" id="widget_version" name="widget_version" placeholder="必填，最多32位，只能包含数字和." requried=true len="32" reg="version" tip="必填，最多32位，只能包含数字和." />
		  </div>
		  <div class="form-group">
		    <label for="remarks">说明</label>
		    <textarea class="form-control" id="remarks" name="remarks" placeholder="最多1024个字符，一个汉字算2个字符." len="1024" tip="最多1024个字符，一个汉字算2个字符."></textarea>
		  </div>--%>
		  <div class="form-group">
		    <label for="inputfile">上传文件:</label>
		    <input type="file" class="form-control" id="inputfile" name="inputfile" />
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
			//获取控件类别
			// $.ajax({
	      // dataType: "json",
	      // url: "${pageContext.request.contextPath}/manager/widgetType/getWidgetType",
	      // cache: false,
	      // success: function(res) {
	        // if(res.success){
						// var innerStr = "";
						// for(var i=0;i<res.data.length;i++){
							// innerStr += '<option value="'+res.data[i].id+'">'+res.data[i].widgetTypeName+'</option>';
						// }
						// $("#widgetType").html(innerStr);
						// $("#widgetType").selectpicker('refresh');
						// $('#widgetType').selectpicker('val', record.widget_type_id);
	        // }else{
	        	// window.parent.layer.alert(res.message);
	        // }
	      // },
	      // error: function() {
	        // window.parent.layer.alert("获取目录失败！");
	      // }
	    // });
			
			$("#submit").on("click",function(){
				//还要比较版本，版本不能变小
				if(!$("#inputfile").val()){
					window.parent.layer.alert("请选择要上传的文件！");
					return;
				}
				var formData = new FormData(); 
				// formData.append('type',$("#widgetType").selectpicker('val'));
  			formData.append('content', '');
  			formData.append('widgetId', record.id);
  			formData.append('inputfile', $('#inputfile')[0].files[0]);
  			$.ajax({ 
	  			type: "POST", 
	  			url: "${pageContext.request.contextPath}/manager/widget/updateWidget", 
	  			data: formData, 
	  			processData : false, 
	  			contentType : false , 
	  			/* xhr: function(){ 
		  			var xhr = $.ajaxSettings.xhr(); 
		  			if(onprogress && xhr.upload) { 
			  			xhr.upload.addEventListener("progress" , onprogress, false); 
			  			return xhr; 
		  			} 
	  			},  */
	  			success:function (res) { 
		  			if(res.success){
		  					window.parent.layer.alert("升级成功",function(i){
		  						window.parent.layer.close(i);
                                var parentNodeId = window.parent.frames["mainFrame"].seledRoute[window.parent.frames["mainFrame"].seledRoute.length-1];
                                if(parentNodeId == 0){
                                    window.parent.frames["mainFrame"].query({
                                        directoryId:""
                                    });
                                }else{
                                    window.parent.frames["mainFrame"].query({
                                        directoryId:parentNodeId,
                                        directoryName:window.parent.frames["mainFrame"].seledRouteTxt[window.parent.frames["mainFrame"].seledRouteTxt.length-1],
                                    });
                                }
                                window.parent.layer.close(window.parent.winList.pop());
		  					}); 
		  			} else{
		  				window.parent.layer.alert(res.message); 
		  			}
	  			}, 
	  			error: function (data, status, e) {
	  				window.parent.layer.alert("升级失败"); 
	  			} 
  			});
			});
		  
		});
		
		//比较版本号大小，1.1.0这种情况看成和1.1一样
		function compareVersion(oldVersion,newVersion){
			var olds = oldVersion.split(".");
			var news = newVersion.split(".");
			var flag = true;
			if(olds.length>news.length){
				var isEqual = 0;
				for(var i=0;i<news.length;i++){
					if(parseInt(olds[i])>parseInt(news[i])){
						flag = false;
						break;
					}else if(parseInt(olds[i])<parseInt(news[i])){
						break;
					}else{
						isEqual++;
					}
				}
				
				if(isEqual == news.length){
					//这里旧的版本号必然是>=0的
					flag = false;
				}
			}else if(olds.length<news.length){
				for(var i=0;i<olds.length;i++){
					if(parseInt(olds[i])>parseInt(news[i])){
						flag = false;
						break;
					}else if(parseInt(olds[i])<parseInt(news[i])){
						break;
					}
				}
				//这里新的版本号必须是==0，才会出现版本号相同的情况
				if(flag){
					for(var j=olds.length;j<news.length;j++){
						if(parseInt(news[j])==0){
							flag = false;
							break;
						}
					}
				}
			}else{
				//这里需要判断是否完全相等的情况
				var isEqual = 0;
				for(var i=0;i<olds.length;i++){
					if(parseInt(olds[i])>parseInt(news[i])){
						flag = false;
						break;
					}else if(parseInt(olds[i])==parseInt(news[i])){
						isEqual++;
					}else{
						break;
					}
				}
				//完全相等
				if(isEqual == olds.length){
					flag = false;
				}
			}
			return flag;
		}
	</script>
</html>
