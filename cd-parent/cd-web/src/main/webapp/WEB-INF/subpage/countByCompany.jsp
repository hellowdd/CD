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
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/highchart/highcharts.js" ></script>
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
						<th>控件名称</th>
						<th>使用次数</th>
					</tr>
				</thead>
				<tbody id="tBody"></tbody>
			</table>
			<div style="float:right; height: 40px;" id="page"></div>
		</div>
	</body>
	<script type="text/javascript">
		var companyId;
		$(document).on("ready",function(){
			var arr = window.location.href.split("#");
			if(arr.length<2){
				window.parent.layer.alert("未获取到用户信息！");
			}else{
				companyId = arr[1];
				//获取数据
		  	query(1);
			}
		});
		//查询控件列表
		function query(pageNum) {
			$.ajax({
		    dataType: "json",
		    //type: "POST",
		    url: "${pageContext.request.contextPath}/manager/widget/getWidgetReleaseNum",
				data:{
					uploadUserid:companyId,
					pageSize: 20,
					pageNum:pageNum
				},
		    cache: false,
		    success: function(res) {
		      if(res.success){
		      	createTable(res.data);
		      	laypage({
							cont: 'page', //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
							pages: Math.ceil(res.count/20), //通过后台拿到的总页数
							curr: pageNum || 1, //当前页
							skin: '#428bca',
							jump: function(obj, first) { //触发分页后的回调
								if(!first) { //点击跳页触发函数自身，并传递当前页：obj.curr
									query(obj.curr);
								}
							}
						});
		      }else{
		      	window.parent.layer.alert("获取统计列表失败！"+res.message);
		      }
		    },
		    error: function() {
		      window.parent.layer.alert("获取统计列表失败！");
		    }
		  });
		}
			
		function createTable(data){
			var str = '';
			for(var i=0;i<data.length;i++){
				str += '<tr>' +
							 '	<td><span>'+data[i].widget_name_show+'</span></td>' +
							 '	<td>'+data[i].release_times+'</td>' +
							 '</tr>';
			}
			$("#tBody").html(str);
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
	</script>
</html>
