<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title></title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
		<style type="text/css">
			html,body{
				height:100%;
				overflow: hidden;
			}
			.container{
				width:80%;
				height: 70%;
			}
			.tool{
				margin: 20px;
			}
		</style>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js" ></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js" ></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/highchart/highcharts.js" ></script>
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
		<div class="tool">
			<button class="btn btn-default" id="spaceApply"><span class="glyphicon glyphicon-open-file"></span>空间申请</button>
		</div>
		<div id="container" class="container"></div>
	</body>
	<script type="text/javascript">
		var result;
		$(document).on("ready",function(){
			$.ajax({
        dataType: "json",
        //type: "POST",
        url: "${pageContext.request.contextPath}/manager/space/getSpaceInfo",
        cache: false,
        success: function(res) {
        	if(res.success){
        		result = res.data;
        		if(result == "无空间信息"){
           			$('#container').html("<h2 style='text-align: center;'>尚未分配空间，请先申请空间！</h2>");
           			return;
        		}
            //var left = res.data.spaceTotalByte - res.data.spaceRestByte - res.data.spaceUseByte;
            var use = res.data.spaceUseByte;
            var nouse = res.data.spaceRestByte;
            
            $('#container').highcharts({
              chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false
              },
              title: {
                text: '空间使用情况'
              },
              plotOptions: {
                pie: {
                  cursor: 'pointer',
                  dataLabels: {
                    enabled: true,
                    format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                    style: {
                      color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                    }
                  }
                }
              },
              colors:[
                "#006699",
                "#468C00"
              ],
              credits: {
         	      enabled: false
            	},
              series: [{
                type: 'pie',
                name: '空间使用情况',
                data: [
                  ['已使用',   use],
                  ['未使用', nouse]
                ]
              }],
              tooltip : {
      			    formatter : function() {
      		        return "<b>空间使用情况</b><br />" + this.point.name+":"+getSizeStr(this.point.name);
      			    }
      				}
          	});
        	}else{
        		window.parent.layer.alert('获取信息失败！');
        	}
        },
				error: function(jqXHR,textStatus,errorThrown) {
					if(jqXHR.readyState == 0 && jqXHR.responseText == "" && XHR.status == 0 && jqXHR.statusText == "error"){
						
					}else{
						window.parent.layer.alert("获取信息失败！");
					}
        }
      });
			

          	
     	$("#spaceApply").on("click",function(){
     		window.parent.layer.open({
 				  type: 2,
 				  title: '新增控件',
 				  shade: 0.3,
 				  area: ['500px', '360px'],
 				  content: '${pageContext.request.contextPath}/manager/view/spaceApply', //iframe的url
 				  success:function(layero, index){
				    window.parent.winList.push(index);
 				  }
 				}); 
     	});
     	
     	function getSizeStr(name){
     		if(name == "已使用"){
     			return result.spaceUse;
     		}else{
     			return result.spaceRest;
     		}
     	}
     	
		});
	</script>
</html>
