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
		<title></title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/sub.css" />
		<style type="text/css">
			.txt-red{
				color: red;
			}
		</style>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js" ></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js" ></script>
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
			<div class="toolbar">
				<button class="btn btn-default" id="addWidget"><span class="glyphicon glyphicon-plus"></span> 上传文件</button>
				<!--button class="btn btn-default"><span class="glyphicon glyphicon-eject"></span> 升级控件</button>
                <button class="btn btn-default" id="delWidget"><span class="glyphicon glyphicon-minus"></span> 删除控件</button-->
			</div>
			<table class="table">
				<thead>
				<tr>
					<th style="width:20px; display:none;"><input type="checkbox" id="checkAll" /></th>
					<th>文件ID</th>
					<th>文件名称</th>

					<th>文件大小</th>
					<th>上传时间</th>

					<th style="width:160px;">操作</th>
				</tr>
				</thead>

				<tbody id="mainTable"></tbody>
			</table>
			<div id="pageContainer" class="page-container"></div>
		</div>
	</body>
	<script type="text/javascript">
		//存储数据
		var store = [];
		var dataSeled = [];
		//存储页码
		var pageNum = 1;
		var recordSeled;
		var url;
		$(document).on("ready",function(){
			//获取数据
	      query(1);
	      
	      //全选check
	      $("#checkAll").on("change",function(){
	      	if($(this).prop("checked")){
	      		dataSeled = store;
	      		$("#mainTable").find("input").prop("checked",true);
	      	}else{
	      		dataSeled = [];
	      		$("#mainTable").find("input").prop("checked",false);
	      	}
	      });
	      
	      //每一行的check
	      $("#mainTable").on("change","input",function(e){
	      	var check = $(e.currentTarget);
	      	if(check.prop("checked")){
	      		dataSeled.push(store[parseInt(check.attr("index"))]);
	      	}else{
	      		for(var i=0;i<dataSeled.length;i++){
	      			if(dataSeled[i].id == store[parseInt(check.attr("index"))].id){
	      				dataSeled.splice(i,1);
	      				break;
	      			}
	      		}
	      		$("#checkAll").prop("checked",false);
	      	}
	      });

	      //新增
	      $("#addWidget").on("click",function(e){
					$("#addWidget").blur();
	      	window.parent.layer.open({
					  type: 2,
					  title: '新增上传',
					  shade: 0.3,
					  area: ['600px', '400px'],
					  content: '${pageContext.request.contextPath}/manager/view/upload', //iframe的url
					  success:function(layero, index){
					    window.parent.winList.push(index);
					  }
					}); 
	      });
	      
	      //控件升级
	      $("#updateWidget").on("click",function(){
					$("#updateWidget").blur();
	      	if(dataSeled.length == 0){
	      		window.parent.layer.alert("请先选择文件");
	      		return;
	      	}
	      	if(dataSeled.length == 0){
	      		window.parent.layer.alert("一次只能升级一个文件");
	      		return;
	      	}
	      	recordSeled = dataSeled[0];
	      	updateWidget();
	      });
	      
	      //删除，允许批量删除，用,分割
	      $("#delWidget").on("click",function(){
					$("#delWidget").blur();
	      	if(dataSeled.length == 0){
	      		window.parent.layer.alert("请先选择文件");
	      		return;
	      	}
					if(dataSeled.length > 1){
	      		window.parent.layer.alert("暂时不支持多选");
	      		return;
	      	}
					window.parent.layer.confirm('确认要删除该文件么？', {
						btn: ['删除','取消'] //按钮
					}, function(){
						var ids=[];
						for(var i=0;i<dataSeled.length;i++){
							ids.push(dataSeled[i].id);
						}
						delWidget(ids);
					}, function(){
						
					});
	      });
	      
	      //表格中的升级，删除，共享，取消共享
	      $("#mainTable").on("click","button",function(e){
	          url = $(this).parent().parent().find(".url").text();
	      	var btn = $(e.currentTarget);
					btn.blur();
	      	var index = btn.parent().parent().index();
	      	switch(btn.attr("title")){
	      		case "升级文件":
	      			recordSeled = store[index];
	      			updateWidget();
	      			break;
						case "编辑文件基本信息":
							recordSeled = store[index];
							editWidget();
							break;
	      		case "删除文件":
							window.parent.layer.confirm('确认要删除该文件么？', {
								btn: ['删除','取消'] //按钮
							}, function(){
								delWidget(store[index].id+"",store[index].storage_path);
							}, function(){
								
							});
	      			break;
	      		case "共享":
	      			shareWidget(store[index].id+"");
	      			break;
	      		case "取消共享":
	      			cancelShareWidget(store[index].id+"");
	      			break;
						case "文件授权":
	      			setWidgetPermission(store[index].id+"");
	      			break;
	      		default:
	      			break;
	      	}
	      })

		});
		
		//查询控件列表
		function query(curr) {
			$.ajax({
        dataType: "json",
        //type: "POST",
        url: "${pageContext.request.contextPath}/manager/widget/getWidgetList",
        data:{
        	pageNum:curr,
        	pageSize:20
        },
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
          	window.parent.layer.alert("获取文件列表失败！"+res.message);
          }
        },
        error: function(jqXHR,textStatus,errorThrown) {
					if(jqXHR.readyState == 0 && jqXHR.responseText == "" && XHR.status == 0 && jqXHR.statusText == "error"){
						
					}else{
						window.parent.layer.alert("获取文件列表失败！");
					}
        }
      });
		}
		
		function createTable(){

			var str = '';
			for(var i=0;i<store.length;i++){
				var tools = '<button class="btn btn-default btn-xs" title="升级文件"><span class="glyphicon glyphicon-eject"></span></button> ' +
										'<button class="btn btn-default btn-xs" title="删除文件"><span class="glyphicon glyphicon-minus"></span></button> ';
				if(store[i].is_share == "0"){
					tools += '<button class="btn btn-default btn-xs" title="共享"><span class="glyphicon glyphicon-share"></span></button> ';
				}else{
					tools += '<button class="btn btn-default btn-xs" title="取消共享"><span class="glyphicon glyphicon-ban-circle"></span></button> ';
				}
				str += '<tr>' +
							 '	<td style="display:none;"><input type="checkbox" index="'+i+'" /></td>' +
							 '	<td>'+store[i].id+'</td>' +
							 '	<td>'+store[i].widget_name+'</td>' +
							 '	<td>'+getSize(store[i].widget_size)+'</td>' +
							 '	<td>'+formatDate(new Date(store[i].upload_time))+'</td>' +
							 '  <td>'+tools+'</td>' +
                    '	<td style="display: none" class="url">'+store[i].storagePath+'</td>' +
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
		

		//编辑控件基本信息，不能修改控件版本及上传新的控件内容
		function editWidget(){
			window.parent.layer.open({
			  type: 2,
			  title: '修改文件信息',
			  shade: 0.3,
			  area: ['600px', '460px'],
			  content: '${pageContext.request.contextPath}/manager/view/editWidget1', //iframe的url
			  success: function(layero, index){
			    window.parent.winList.push(index);
			  }
			}); 
		}
		
		//升级控件，不能修改控件类别
		function updateWidget(){
			window.parent.layer.open({
			  type: 2,
			  title: '升级文件',
			  shade: 0.3,
			  area: ['600px', '460px'],
			  content: '${pageContext.request.contextPath}/manager/view/updateWidget', //iframe的url
			  success: function(layero, index){
			    window.parent.winList.push(index);
			  }
			}); 
		}
		
		//删除控件
		function delWidget(ids,path){
			$.ajax({
        dataType: "json",
        //type: "POST",
        url: "${pageContext.request.contextPath}/manager/widget/deleteWidget",
        data:{
        	widgetId:ids,
        	widgetPath:path
        },
        cache: false,
        success: function(res) {
          if(res.success){
          	window.parent.layer.alert("删除成功！",function(i){
          		window.parent.layer.close(i);
          		query(pageNum,20);
          		$("#checkAll").prop("checked",false);
          		dataSeled = [];
          	});
          }else{
          	window.parent.layer.alert("删除失败！"+res.message);
          }
        },
        error: function() {
          window.parent.layer.alert("删除失败！");
        }
      });
		}
		
		//共享
		function shareWidget(ids){
			$.ajax({
        dataType: "json",
        //type: "POST",
        url: "${pageContext.request.contextPath}/manager/widget/shareWidgetInfo",
        data:{
        	widgetId:ids,
        	shareRange:"all"
        },
        cache: false,
        success: function(res) {
          if(res.success){
          	var idList = ids.split(",");
          	for(var i=0;i<idList.length;i++){
          		var index = getIndexById(idList[i]);
          		$("#mainTable").find("tr").eq(index).find("button").eq(2).attr("title","取消共享");
          		$("#mainTable").find("tr").eq(index).find(".glyphicon").eq(2).removeClass("glyphicon-share").addClass("glyphicon-ban-circle");
          	}
      			window.parent.layer.alert("共享成功！"+url);
          }else{
          	window.parent.layer.alert("共享失败！"+res.message);
          }
        },
        error: function() {
          window.parent.layer.alert('共享失败！');
        }
      });
		}
		
		//取消共享
		function cancelShareWidget(ids){
			$.ajax({
        dataType: "json",
        //type: "POST",
        url: "${pageContext.request.contextPath}/manager/widget/unshareWidgetInfo",
        data:{
        	widgetId:ids
        },
        cache: false,
        success: function(res) {
          if(res.success){
          	var idList = ids.split(",");
          	for(var i=0;i<idList.length;i++){
          		var index = getIndexById(idList[i]);
          		$("#mainTable").find("tr").eq(index).find("button").eq(2).attr("title","共享");
          		$("#mainTable").find("tr").eq(index).find(".glyphicon").eq(2).addClass("glyphicon-share").removeClass("glyphicon-ban-circle");
          	}
          	window.parent.layer.alert('取消共享成功！');
          }else{
          	window.parent.layer.alert("取消共享失败！"+res.message);
          }
        },
        error: function() {
          window.parent.layer.alert("取消共享失败！");
        }
      });
		}
		
		function setWidgetPermission(id){
			window.parent.layer.open({
			  type: 2,
			  title: '设置文件权限',
			  shade: 0.3,
			  area: ['600px', '460px'],
			  content: '${pageContext.request.contextPath}/manager/view/setWidgetPermission#'+id, //iframe的url
			  success: function(layero, index){
			    window.parent.winList.push(index);
			  }
			});
		}

		
		function getIndexById(id){
			for(var i=0;i<store.length;i++){
				if(store[i].id == id){
					return i;
				}
			}
		}
        function  formatDate(objD)   {
            var str,colorhead,colorfoot;
            var yy = objD.getYear();
            if(yy<1900) yy = yy+1900;
            var MM = objD.getMonth()+1;
            if(MM<10) MM = '0' + MM;
            var dd = objD.getDate();
            if(dd<10) dd = '0' + dd;
            var hh = objD.getHours();
            if(hh<10) hh = '0' + hh;
            var mm = objD.getMinutes();
            if(mm<10) mm = '0' + mm;
            var ss = objD.getSeconds();
            if(ss<10) ss = '0' + ss;
            str =  yy + "-" + MM + "-" + dd + " " + hh + ":" + mm + ":" + ss;
            return(str);
        }

	</script>
</html>
