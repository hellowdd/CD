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
	  	<link rel="stylesheet" href="${pageContext.request.contextPath}/js/webuploader/webuploader.css" type="text/css">

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
	  	<script type="text/javascript" src="${pageContext.request.contextPath}/js/webuploader/webuploader.min.js"></script>

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
	  <div class="form-group">
	    <label for="widgetVersion">选择目录 (根目录不可选)<b class="txt-red">*</b></label>
	    <ul id="tree" class="ztree" style="height:120px; border:1px solid #ccc; background-color:#fafafa; overflow:auto;"></ul>
	  </div>
	 <%-- <div class="form-group">
	    <label for="widgetVersion">说明</label>
	    <textarea class="form-control" id="content" name="content" name="content" placeholder="说明" len="1024" tip="最多1024个字符，一个汉字算2个字符."></textarea>
	  </div>--%>
	  <div class="form-group" id="fileInfo">
	    <label for="inputfile"><b class="txt-red">*</b>上传文件:</label>
	   <%-- <input type="file" id="inputfile" name="inputfile">--%>
		  <div id="picker">选择文件</div>


	  </div>
	  <button type="button" id="ctlBtn" class="btn btn-default" style="float: right;">上传</button>
	</form>
	<iframe style="display:none" id="callback" src=""></iframe>
  </body>
  <script type="text/javascript" id="mainJs">
      var node;
  	var setting = {
			callback:{
				beforeExpand: function(treeId, treeNode) {
					query({
						directoryId:treeNode.id,
						directoryName:treeNode.name
					},treeNode);
			    return true;
				},
				beforeClick: function(treeId, treeNode, clickFlag) {
				    return (treeNode.id !== "");
				}
			}
		};
		
		var treeObj;
		
  	$(document).on("ready",function(){
  	    var name;
  		var type = "<%=type%>";
  		var ip = "<%=ip%>";
			
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
	        }else{
	        	window.parent.layer.alert(res.message);
	        }
	      },
	      error: function() {
	        window.parent.layer.alert("获取目录失败！");
	      }
	    });
  		
  		autoCheckForm("widgetForm");
  		
  		//创建树
  		//初始根节点不需要查询
			var zNodes =[{
				id:"",
   			name:"根目录",
   			isParent:true
			}];
			treeObj = $.fn.zTree.init($("#tree"), setting, zNodes);
			
  		/*$("#upload").on("click",function(){
  			checkForm("widgetForm");
				if(!canSubmit){
					return;
				}
				if(!$("#inputfile").val()){
					window.parent.layer.alert("请选择要上传的文件！");
					return;
				}
  			var widgetName = $("#widgetName").val();
  			var version = $("#widgetVersion").val();
  			var date = new Date();
  			var formData = new FormData(); 
  			var node = treeObj.getSelectedNodes()[0];
				if(!node){
					window.parent.layer.alert("请选择目录！");
					return;
				}
  			formData.append('content', "");
  			formData.append('inputfile', $('#inputfile')[0].files[0]);
  			formData.append('widgetName',"");
  			formData.append('directoryId',node.id);
  			$.ajax({ 
	  			type: "POST", 
	  			url: "${pageContext.request.contextPath}/manager/widget/uploadWidget",
	  			data: formData, 
	  			processData : false, 
	  			contentType : false , 
	  			/!* xhr: function(){
		  			var xhr = $.ajaxSettings.xhr(); 
		  			if(onprogress && xhr.upload) { 
			  			xhr.upload.addEventListener("progress" , onprogress, false); 
			  			return xhr; 
		  			} 
	  			},  *!/
	  			success:function (res) {
	  			    console.log(res)
		  			if(res.success){
                        window.parent.layer.alert("上传成功",function(i){
                            window.parent.layer.close(i);
                            window.parent.frames["mainFrame"].query(window.parent.frames["mainFrame"].pageNum);
                            window.parent.layer.close(window.parent.winList.pop());
                        });
                        /!*if(type&&ip){
                            $("#callback").attr("src",ip+"/pac-web/widgetFrame/callBack.jsp?widgetName="+encodeURI(encodeURI(widgetName))+"&widgetVersion="+version+"&widgetId="+res.data+"&type="+type+"_="+date.getTime());
                        }else{

                        }*!/
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

  		});*/
