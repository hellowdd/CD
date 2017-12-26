<%@ page contentType="text/html" pageEncoding="UTF-8" %>
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
				<button class="btn btn-default" id="addType"><span class="glyphicon glyphicon-plus"></span> 新增控件类型</button>
			</div>
			<table class="table">
				<thead>
					<tr>
						<th style="width:30%;">类型名称</th>
						<th>说明</th>
						<th style="width:10%;">操作</th>
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
		var recordSeled;
		$(document).on("ready",function(){
			//获取数据
	      query();
	      
	      //新增类型
	      $("#addType").on("click",function(){
					$(this).blur();
	      	window.parent.layer.open({
					  type: 2,
					  title: '新增控件类型',
					  shade: 0.3,
					  area: ['600px', '300px'],
					  content: '${pageContext.request.contextPath}/manager/view/addWidgetType', //iframe的url
					  success:function(layero, index){
					    window.parent.winList.push(index);
					  }
					}); 
	      });
	      
	      //表格中的升级，删除，共享，取消共享
	      $("#mainTable").on("click","button",function(e){
	      	var btn = $(e.currentTarget);
	      	var index = btn.parent().parent().index();
	      	switch(btn.attr("title")){
	      		case "修改控件类型":
							btn.blur();
	      			recordSeled = store[index];
	      			editWidgetType();
	      			break;
	      		case "删除控件类型":
							btn.blur();
							window.parent.layer.confirm('确认要删除该控件类型么？', {
								btn: ['删除','取消'] //按钮
							}, function(){
								delWidgetType(store[index].id+"");
							}, function(){
								
							});
	      			break;
	      		default:
	      			break;
	      	}
	      })
      
		});
		
		//查询控件列表
		function query() {
			$.ajax({
        dataType: "json",
        //type: "POST",
        url: "${pageContext.request.contextPath}/manager/widgetType/getWidgetType",
        cache: false,
        success: function(res) {
          if(res.success){
          	store = res.data;
          	createTable();
          }else{
          	window.parent.layer.alert("获取控件类型列表失败！"+res.message);
          }
        },
				error: function(jqXHR,textStatus,errorThrown) {
					if(jqXHR.readyState == 0 && jqXHR.responseText == "" && XHR.status == 0 && jqXHR.statusText == "error"){
						
					}else{
						window.parent.layer.alert("获取控件类型列表失败！");
					}
        }
      });
		}
		
		function createTable(){
			var str = '';
			for(var i=0;i<store.length;i++){
				var tools = '';
				var check = '';
				if(store[i].isAll == "0"){
					tools = '<button class="btn btn-default btn-xs" title="修改控件类型"><span class="glyphicon glyphicon-pencil"></span></button> ' +
									'<button class="btn btn-default btn-xs" title="删除控件类型"><span class="glyphicon glyphicon-minus"></span></button> ';
				}
				var intro = store[i].widgetTypeContent?store[i].widgetTypeContent:"";
				
				str += '<tr>' +
							 '	<td>'+store[i].widgetTypeName+'</td>' +
							 '	<td>'+intro+'</td>' +
							 '  <td>'+tools+'</td>' +
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
		
		//修改控件类型
		function editWidgetType(){
			window.parent.layer.open({
			  type: 2,
			  title: '控件类型修改',
			  shade: 0.3,
			  area: ['600px', '300px'],
			  content: '${pageContext.request.contextPath}/manager/view/editWidgetType', //iframe的url
			  success: function(layero, index){
			    window.parent.winList.push(index);
			  }
			}); 
		}
		
		//删除控件类型
		function delWidgetType(id){
			$.ajax({
        dataType: "json",
        //type: "POST",
        url: "${pageContext.request.contextPath}/manager/widgetType/removeWidgetTypeInfo",
        data:{
        	widgetTypeId:id
        },
        cache: false,
        success: function(res) {
          if(res.success){
          	window.parent.layer.alert("删除成功！",function(i){
          		window.parent.layer.close(i);
          		query();
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
		
		function getIndexById(id){
			for(var i=0;i<store.length;i++){
				if(store[i].id == id){
					return i;
				}
			}
		}
	</script>
</html>
