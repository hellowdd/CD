<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title></title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/js/bootstrap-datepicker/css/bootstrap-datepicker.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/sub.css" />
		<style type="text/css">
			.txt-red{
				color: red;
			}
		</style>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js" ></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js" ></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap-datepicker/js/bootstrap-datepicker.min.js" ></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js" ></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/laypage-v1.3/laypage/laypage.js" ></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/base.js" ></script>
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
		<div style="margin:20px;">
			<div class="toolbar" style="display:none;">
				<input type="text" class="form-control" style="width:150px; display:inline-block;" id="widgetId" placeholder="控件ID" />
				<input type="text" class="form-control" style="width:150px; display:inline-block;" id="widgetName" placeholder="控件名称" />
				<input type="text" class="form-control datepicker" style="width:150px; display:inline-block;" id="start" readonly data-provide="datepicker" placeholder="起始时间" />
				 - 
				<input type="text" class="form-control" style="width:150px; display:inline-block;" id="end" placeholder="结束时间" />
				<button class="btn btn-default" id="search">搜索</button>
			</div>
			<table class="table">
				<thead>
					<tr>
						<th>控件ID</th>
						<th>控件名称</th>
						<th>控件版本</th>
						<th>上传人</th>
						<th>上传时间</th>
						<th>说明</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody id="mainTable"></tbody>
			</table>
			<div id="pageContainer"></div>
			<iframe id="download" style="display:none;"></iframe>
		</div>
	</body>
	<script type="text/javascript">
		//存储数据
		var store = [];
		var dataSeled = [];
		//存储页码
		var pageNum = 1;
		$(document).on("ready",function(){
			//获取数据
		  query(1);
		  
		 /*  $("#start").datetimepicker({format: 'yyyy-mm-dd'}); */
			
			/* $("#search").on("click",function(){
				var obj = {};
				if($("#widgetId").val() != ""){
					obj.widgetId = $("#widgetId").val();
				}
				if($("#widgetName").val() != ""){
					obj.widgetName = $("#widgetName").val();
				}
				if($("#start").getDate() != ""){
					obj.start = $("#start").getDate();
				}
				if($("#end").getDate() != ""){
					obj.end = $("#end").getDate();
				}
				query(1,obj);
			}); */
		  
		  $("#mainTable").on("click","button",function(e){
		  	var btn = $(e.currentTarget);
				btn.blur();
		  	switch(btn.attr("title")){
		  		case "查看详细":
		  			window.parent.layer.open({
						  type: 2,
						  title: '查看详细',
						  shade: 0.3,
						  area: ['600px', '400px'],
						  content: '${pageContext.request.contextPath}/manager/view/view', //iframe的url,
						  success:function(layero, index){
						  	window.parent.winList.push(index);
						  }
						});
		  			break;
		  		case "下载":
		  			var btn = $(e.currentTarget);
		  			var index = btn.parent().parent().index();
		  			var widgetId = store[index].id;
		  			var widgetName = store[index].widget_name;
		  			$("#downloaddownload").attr("src","${pageContext.request.contextPath}/manager/widget/downloadWidget?widgetId="+widgetId+"&widgetName="+widgetName);
		  			break;
		  		default:
		  			break;
		  	}
		  });
		  
		});
			
		//查询控件列表
		function query(curr,obj) {
			var paramsObj = {
					pageNum:curr,
		    	pageSize:20
			};
			$.extend(paramsObj, obj);
			$.ajax({
		    dataType: "json",
		    //type: "POST",
		    data:paramsObj,
		    url: "${pageContext.request.contextPath}/manager/widget/getWidgetShareList",
		    cache: false,
		    success: function(res) {
		      if(res.success){
		      	store = res.data;
		      	createTable();
		      	pageNum = curr;
		      	laypage({
							cont: 'pageContainer', //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
							pages: Math.ceil(res.count/20), //通过后台拿到的总页数
							curr: curr || 1, //当前页
							skin: '#428bca',
							jump: function(obj, first) { //触发分页后的回调
								if(!first) { //点击跳页触发函数自身，并传递当前页：obj.curr
									query(obj.curr);
								}
							}
						});
		      }else{
		      	window.parent.layer.alert("获取共享控件列表失败！"+res.message);
		      }
		    },
				error: function(jqXHR,textStatus,errorThrown) {
					if(jqXHR.readyState == 0 && jqXHR.responseText == "" && XHR.status == 0 && jqXHR.statusText == "error"){
						
					}else{
						window.parent.layer.alert("获取共享控件列表失败！");
					}
        }
		  });
		}
			
		function createTable(){
			var str = '';
			for(var i=0;i<store.length;i++){
				str += '<tr>' +
							 '	<td>'+store[i].id+'</td>' +
							 '	<td>'+store[i].widget_name_show+'</td>' +
							 '	<td>'+store[i].widget_version+'</td>' +
							 '	<td>'+store[i].upload_username+'</td>' +
							 '	<td>'+dateFormat(new Date(store[i].upload_time))+'</td>' +
							 '	<td>'+store[i].remarks+'</td>' +
							 '  <td>' +
							 '		<button class="btn btn-default btn-xs" title="下载"><span class="glyphicon glyphicon-save"></span></button>' +
							 /* '		<button class="btn btn-default btn-xs" title="查看详细"><span class="glyphicon glyphicon-eye-open"></span></button>' + */
							 '	</td>' +
							 '</tr>';
			}
			$("#mainTable").html(str);
		}
			
		function getSize(size){
			var m = Math.floor(size/(1024*1024));
			var k = Math.floor((size-m*1024*1024)/1024);
			var b;
			if(m > 0){
				return (size/(1024*1024)).toFixed(2)+"MB";
			}else if(k > 0){
				return (size/1024).toFixed(2)+"KB";
			}else{
				return size+"B";
			}
		}
		
		function getIndexById(id){
			for(var i=0;i<store.length;i++){
				if(store[i].id == id){
					return i;
				}
			}
		}
	</script>
</html>
