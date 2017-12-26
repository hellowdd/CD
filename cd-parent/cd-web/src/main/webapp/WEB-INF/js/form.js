//定义好的正则表达式
var regObj = {
	version:/^([\d]{1,}\.){1,}\d{1,}$/,
	num:/^[\d]{1,}$/
};

//绑定事件，失去焦点检验
function autoCheckForm(id){
	var inputs = $("#"+id).find("input");
	var textareas = $("#"+id).find("textarea");
	var select = $("#"+id).find("select");
	inputs.on("blur",function(){
		if($(this).attr("type") != "file"){
			$(this).val($.trim($(this).val()));
			checkItem($(this));
		}
	});
	textareas.on("blur",function(){
		checkItem($(this));
	});
	select.on("blur",function(){
		checkItem($(this));
	});
}

//提交时全部检验
var canSubmit = true;
function checkForm(id){
	canSubmit = true;
	var inputs = $("#"+id).find("input");
	var textareas = $("#"+id).find("textarea");
	var select = $("#"+id).find("select");
	for(var i=0;i<inputs.length;i++){
		if(inputs.eq(i).attr("name") && inputs.eq(i).attr("type") != "file"){
			inputs.eq(i).val($.trim(inputs.eq(i).val()));
			checkItem(inputs.eq(i))
		}
	}
	for(var i=0;i<textareas.length;i++){
		if(textareas.eq(i).attr("name")){
			checkItem(textareas.eq(i))
		}
	}
	for(var i=0;i<select.length;i++){
		if(select.eq(i).attr("name")){
			checkItem(select.eq(i))
		}
	}
}

function checkItem(jqObj){
	if(jqObj.attr("len")){
		var len = parseInt(jqObj.attr("len"));
		if(jqObj.attr("requried")&&$.trim(jqObj.val())==""){
			if("blank" != jqObj.attr("errortype")){
				jqObj.attr("errortype","blank");
				jqObj.parent().addClass("has-error");
				jqObj.tooltip("destroy");
				jqObj.tooltip({
					title:"该项为必填项",
					animation:false,
					placement:"bottom"
				});
				jqObj.tooltip("show");
			}
			canSubmit = false;
			return;
		}
		if(jqObj.val().replace(/[\u4e00-\u9fa5]/g,"aa").length>len){
			if("outLen" != jqObj.attr("errortype")){
				jqObj.attr("errortype","outLen");
				jqObj.parent().addClass("has-error");
				jqObj.tooltip("destroy");
				jqObj.tooltip({
					title:"长度超过限制,最多"+len+"个字符",
					animation:false,
					placement:"bottom"
				});
				jqObj.tooltip("show");
			}
			canSubmit = false;
			return;
		}
	}
	if(jqObj.attr("reg")){
		if(jqObj.val()!=""&&jqObj.val().match(regObj[jqObj.attr("reg")])==null){
			if("regError" != jqObj.attr("errortype")){
				jqObj.attr("errortype","regError");
				jqObj.parent().addClass("has-error");
				jqObj.tooltip("destroy");
				jqObj.tooltip({
					title:jqObj.attr("tip"),
					animation:false,
					placement:"bottom"
				});
				jqObj.tooltip("show");
			}
			canSubmit = false;
			return;
		}
	}
	jqObj.attr("errortype","");
	jqObj.parent().removeClass("has-error");
	jqObj.tooltip("destroy");
}
