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
	</head>
	
	<body>
		<ul id="treeDemo" class="ztree" style="height:290px; margin: 10px;"></ul>
		<button class="btn btn-default" id="submit" style="float:right; margin-right: 20px;">移动</button>
	</body>
	<script type="text/javascript">
		var store = [];
		var setting = {
			callback:{
				beforeExpand: function(treeId, treeNode) {
					query({
						directoryId:treeNode.id,
						directoryName:treeNode.name
					},treeNode);
			    return true;
				}
			}
		};
		
		var treeObj;
		
		$(document).ready(function(){
			//初始根节点不需要查询
			var zNodes =[{
				id:"",
   			name:"根目录",
   			isParent:true
			}];
			treeObj = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
			
			//移动，能够移动目录及控件
			$("#submit").on("click",function(){
				var seledNode = treeObj.getSelectedNodes()[0];
				var toMove = window.parent.frames["mainFrame"].$("#folderList").find(".seled").eq(0);
				//移动目录
				if(toMove.attr("directoryid")){
					$.ajax({
			      dataType: "json",
			      //type: "POST",
			      data:{
			    	  directoryId:toMove.attr("directoryid"),
			    	  directoryName:toMove.html(),
			    	  parentDirectoryId:seledNode.id
			      },
			      url: "${pageContext.request.contextPath}/manager/widgetDirectory/updateDirectoryInfo",
			      cache: false,
			      success: function(res) {
			        if(res.success){
			        	window.parent.layer.close(window.parent.winList.pop());
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
			        	
			        }else{
			        	window.parent.layer.alert(res.message);
			        }
			      },
			      error: function() {
			        window.parent.layer.alert("移动目录失败！");
			      }
			    });
				}else{	//移动控件
					if(seledNode.id == ""){
						window.parent.layer.alert("控件不能存放在根目录上！");
						return;
					}
					$.ajax({
			      dataType: "json",
			      //type: "POST",
			      data:{
			    	  widgetId:toMove.attr("widgetId"),
							//widgetTypeId:toMove.attr("typeId"),
			    	  //widgetNameShow:toMove.attr("widget_name_show"),
			    	  directoryId:seledNode.id
			      },
			      url: "${pageContext.request.contextPath}/manager/widget/updateWidgetInfoBase",
			      cache: false,
			      success: function(res) {
			        if(res.success){
			        	window.parent.layer.close(window.parent.winList.pop());
			        	var parentNodeId = window.parent.frames["mainFrame"].seledRoute[window.parent.frames["mainFrame"].seledRoute.length-1];
			        	if(parentNodeId == 0){
			        		window.parent.frames["mainFrame"].query();
			        	}else{
									window.parent.frames["mainFrame"].query({
										directoryId:parentNodeId,
						    	  directoryName:window.parent.frames["mainFrame"].seledRouteTxt[window.parent.frames["mainFrame"].seledRouteTxt.length-1],
				        	});
			        	}
			        	
			        }else{
			        	window.parent.layer.alert(res.message);
			        }
			      },
			      error: function() {
			        window.parent.layer.alert("移动文件失败！");
			      }
			    });
				}
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
	        	var toMoveDirectory = window.parent.frames["mainFrame"].$("#folderList").find(".seled").eq(0);
	        	for(var i=0;i<store.length;i++){
	        		if(toMoveDirectory.attr("directoryId") != store[i].id){
		        		childNodes.push({
		        			id:store[i].id,
		        			name:store[i].directorName,
		        			isParent:true
		        		});
	        		}
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
