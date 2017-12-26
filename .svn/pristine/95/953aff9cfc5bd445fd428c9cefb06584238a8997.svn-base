//将null转化为"",同时可截取字符串长度
function strFormat(str,len){
	if(str){
		if(len&&str.length>10){
			return str.substring(0,len)+"...";
		}else{
			return str;
		}		
	}else{
		return "";
	}
}
//判断是否是字母及下划线组成，首字母不得为下划线
function checkCode(str){
	if(""==str){
		return false;
	}
	for(var i=0;i<str.length;i++){ 
		var c = str.charAt(i);
		if(i==0){
			if((c=="_")&&(c<"a"||c>"z")&&(c<"A"||c>"Z")){
				return false;
			}
		}else{
			if((c!="_")&&(c<"a"||c>"z")&&(c<"A"||c>"Z")){
				return false;
			}
		}
	}
	return true;
}

function getLen(val) {
	var len = 0;
    for (var i = 0; i < val.length; i++) {
    	var patt = new RegExp(/[^\x00-\xff]/ig);
    	var a = val[i];
    	if (patt.test(a)){
    		len += 2;
    	}
    	else{
    		len += 1;
    	}
	}
	return len;
}

function fillForm(josn,formId){
	var inps = $("#"+formId).find("input");
	inps.each(function(i){
		inps.eq(i).val(josn[this.id]);
		inps.eq(i).attr("title",josn[this.id]);
	});
	var textareas = $("#"+formId).find("textarea");
	textareas.each(function(i){
		textareas.eq(i).val(josn[this.id]);
		textareas.eq(i).attr("title",josn[this.id]);
	});
}

function dateFormat(date){
	var year = date.getFullYear();
	var month = (date.getMonth()+1)<10?"0"+(date.getMonth()+1):(date.getMonth()+1);
	var day = date.getDate()<10?"0"+date.getDate():date.getDate();
	return year +"-"+month+"-"+day;
}

//格式化日期，返回日期格式
function strToDate(str){
	var list = str.split("-");
	return new Date(list[0],parseInt(list[1])-1,list[2]);
}

//ajax请求
function ajaxList(config) {
	var param = {
		pageSize: 20,
		pageNum: config.curr || 1
	}
	if(config.param){
		jQuery.extend(param, config.param);
	}
  $.ajax({
      url: config.url,  
      data: param,  
      dataType: "json",
      cache: false,
      success: function(res){
      	if(res.success){
      		config.fnSuccess(res);
  				//显示分页
					laypage({
						cont: 'pageContainer', //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
						pages: Math.ceil(res.count/20), //通过后台拿到的总页数
						curr: config.curr || 1, //当前页
						skin: '#428bca',
						jump: function(obj, first) { //触发分页后的回调
							if(!first) { //点击跳页触发函数自身，并传递当前页：obj.curr
								query(obj.curr);
							}
						}
					});
      	}else{
      		window.parent.layer.alert(config.errorMsg);
      	}
      },  
      error:function(jqXHR, textStatus, errorThrown){
      	if(jqXHR.readyState == 0 && jqXHR.responseText=="" && jqXHR.status == 0 && jqXHR.statusText == "error"){
			//用户快速切换或刷新导致的ajax被取消了
        }else{
        	window.parent.layer.alert(config.errorMsg);
        }
			}
  });  
}
//创建表格
function createTable(page,columns,check,singleSel){
	if(check){
		if(!singleSel){
			$("#checkAll").prop("checked",false);
		}
		$("#mainTable input").unbind("change");
		selIndexList = [];
	}
	$("#mainTable a").unbind("click");
	$("#mainTable").empty();
	pageNum = page.pageNum;
	var innerStr = "";
	for(var i = 0; i < page.data.length; i++) {
		var trCls = i % 2 == 0?"bg-tr-even":"";
		innerStr += "<tr class='" + trCls + "'>";
		if(check){
			innerStr += '<td><input type="checkbox" /></td>';
		}
		for(var j=0;j<columns.length;j++){
			if(columns[j].filter){
				if(columns[j].name){
					innerStr += "<td>"+columns[j].filter(page.data[i][columns[j].name],i)+"</td>";
				}else{
					innerStr += "<td>"+columns[j].filter(i)+"</td>";
				}
			}else{
				var str = (page.data[i][columns[j].name]||page.data[i][columns[j].name]==0)?page.data[i][columns[j].name]:"";
				innerStr += "<td>"+str+"</td>";
			}
		}
	}
	innerStr += "</tr>";
	$("#mainTable").append(innerStr);
	if(check&&singleSel){	//单选
		$("#mainTable").find("input").bind("change", function() {
			var id = parseInt($(this).attr("id").split("_")[1]);
			if($(this).prop("checked")){
				if(selIndexList.length>0){
					$("#check_"+selIndexList[0]).prop("checked",false);
					selIndexList = [];
				}
				selIndexList.push(id);
			}else{
				selIndexList = [];
			}
		});
	}else if(check){	//多选
		$("#mainTable").find("input").bind("change", function() {
			var id = parseInt($(this).attr("id").split("_")[1]);
			if($(this).prop("checked")){
				selIndexList.push(id);
			}else{
				for(var i = 0;i<selIndexList.length;i++){
					if(selIndexList[i]==id){
						selIndexList.splice(i,1);
						break;
					}
				}
			}
		});
	}
}
//主框架iframe刷新
function frameQuery(){
	window.parent.frames["mainFrame"].query(window.parent.frames["mainFrame"].pageNum);
}


/*----    下面这些是弹出层操作    ----*/
function frameWin(config){
	if(!config.url){
		window.parent.layer.alert("未配置url！");
		return;
	}
	if(!config.title){
		window.parent.layer.alert("未配置标题！");
		return;
	}
	var w = config.w?config.w+"px":"400px";
	var h = config.h?config.h+"px":"300px";
	var layerId = window.parent.layer.open({
		type: 2,
		title: config.title,
		area: [w,h],
		offset: 'auto',
		content: [config.url],
		end: function() {
			window.parent.winList.pop();
		}
	});
	window.parent.winList.push(layerId);
}

function closeTopWin(){
	window.parent.layer.close(window.parent.winList[window.parent.winList.length-1]);
}

function confirm(config){
	if(!config.msg){
		window.parent.layer.alert("未配置询问信息！");
		return;
	}
	window.parent.layer.confirm(config.msg, {
		  btn: config.btn //按钮
	}, function(){
		config.yesFn();
	}, function(){
		config.noFn();
	});
}

function layerAlert(msg,callback){
	if(callback){
		window.parent.layer.alert(msg,function(i){
			window.parent.layer.close(i);
			callback();
		});
	}else{
		window.parent.layer.alert(msg);
	}
}
