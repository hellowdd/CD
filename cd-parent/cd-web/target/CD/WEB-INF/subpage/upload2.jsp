<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%
	String type = request.getParameter("type")==null?"":request.getParameter("type");
	String ip = request.getParameter("ip")==null?"":request.getParameter("ip");
%>
<!DOCTYPE html>
<html>

  <head>
    <meta charset="UTF-8">
    <title></title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-select.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/js/zTree_v3-master/css/zTreeStyle/zTreeStyle.css" type="text/css">
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
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap-select.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-form.js" ></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/zTree_v3-master/js/jquery.ztree.core.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/ajaxfileupload.js"></script>
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
	 <%-- <div class="form-group">
	    <label for="widgetName">控件名称 <b class="txt-red">*</b></label>
	    <input type="text" class="form-control" id="widgetName" name="widgetName" placeholder="控件名称" placeholder="必填，最多255个字符，一个汉字算2个字符" requried=true len="255" tip="必填，最多255个字符，一个汉字算2个字符" />
	  </div>
		<div class="form-group">
	    <label for="widgetVersion">控件类别 <b class="txt-red">*</b></label>
	    <select id="widgetType" class="selectpicker" data-width="100%"></select>
	  </div>
	  <div class="form-group">
	    <label for="widgetVersion">端类型 <b class="txt-red">*</b></label>
	    <select id="appType" class="selectpicker" data-width="100%">
	    	<option value="0">桌面端</option>
	    	<option value="1">大屏端</option>
	    	<option value="2">移动端</option>
	    </select>
	  </div>
	  <div class="form-group">
	    <label for="widgetVersion">控件版本 <b class="txt-red">*</b></label>
	    <input type="text" class="form-control" id="widgetVersion" name="widgetVersion" placeholder="控件版本" placeholder="必填，最多32位，只能包含数字和." requried=true len="32" reg="version" tip="必填，最多32位，只能包含数字和." />
	  </div>--%>

	 <%-- <div class="form-group">
	    <label for="widgetVersion">说明</label>
	    <textarea class="form-control" id="content" name="content" name="content" placeholder="说明" len="1024" tip="最多1024个字符，一个汉字算2个字符."></textarea>
	  </div>--%>
	  <div class="form-group">
	    <label for="exampleInputFile">上传文件:<b class="txt-red">*</b></label>
	    <input type="file" id="inputfile" name="inputfile">
	  </div>
	  <button type="button" id="upload" class="btn btn-default" style="float: right;">上传</button>
	</form>
	<iframe style="display:none" id="callback" src=""></iframe>
  </body>
  <script type="text/javascript" id="mainJs">

		
  	$(document).on("ready",function(){
  		var type = "<%=type%>";
  		var ip = "<%=ip%>";
			

  		
  		autoCheckForm("widgetForm");
  		


			
  		$("#upload").on("click",function(){

				if(!$("#inputfile").val()){
					window.parent.layer.alert("请选择要上传的文件！");
					return;
				}
  			var date = new Date();
  			var formData = new FormData();

			var id = window.location.href.split("?")[1];
  			formData.append('content', "");
  			formData.append('inputfile', $('#inputfile')[0].files[0]);
  			formData.append('widgetName',"");
  			formData.append('directoryId',id);
  			$.ajax({ 
	  			type: "POST", 
	  			url: "${pageContext.request.contextPath}/manager/widget/uploadWidget",
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

                        window.parent.layer.alert("上传成功",function(i){
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







                        /*if(type&&ip){
                            $("#callback").attr("src",ip+"/pac-web/widgetFrame/callBack.jsp?widgetName="+encodeURI(encodeURI(widgetName))+"&widgetVersion="+version+"&widgetId="+res.data+"&type="+type+"_="+date.getTime());
                        }else{

                        }*/
		  			}else{
		  				try{
		  					window.parent.layer.alert(res.message);
		  				}catch(e){
		  					layer.alert("上传失败");
		  				}
		  			}
	  			}, 
	  			error: function (data, status, e) {
	  				try{
	  					window.parent.layer.alert("上传失败");
	  				}catch(e){
	  					layer.alert("上传失败");
	  				}
	  			} 
  			});

  		});
  	});

  </script>
</html>