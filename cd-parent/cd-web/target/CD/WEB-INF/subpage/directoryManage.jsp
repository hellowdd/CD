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
			<div class="toolbar" style="border-bottom: 1px solid #eee; padding-bottom: 10px; margin-bottom: 0;">
				<button class="btn btn-default" id="addDirectory" title="新建目录"><span class="glyphicon glyphicon-plus"></span> 新建目录</button>
				<button class="btn btn-default" id="eidtDirectory" title="修改目录"><span class="glyphicon glyphicon-pencil"></span> 修改目录</button>
				<button class="btn btn-default" id="delDirectory" title="删除"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				<button class="btn btn-default" id="moveDirectory" title="移动"><span class="glyphicon glyphicon-share-alt"></span> 移动</button>
				<button class="btn btn-default" id="uploadFile" title="上传文件"><span class="glyphicon glyphicon-upload"></span> 上传文件</button>
				<button class="btn btn-default " title="升级文件"><span class="glyphicon glyphicon-eject"></span>升级文件</button>
				<button id="qxgx" style="display: none" class="btn btn-default " title="取消共享"><span class="glyphicon glyphicon-ban-circle"></span> 取消共享</button>
				<button id="gx" style="display: none" class="btn btn-default " title="共享"><span class="glyphicon glyphicon-share"></span> 共享</button>
				<a id="dowland"   class="btn btn-default " title="下载"><span class="glyphicon glyphicon-save"></span> 下载</a>

			</div>
			<div id="route" class="route">
				<a href="javascript:void(0)" directoryId="0" >根目录</a>
			</div>
			<div id="folderList" class="folder-container"></div>
			<div id="directoryTree" style="display:none;">
				<div id="tree"></div>
			</div>
		</div>
		<iframe id="downloads" style="display:none;"></iframe>
	</body>
	<script type="text/javascript">
		//存储数据
		var store;
		var storeWidget;
		var stores = {};
		//面包屑
		var seledRoute = [""];
		var seledRouteTxt = ["根目录"];
		var url ='';
		var fileName='';
		var isShare=""
		$(document).on("ready",function(){
			//获取数据
      query({
    	  "directoryId":""
      });

			//通过面包屑切换目录
      $("#route").on("click","a",function(e){
    	  var a = $(e.currentTarget);
    	  var last;
    	  //先过滤出面包屑的列表
    	  for(var i=0;i<seledRoute.length;i++){
    		  if(seledRoute[i] == a.attr("directoryId")){
    			  last = i+1;
    			  break;
    		  }
    	  }
    	  seledRoute.splice(last,seledRoute.length-1);
    	  seledRouteTxt.splice(last,seledRouteTxt.length-1);
    	  /* console.log(seledRoute); */
    	  var routeStrList = [];
				for(var i=0;i<seledRoute.length;i++){
					routeStrList.push('<a href="javascript:void(0)" directoryId="'+seledRoute[i]+'">'+seledRouteTxt[i]+'</a>');
				}
				$("#route").html(routeStrList.join(">"));
				var obj = a.attr("directoryId") != ""?{
					directoryId:a.attr("directoryId"),
					directoryName:a.html()
				}:{
					"directoryId":""
				};
				$("#gx,#qxgx").hide();
				query(obj);

			});

      //上传文件


            $("#uploadFile").on("click",function(e){
                if($("#route").find('a').length==1){
					window.parent.layer.alert("不可在根目录进行上传操作!")
					return
                }

               var id = $("#route a:last-child").attr('directoryId');
                window.parent.layer.open({
                    type: 2,
                    title: '新增上传',
                    shade: 0.3,
                    area: ['600px', '400px'],
                    content: '${pageContext.request.contextPath}/manager/view/upload2?'+id, //iframe的url
                    success:function(layero, index){
                        window.parent.winList.push(index);
                    }
                });
            });


            $(".toolbar").on("click","button",function(e){
                var btn = $(e.currentTarget);
                btn.blur();
                var index = btn.parent().parent().index();
                switch(btn.attr("title")){
                    case "升级文件":
                        recordSeled = stores;
                        updateWidget();
                        break;
                    case "编辑文件基本信息":
                        recordSeled = stores;
                        editWidget();
                        break;
                    case "删除文件":
                        window.parent.layer.confirm('确认要删除该文件么？', {
                            btn: ['删除','取消'] //按钮
                        }, function(){
                            delWidget(stores.id+"",stores.path);
                        }, function(){

                        });
                        break;
                    case "共享":
                        shareWidget(stores.id+"");
                        break;
                    case "取消共享":
                        cancelShareWidget(stores.id+"");
                        break;
                    case "文件授权":
                        setWidgetPermission(stores.id+"");
                        break;
                    default:
                        break;
                }
            })

            //升级控件，不能修改控件类别
            function updateWidget(){

                var folderList = $("#folderList").find(".seled");
                if(folderList.length>0){
                    if($("#route").find('a').length==1){
                        window.parent.layer.alert("不可在根目录进行升级操作!")
                        return
                    }
                window.parent.layer.open({
                    type: 2,
                    title: '升级文件',
                    shade: 0.3,
                    area: ['600px', '460px'],
                    content: '${pageContext.request.contextPath}/manager/view/updateWidget2', //iframe的url
                    success: function(layero, index){
                        window.parent.winList.push(index);
                    }
                });
				}else{
                    window.parent.layer.alert("请先选择一个文件！")

                }
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
                                var obj = {
                                    directoryId:parentDirectoryId
                                }
                                if(parentDirectoryId != ""){
                                    obj.directoryName = parentDirectoryName
                                }
                                query();
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
                var parentDirectoryId = seledRoute[seledRoute.length-1];
                var parentDirectoryName = seledRouteTxt[seledRouteTxt.length-1];
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

                            $("#qxgx").css("display","inline-block")
                            $("#gx").css("display","none")
                            window.parent.layer.alert("共享成功！"+url)
                            var obj = {
                                directoryId:parentDirectoryId
                            }
                            if(parentDirectoryId != ""){
                                obj.directoryName = parentDirectoryName
                            }
                            query(obj);
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
                var parentDirectoryId = seledRoute[seledRoute.length-1];
                var parentDirectoryName = seledRouteTxt[seledRouteTxt.length-1];
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
                            $("#gx").css("display","inline-block");
                            $("#qxgx").css("display","none");
                            window.parent.layer.alert('取消共享成功！');
                            var obj = {
                                directoryId:parentDirectoryId
                            }
                            if(parentDirectoryId != ""){
                                obj.directoryName = parentDirectoryName
                            }
                            query(obj);

                        }else{
                            window.parent.layer.alert("取消共享失败！"+res.message);
                        }
                    },
                    error: function() {
                        window.parent.layer.alert("取消共享失败！");
                    }
                });
            }
      //选择控件或目录
			$("#dowland").click(function () {
			    if(url=="undefined"||url == ""){
			        window.parent.layer.alert("请选择一个文件！")
				}else{
                    $("#downloads").attr("src","${pageContext.request.contextPath}/manager/widget/downloadWidget?widgetId="+stores["id"]+"&widgetName="+fileName);
				}
            });

			$("#folderList").on("click","a",function(e){
                fileName = $(this).attr("title");
                $("#dowland").attr("href","####");

                var thisid
                if($(this).attr('widgetid')==undefined){

				}else{
                     thisid = $(this).attr('widgetid');
				}
               var path = $(this).attr('parth');
			   stores["id"]=thisid;
			   stores["path"]=path;
                url = $(this).attr('storagepath');
				var a = $(e.currentTarget);
				var ss =$(this).attr('share')
				if($(this).attr('share')==0||$(this).attr('share')==null){
                    $("#gx").css("display","inline-block");
                    $("#qxgx").css("display","none");
				}else if($(this).attr('share')==1){
                    $("#qxgx").css("display","inline-block");
                    $("#gx").css("display","none");
				}else if($(this).attr('share')=="undefined"){
                    $("#qxgx").css("display","none");
                    $("#gx").css("display","none");
				}
				$("#toolBar").show();
				if(!a.hasClass("seled")){
					$("#folderList").find(".seled").removeClass("seled");
					a.addClass("seled");
				}
			});
			
			//双击进入目录
			$("#folderList").on("dblclick",".folder",function(e){
                $('#uploadFile').attr("disabled",false)
				var a = $(e.currentTarget);
				seledRoute.push(a.attr("directoryId"));
				seledRouteTxt.push(a.html());
				var routeStrList = [];
				for(var i=0;i<seledRoute.length;i++){
					routeStrList.push('<a href="javascript:void(0)" directoryId="'+seledRoute[i]+'">'+seledRouteTxt[i]+'</a>');
				}
				$("#route").html(routeStrList.join(">"));
				var obj = {
					directoryId:a.attr("directoryId"),
					directoryName:a.html()
				}
				query(obj);
			});
			
			//新建目录
			$("#addDirectory").on("click",function(){
				window.parent.layer.prompt({title: '请输入文件夹名称', formType: 0}, function(text, index){
					if(text.replace(/[\u4e00-\u9fa5]/g,"aa").length>40){
						window.parent.layer.alert("文件夹名称字数超过限制,最多20个字");
						return;
					}
					var parentDirectoryId = seledRoute[seledRoute.length-1];
					var parentDirectoryName = seledRouteTxt[seledRouteTxt.length-1];
			    $.ajax({
		        dataType: "json",
		        //type: "POST",
		        data:{
		        	directoryName:text,
		        	parentDirectoryId:parentDirectoryId
		        },
		        url: "${pageContext.request.contextPath}/manager/widgetDirectory/creatDirectoryInfo",
		        cache: false,
		        success: function(res) {
		          if(res.success){
		        	  window.parent.layer.close(index);
		        	  var obj = {
									directoryId:parentDirectoryId
								}
								if(parentDirectoryId != ""){
									obj.directoryName = parentDirectoryName
								}
		        	  query(obj);
		          }else{
		          	window.parent.layer.alert(res.message);
		          }
		        },
		        error: function() {
		          window.parent.layer.alert("创建目录失败！");
		        }
		      });
			  });
			});
			
			//编辑目录
			$("#eidtDirectory").on("click",function(){
				var folderList = $("#folderList").find(".seled");
				if(folderList.length>0){
					if(!folderList.eq(0).attr("directoryId")){
						window.parent.layer.alert("只能修改目录，文件升级请到文件列表操作！");
						return;
					}
					window.parent.layer.prompt({title: '请输入文件夹名称', formType: 0}, function(text, index){
						if(text.replace(/[\u4e00-\u9fa5]/g,"aa").length>40){
							window.parent.layer.alert("文件夹名称字数超过限制,最多20个字");
							return;
						}
						var parentDirectoryId = seledRoute[seledRoute.length-1];
						var parentDirectoryName = seledRouteTxt[seledRouteTxt.length-1];
						directoryId = folderList.eq(0).attr("directoryId");
						$.ajax({
			        dataType: "json",
			        //type: "POST",
			        data:{
			        	directoryName:text,
			        	parentDirectoryId:parentDirectoryId,
			        	directoryId:directoryId
			        },
			        url: "${pageContext.request.contextPath}/manager/widgetDirectory/updateDirectoryInfo",
			        cache: false,
			        success: function(res) {
			          if(res.success){
			        	  window.parent.layer.close(index);
			        	  var obj = {
										directoryId:parentDirectoryId
									}
									if(parentDirectoryId != ""){
										obj.directoryName = parentDirectoryName
									}
			        	  query(obj);
			          }else{
			          	window.parent.layer.alert(res.message);
			          }
			        },
			        error: function() {
			          window.parent.layer.alert("修改目录失败！");
			        }
			      });
					});
				}else{
					window.parent.layer.alert("请先选择一个目录！");
				}
			});
			
			//删除目录
			$("#delDirectory").on("click",function(){
				$("#delDirectory").blur();
				var list = $("#folderList").find(".seled");
				if(list.length>0){
					if(list.eq(0).attr("directoryId")){
						var parentDirectoryId = seledRoute[seledRoute.length-1];
						var parentDirectoryName = seledRouteTxt[seledRouteTxt.length-1];
						directoryId = list.eq(0).attr("directoryId");
						$.ajax({
			        dataType: "json",
			        //type: "POST",
			        data:{
			        	directoryId:directoryId
			        },
			        url: "${pageContext.request.contextPath}/manager/widgetDirectory/removeDirectoryInfo",
			        cache: false,
			        success: function(res) {
			          if(res.success){
			        	  var obj = {
										directoryId:parentDirectoryId
									}
									if(parentDirectoryId != ""){
										obj.directoryName = parentDirectoryName
									}
			        	  query(obj);
			          }else{
			          	window.parent.layer.alert("删除目录失败！"+res.message);
			          }
			        },
			        error: function() {
			          window.parent.layer.alert("删除目录失败！");
			        }
			      });
					}else{
						var parentDirectoryId = seledRoute[seledRoute.length-1];
						var parentDirectoryName = seledRouteTxt[seledRouteTxt.length-1];
						$.ajax({
			        dataType: "json",
			        //type: "POST",
			        url: "${pageContext.request.contextPath}/manager/widget/deleteWidget",
			        data:{
			        	widgetId:list.eq(0).attr("widgetId"),
			        	widgetPath:parentDirectoryId
			        },
			        cache: false,
			        success: function(res) {
			          if(res.success){
			          	var obj = {
										directoryId:parentDirectoryId
									}
									if(parentDirectoryId != ""){
										obj.directoryName = parentDirectoryName
									}
									query(obj);
			          }else{
			          	window.parent.layer.alert("删除文件失败！"+res.message);
			          }
			        },
			        error: function() {
			          window.parent.layer.alert("删除文件失败！");
			        }
			      });
					}
				}else{
					window.parent.layer.alert("请先选择一个目录！");
				}
			});
			
			//移动目录，控件移动和目录移动调用不同方法
			$("#moveDirectory").on("click",function(){
				$("#moveDirectory").blur();
				var folderList = $("#folderList").find(".seled");
				if(folderList.length>0){
					window.parent.layer.open({
						  type: 2,
						  title: '移动至',
						  shade: 0.3,
						  area: ['600px', '410px'],
						  content: '${pageContext.request.contextPath}/manager/view/directoryTree', //iframe的url
						  success:function(layero, index){
						    window.parent.winList.push(index);
						  }
						}); 
				}else{
					window.parent.layer.alert("请先选择一个目录！");
				}
			});
		});
		
		//获取目录
		function query(obj) {
		    console.log(obj)
			//获取目录
			$.ajax({
        dataType: "json",
        //type: "POST",
        data:obj,
        url: "${pageContext.request.contextPath}/manager/widgetDirectory/getDirectoryInfo",
        cache: false,
        success: function(res) {
          if(res.success){
          	store = res;
          	createAll();
          }else{
          	window.parent.layer.alert(res.message);
          }
        },
				error: function(jqXHR,textStatus,errorThrown) {
					if(jqXHR.readyState == 0 && jqXHR.responseText == "" && XHR.status == 0 && jqXHR.statusText == "error"){
						
					}else{
						window.parent.layer.alert("获取目录失败！");
					}
        }
      });
			
			//获取控件
			
			if(obj.directoryId != ""){
				$.ajax({
	        dataType: "json",
	        //type: "POST",
	        data:{
		       	pageNum:1,
		       	pageSize:10000,
		       	directoryId:obj.directoryId
		      },
	        url: "${pageContext.request.contextPath}/manager/widget/getWidgetList",
	        cache: false,
	        success: function(res) {
	          if(res.success){
	        	  storeWidget = res;
	        	  createAll();
	          }else{
	          	window.parent.layer.alert(res.message);
	          }
	        },
					error: function(jqXHR,textStatus,errorThrown) {
						if(jqXHR.readyState == 0 && jqXHR.responseText == "" && XHR.status == 0 && jqXHR.statusText == "error"){
							
						}else{
							window.parent.layer.alert("获取目录失败！");
						}
					}
	      });
			}else{
				storeWidget = {data:[]};
				createAll();
			}
		}
		
		//只有当2次ajax都获得结果了才会添加内容
		function createAll(){
			if(store && storeWidget){
				$("#folderList").empty();
				var strDirectory = getFolderStr();
				var strWidget = getWidgetStr();
				if(strDirectory != "" || strWidget != ""){
					$("#folderList").html(strDirectory+strWidget);
				}else{
					$("#folderList").html('<p style="text-align:center; color: #999;">没有内容</p>');
				}
				store = null;
				storeWidget = null;
			}
		}
		
		function getFolderStr(){
			var str = "";
			if(store.data.length>0){
				for(var i=0;i<store.data.length;i++){
                    str += '<a href="javascript:void(0)" class="folder" share="'+store.data[i].is_share+'" storagePath="'+store.data[i].storagePath+'" directoryId="'+store.data[i].id+'" parth="'+store.data[i].storage_path+'" title="'+store.data[i].directorName+'">'+store.data[i].directorName+'</a>';
				}
			}
			return str;
		}
		
		function getWidgetStr(){
			var str = "";
			if(storeWidget.data.length>0){
				for(var i=0;i<storeWidget.data.length;i++){
                    if(storeWidget.data[i].fileType=="0"){
                        str += '<a href="javascript:void(0)" class="doc-else" ' +
                            'share="'+storeWidget.data[i].is_share+'"' +
                            ' storagePath="'+storeWidget.data[i].storagePath+'" ' +
                            'widgetId="'+storeWidget.data[i].id+'" ' +
                            'parth="'+storeWidget.data[i].storage_path+'" ' +
                            'widget_name_show="'+storeWidget.data[i].widget_name_show+'" ' +
                            'title="'+storeWidget.data[i].widget_name+'" ' +
                            'typeId = "'+storeWidget.data[i].widget_type_id+'">' +
                            ''+storeWidget.data[i].widget_name+'</a>';
                    }else if(storeWidget.data[i].fileType=="1"){
                        str += '<a href="javascript:void(0)" class="document" ' +
                            'share="'+storeWidget.data[i].is_share+'"' +
                            ' storagePath="'+storeWidget.data[i].storagePath+'" ' +
                            'widgetId="'+storeWidget.data[i].id+'" ' +
                            'parth="'+storeWidget.data[i].storage_path+'" ' +
                            'widget_name_show="'+storeWidget.data[i].widget_name_show+'" ' +
                            'title="'+storeWidget.data[i].widget_name+'" ' +
                            'typeId = "'+storeWidget.data[i].widget_type_id+'">' +
                            ''+storeWidget.data[i].widget_name+'</a>';

                    }else if(storeWidget.data[i].fileType=="2"){
                        str += '<a href="javascript:void(0)" class="pic" ' +
                            'share="'+storeWidget.data[i].is_share+'"' +
                            ' storagePath="'+storeWidget.data[i].storagePath+'" ' +
                            'widgetId="'+storeWidget.data[i].id+'" ' +
                            'parth="'+storeWidget.data[i].storage_path+'" ' +
                            'widget_name_show="'+storeWidget.data[i].widget_name_show+'" ' +
                            'title="'+storeWidget.data[i].widget_name+'" ' +
                            'typeId = "'+storeWidget.data[i].widget_type_id+'">' +
                            ''+storeWidget.data[i].widget_name+'</a>';

                    }else if(storeWidget.data[i].fileType=="3"){
                        str += '<a href="javascript:void(0)" class="media" ' +
                            'share="'+storeWidget.data[i].is_share+'"' +
                            ' storagePath="'+storeWidget.data[i].storagePath+'" ' +
                            'widgetId="'+storeWidget.data[i].id+'" ' +
                            'parth="'+storeWidget.data[i].storage_path+'" ' +
                            'widget_name_show="'+storeWidget.data[i].widget_name_show+'" ' +
                            'title="'+storeWidget.data[i].widget_name+'" ' +
                            'typeId = "'+storeWidget.data[i].widget_type_id+'">' +
                            ''+storeWidget.data[i].widget_name+'</a>';

                    }else if(storeWidget.data[i].fileType=="4"){
                        str += '<a href="javascript:void(0)" class="widget" ' +
                            'share="'+storeWidget.data[i].is_share+'"' +
                            ' storagePath="'+storeWidget.data[i].storagePath+'" ' +
                            'widgetId="'+storeWidget.data[i].id+'" ' +
                            'parth="'+storeWidget.data[i].storage_path+'" ' +
                            'widget_name_show="'+storeWidget.data[i].widget_name_show+'" ' +
                            'title="'+storeWidget.data[i].widget_name+'" ' +
                            'typeId = "'+storeWidget.data[i].widget_type_id+'">' +
                            ''+storeWidget.data[i].widget_name+'</a>';
                    }







				}
			}
			return str;
		}
	</script>
</html>