//		断点续传

        var fileMd5;
        //监听分块上传过程中的三个时间点
        WebUploader.Uploader.register({
            "before-send-file": "beforeSendFile",
            "before-send": "beforeSend",
            "after-send-file": "afterSendFile",
        }, {
            //时间点1：所有分块进行上传之前调用此函数
            beforeSendFile: function (file) {
                console.log(file);
                name=file.name;
                var deferred = WebUploader.Deferred();
                //1、计算文件的唯一标记，用于断点续传
                (new WebUploader.Uploader()).md5File(file, 0, 10 * 1024 * 1024)
                    .progress(function (percentage) {
                        console.log("正在读取文件");
                    })
                    .then(function (val) {
                        fileMd5 = val;
                        console.log("成功获取文件信息...");
                        //获取文件信息后进入下一步
                        deferred.resolve();
                    });
                return deferred.promise();
            },
            //时间点2：如果有分块上传，则每个分块上传之前调用此函数
            beforeSend: function (block) {
                var deferred = WebUploader.Deferred();

                $.ajax({
                    type: "POST",
                    url: "${pageContext.request.contextPath}/api/file/checkFile",
                    data: {
                        //文件唯一标记
                        fileMd5: fileMd5,
                        //当前分块下标
                        chunk: block.chunk,
                        //当前分块大小
                        chunkSize: block.end - block.start
                    },
                    dataType: "json",
                    success: function (response) {
                        if (response.ifExist) {
                            //分块存在，跳过
                            deferred.reject();
                        } else {
                            //分块不存在或不完整，重新发送该分块内容
                            deferred.resolve();
                        }
                    }
                });

                this.owner.options.formData.fileMd5 = fileMd5;
                deferred.resolve();
                return deferred.promise();
            },
            //时间点3：所有分块上传成功后调用此函数
            afterSendFile: function () {
                //如果分块上传成功，则通知后台合并分块
                $.ajax({
                    type: "POST",
                    url: "${pageContext.request.contextPath}/api/file/mergeChunks",
                    data: {
                        fileMd5: fileMd5,
						name:name
                    },
                    success:function (res) {
                        console.log(res);
                        if(res.success){
                            window.parent.layer.alert("上传成功",function(i){
                                window.parent.layer.close(i);
                                window.parent.frames["mainFrame"].query(window.parent.frames["mainFrame"].pageNum);
                                window.parent.layer.close(window.parent.winList.pop());
                            });

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
            }
        });

        var uploader = WebUploader.create({
            // swf文件路径
            swf: '${pageContext.request.contextPath}/js/webuploader/Uploader.swf',
            // 文件接收服务端。
            server: '${pageContext.request.contextPath}/api/file/uploadVideo',
            // 选择文件的按钮。可选。
            // 内部根据当前运行是创建，可能是input元素，也可能是flash.id就是文件id
            pick: {
                id: '#fileInfo', //这个id是你要点击上传文件按钮的外层div的id
                multiple : false //是否可以批量上传，true可以同时选择多个文件
            },
            resize: true,
            auto: false,
            //开启分片上传
            chunked: true,
            chunkSize: 10 * 1024 * 1024
        });

        // 当有文件被添加进队列的时候
        uploader.on('fileQueued', function (file) {
			$("#picker").text("已选择文件:"+file.name);
            console.log(file.name + "等待上传...");
            /* $('#item1').empty();
             $('#item1').html('<div id="' + file.id + '" class="item">' +
                 '<a class="upbtn" id="btn" onclick="stop()">[取消上传]</a>' +
                 '<p class="info">' + file.name + '</p>' +
                 '<p class="state">等待上传...</p></div>'
             );*/
        });

        // 文件上传过程中创建进度条实时显示。
        uploader.on('uploadProgress', function (file, percentage) {
            //console.log(file.name + '上传中 ' + Math.round(percentage * 100) + '%');100
            /* $('#item1').find('p.state').text('上传中 ' + Math.round(percentage * 100) + '%');*/
        });

        uploader.on('uploadSuccess', function (file) {
            console.log(file.name + '已上传');
            // $('#' + file.id).find('p.state').text('已上传');
        });

        uploader.on('uploadError', function (file) {
            console.log(file.name + '上传出错');
            //$('#' + file.id).find('p.state').text('上传出错');
        });

        uploader.on('uploadComplete', function (file) {
            // $('#' + file.id).find('.progress').fadeOut();
        });

        function start() {
             node = treeObj.getSelectedNodes()[0];
            if(!node){
                window.parent.layer.alert("请选择目录！");
                return;
            }
            uploader.upload();

        }

        function stop() {
            uploader.stop(true);
            $('#btn').attr("onclick", "start()");
            $('#btn').text("继续上传");
        }

        $("#ctlBtn").on("click", function () {
            start();
        });


  	});
  	
  	function query(params,node){
			var paramsObj = params?params:{};
			if(paramsObj.directoryId == 0){
				paramsObj = {};
			}
			$.ajax({
	      dataType: "json",
	      //type: "POST",
	      data:paramsObj,
	      url: "${pageContext.request.contextPath}/manager/widgetDirectory/getDirectoryInfo",
	      cache: false,
	      success: function(res) {
	        if(res.success){
	        	var store = res.data;
	        	var childNodes = [];
	        	//var toMoveDirectory = window.parent.frames["mainFrame"].$("#folderList").find(".seled").eq(0);
	        	for(var i=0;i<store.length;i++){
	        		//if(toMoveDirectory.attr("directoryId") != store[i].id){
		        		childNodes.push({
		        			id:store[i].id,
		        			name:store[i].directorName,
		        			isParent:true
		        		});
	        		//}
	        	}
						treeObj.removeChildNodes(node);
	        	treeObj.addNodes(node, childNodes);
	        }else{
	        	window.parent.layer.alert(res.message);
	        }
	      },
	      error: function() {
	        window.parent.layer.alert("获取目录失败！");
	      }
	    });
		}
  </script>
</html>