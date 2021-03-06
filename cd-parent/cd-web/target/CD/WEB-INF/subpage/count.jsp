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
			.fileInfo{
				display: inline-block;
				width: 45%;
				padding-bottom: 15px;
			}
			.fileInfo span{
				color: red;
				display: inline-block;
				margin: 0 5px;
				font-size: 15px;
			}
			#nums{
				padding: 2px;
				border-radius: 2px;
				width: 100px;
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
			<!-- Nav tabs -->
			<%--<ul class="nav nav-tabs" role="tablist" id="tabSel">
				<li role="presentation" class="active"><a href="#byCompany1" aria-controls="byCompany1" role="tab" data-toggle="tab" aria-expanded="true">按企业提交控件总量</a></li>
				<li role="presentation" class=""><a href="#byCompany2" aria-controls="byCompany2" role="tab" data-toggle="tab" aria-expanded="false">按企业提交控件使用次数</a></li>
			</ul>--%>
			<!-- Tab panes -->
			<div class="tab-content">
				<div role="tabpanel" class="tab-pane active" id="byCompany1">
					<div class="fileInfo">目前总文件总量为<span id="allCount"></span>个</div>
					<div class="fileInfo">目前总文件大小为<span id="allCountMB"></span></div>
					<div class="fileInfo">云盘占用比:&nbsp;<select id="nums">
						<option value="0.5">50%</option>
						<option value="0.8">80%</option>
						<option value="0.9">90%</option>
						
						</select></div>
					<div  class="fileInfo">云盘占用<span id="coo"></span>以上用户量的数目为<span id="countn"></span> </div>

					<table class="table">
						<thead>
							<tr>
								<th>上传文件类型</th>
								<th>上传文件数量</th>
								<th>上传文件大小</th>
							</tr>
						</thead>
						<tbody id='mainTable'></tbody>
					</table>
					<div style="float:right; height: 40px;" id="page_byCompany1"></div>
				</div>


			</div>
		</div>
	</body>
	<script type="text/javascript">

		$(document).on("ready",function(){
			//获取数据
		  query();createChart(0.5);
            $("#coo").text(toPercent(0.5))
		  $("#nums").change(function () {
		      $("#coo").text(toPercent($(this).val()))
              createChart($(this).val());
          })

		  
		 $('#tabSel').find("a").click(function (e) {
			  e.preventDefault();
			  $(this).tab('show');
			});
		});
        function toPercent(data){
            var strData = parseFloat(data)*100;
            var ret = strData.toString()+"%";
            return ret;
        }
		//查询控件列表
		function query() {

			$.ajax({
		    dataType: "json",
		    //type: "POST",
		    url: '${pageContext.request.contextPath}/manager/Statistics/queryCountWidget',
				data:"",
		    cache: false,
		    success: function(res) {
		      if(res.success){
					$("#allCount").text(res.data)
		      }else{
		      	window.parent.layer.alert("获取文件总量失败！"+res.message);
		      }
		    },
				error: function(jqXHR,textStatus,errorThrown) {
					if(jqXHR.readyState == 0 && jqXHR.responseText == "" && XHR.status == 0 && jqXHR.statusText == "error"){
						
					}else{
						window.parent.layer.alert("获取文件总量失败！");
					}
        }
		  });
            $.ajax({
                dataType: "json",
                //type: "POST",
                url: '${pageContext.request.contextPath}/manager/Statistics/queryWidgetSize',
                data:"",
                cache: false,
                success: function(res) {
                    if(res.success){
                        $("#allCountMB").text(getSize(res.data))
                    }else{
                        window.parent.layer.alert("获取总文件大小失败！"+res.message);
                    }
                },
                error: function(jqXHR,textStatus,errorThrown) {
                    if(jqXHR.readyState == 0 && jqXHR.responseText == "" && XHR.status == 0 && jqXHR.statusText == "error"){

                    }else{
                        window.parent.layer.alert("获取总文件大小失败！");
                    }
                }
            });


            $.ajax({
                dataType: "json",
                //type: "POST",
                url: '${pageContext.request.contextPath}/manager/Statistics/queryWidgetType',
                data:"",
                cache: false,
                success: function(res) {
                    if(res.success){
                        $("#mainTable").empty()
                        var str= '';

                        for(var i = 0 ;i <res.data.length;i++){


                            str += '<tr>' +
                                '	<td>'+res.data[i].widget_extension+'</td>' +
                                '	<td>'+res.data[i].count+'</td>' +
                                '	<td>'+getSize(res.data[i].widgetSize)+'</td>' +
                                '</tr>';
                        }

                        $("#mainTable").append(str)
                    }else{
                        window.parent.layer.alert("获取统计列表失败！"+res.message);
                    }
                },
                error: function(jqXHR,textStatus,errorThrown) {
                    if(jqXHR.readyState == 0 && jqXHR.responseText == "" && XHR.status == 0 && jqXHR.statusText == "error"){

                    }else{
                        window.parent.layer.alert("获取统计列表失败！");
                    }
                }
            });
		}
			

		function createChart(d){
		$.ajax({
        dataType: "json",
        //type: "POST",
        url: "${pageContext.request.contextPath}/manager/Statistics/selectUserCount",
		data:{
            d:d
        },
        cache: false,
        success: function(res) {
        	if(res.success){
				$("#countn").text(res.data);
        	}else{
        		window.parent.layer.alert('获取信息失败！');
        	}
        },
        error: function() {
          window.parent.layer.alert('获取信息失败！');
        }
      });
		}
        function getSize(size) {
		    if(size == null){
                size = 0;
			}
            var m = Math.floor(size / (1024 * 1024));
            var k = Math.floor((size - m * 1024 * 1024) / 1024);
            var b;
            if (m > 0) {
                return (size / (1024 * 1024)).toFixed(2) + "MB";
            } else if (k > 0) {
                return (size / 1024).toFixed(2) + "KB";
            } else {
                return size + "B";
            }
        }
	</script>
</html>
