<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
	<head>
		<title></title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/js/zTree_v3-master/css/zTreeStyle/zTreeStyle.css" type="text/css">
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js" ></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js" ></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/zTree_v3-master/js/jquery.ztree.core.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/zTree_v3-master/js/jquery.ztree.excheck.min.js"></script>
	</head>
	
	<body>
		<ul id="treeDemo" class="ztree" style="height:330px; margin: 10px; overflow:auto;"></ul>
		<button class="btn btn-default" id="close" style="float:right; margin: 10px 20px 0 0;">关闭</button>
	</body>
	<script type="text/javascript">
		var i = (window.location.href).split("#");
		var widgetId;
		var treeObj;
		var canSet = 0;		//小于0表示失败了，一旦ajax操作失败，直接把它设置为-100
		var permission;
		if(i.length>1){
			widgetId = i[1];
		}
		var setting = {
			data: { simpleData: { enable: true, idKey: "id", pIdKey: "pId", rootPId: '0' } },
			check: {
				enable: true,
				chkStyle: "checkbox",
				chkboxType: { "Y": "", "N": "" }
			},
			callback: {
					onCheck: function (event, treeId, treeNode) {
						var id = treeNode.id;
						var url;
						if(treeNode.checked){
							url = "${pageContext.request.contextPath}/manager/widgetType/shareWidgetToOffice";
						}else{
							url = "${pageContext.request.contextPath}/manager/widgetType/unshareWidgetToOffice";
						}
						$.ajax({
							dataType: "json",
							url: url,
							data:{
								officeId:id,
								widgetId:widgetId
							},
							cache: false,
							success: function(res) {
								if(res.success){
									//window.parent.layer.alert("授权修改成功！");
								}else{
									window.parent.layer.alert(res.message);
									treeObj.checkNode(treeNode, !treeNode.checked, true);
								}
							},
							error: function() {
								window.parent.layer.alert("授权修改失败！");
							}
						});
					}
			}
		};
		$(document).ready(function(){
			if(widgetId){
				refreshTree();
				
				$.ajax({
					dataType: "json",
					url: "${pageContext.request.contextPath}/manager/widgetType/getWidgetOffice",
					data:{
						widgetId:widgetId
					},
					cache: false,
					success: function(res) {
						if(res.success){
							if(canSet>=0){
								canSet++;
								permission = res.data;
								if(canSet == 2){
									setPermission();
								}
							}
						}else{
							canSet = -100;
						}
					},
					error: function() {
						window.parent.layer.alert("授权修改失败！");
					}
				});
			}
			$("#close").on("click",function(){
				window.parent.layer.close(window.parent.winList.pop());
			});
		});
		
		function refreshTree() {
			var arr = {};
			var arrReIndex = {};
			var arrFilter = [];
			$.ajax({
				dataType: "json",
				url: "${pageContext.request.contextPath}/manager/widgetType/getOffice",
				cache: false,
				success: function(result) {
					if(result.success){
						var obj = result.data;
						var str = JSON.stringify(obj);
						str = str.replace(/organizationCode/g,"id");
						str = str.replace(/parentOrganizationCode/g,"pId");
						
						var data = $.parseJSON(str)
						data.sort(function(a,b){return a.id>b.id?1:-1});
						treeObj = $.fn.zTree.init($("#treeDemo"), setting, data);
						treeObj.expandAll(true);
						if(canSet>=0){
							canSet++;
							if(canSet == 2){
								setPermission();
							}
						}
					}else{
						canSet = -100;
					}
				},
				error: function() {
					window.parent.layer.alert("授权修改失败！");
				}
			});
    }
		
		function setPermission(){
			for(var i=0;i<permission.length;i++){
				var node = treeObj.getNodesByParam("id", permission[i].shareRange, null)[0];
				treeObj.checkNode(node, true, true);
			}
		}
	</script>
</html>
