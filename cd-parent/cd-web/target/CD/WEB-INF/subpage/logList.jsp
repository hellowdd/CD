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
			<table class="table">
				<thead>
					<tr>
						<th>操作用户</th>
						<!-- <th>所属组织</th> -->
						<th>操作类型</th>
						<th>操作内容</th>
						<th>操作时间</th>
					</tr>
				</thead>
				<tbody id="mainTable"></tbody>
			</table>
			<div id="pageContainer" style="text-align:right;"></div>
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
		  
		  $("#mainTable").on("click","button",function(e){
		  	var btn = $(e.currentTarget);
		  	switch(btn.attr("title")){
		  		case "查看详细":
		  			window.parent.layer.open({
						  type: 2,
						  title: '查看详细',
						  shade: 0.3,
						  area: ['600px', '400px'],
						  content: 'subpage/view.html', //iframe的url,
						  success:function(layero, index){
						  	window.parent.winList.push(index);
						  }
						});
		  			break;
		  		case "下载":
		  			break;
		  		default:
		  			break;
		  	}
		  });
		  
		});
			
		//查询控件列表
		function query(curr) {
			$.ajax({
		    dataType: "json",
		    //type: "POST",
		    url: "${pageContext.request.contextPath}/manager/operationLog/getLogInfoList",
				data:{
					pageNum:curr,
					pageSize:20
				},
		    cache: false,
		    success: function(res) {
		      if(res.success){
		      	store = res.data.logList;
		      	createTable();
		      	pageNum = curr;
		      	laypage({
							cont: 'pageContainer', //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
							pages: Math.ceil(res.data.logCount/20), //通过后台拿到的总页数
							curr: curr || 1, //当前页
							skin: '#428bca',
							jump: function(obj, first) { //触发分页后的回调
								if(!first) { //点击跳页触发函数自身，并传递当前页：obj.curr
									query(obj.curr);
								}
							}
						});
		      }else{
		      	window.parent.layer.alert("获取日志列表失败！"+res.message);
		      }
		    },
				error: function(jqXHR,textStatus,errorThrown) {
					if(jqXHR.readyState == 0 && jqXHR.responseText == "" && XHR.status == 0 && jqXHR.statusText == "error"){
						
					}else{
						window.parent.layer.alert("获取日志列表失败！");
					}
        }
		  });
		}
			
		function createTable(){
			var str = '';
			for(var i=0;i<store.length;i++){
				str += '<tr>' +
							 '	<td>'+store[i].username+'</td>' +
							 /* '	<td>'+filterBlank(store[i].org_name)+'</td>' + */
							 '	<td>'+store[i].operateType+'</td>' +
							 '	<td>'+store[i].operateContent+'</td>' +
							 '	<td>'+formatDate(new Date(store[i].operateTime))+'</td>' +
							 '</tr>';
			}
			$("#mainTable").html(str);
		}
		
		function getIndexById(id){
			for(var i=0;i<store.length;i++){
				if(store[i].id == id){
					return i;
				}
			}
		}
		
		function filterBlank(str){
			if(str){
				if(str == "undefined"){
					return "";
				}else{
					return str;
				}
			}else{
				return "";
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
