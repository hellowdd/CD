$(function () {

    var fileMd5;
    //监听分块上传过程中的三个时间点
    WebUploader.Uploader.register({
        "before-send-file": "beforeSendFile",
        "before-send": "beforeSend",
        "after-send-file": "afterSendFile",
    }, {
        //时间点1：所有分块进行上传之前调用此函数
        beforeSendFile: function (file) {
            console.log(file);
            var deferred = WebUploader.Deferred();
            //1、计算文件的唯一标记，用于断点续传
            (new WebUploader.Uploader()).md5File(file, 0, 10 * 1024 * 1024)
                .progress(function (percentage) {
                    console.log("正在读取文件");
                })
                .then(function (val) {
                    fileMd5 = val;
                    console.log("成功获取文件信息...");
                    //获取文件信息后进入下一步
                    deferred.resolve();
                });
            return deferred.promise();
        },
        //时间点2：如果有分块上传，则每个分块上传之前调用此函数
        beforeSend: function (block) {
            var deferred = WebUploader.Deferred();

            $.ajax({
                type: "POST",
                url: "http://127.0.0.1:8080/CD/api/file/checkFile",
                data: {
                    //文件唯一标记
                    fileMd5: fileMd5,
                    //当前分块下标
                    chunk: block.chunk,
                    //当前分块大小
                    chunkSize: block.end - block.start
                },
                dataType: "json",
                success: function (response) {
                    if (response.ifExist) {
                        //分块存在，跳过
                        deferred.reject();
                    } else {
                        //分块不存在或不完整，重新发送该分块内容
                        deferred.resolve();
                    }
                }
            });

            this.owner.options.formData.fileMd5 = fileMd5;
            deferred.resolve();
            return deferred.promise();
        },
        //时间点3：所有分块上传成功后调用此函数
        afterSendFile: function () {
            //如果分块上传成功，则通知后台合并分块
            $.ajax({
                type: "POST",
                url: "http://127.0.0.1:8080/CD/api/file/mergeChunks",
                data: {
                    fileMd5: fileMd5,
                },
                success: function (response) {
                    console.log("上传成功");
                }
            });
        }
    });

    var uploader = WebUploader.create({
        // swf文件路径
        swf: '/js/Uploader.swf',
        // 文件接收服务端。
        server: 'http://127.0.0.1:8080/CD/api/file/uploadVideo',
        // 选择文件的按钮。可选。
        // 内部根据当前运行是创建，可能是input元素，也可能是flash.id就是文件id
        pick: {
            id: '#picker', //这个id是你要点击上传文件按钮的外层div的id
            multiple : false //是否可以批量上传，true可以同时选择多个文件
        },
        resize: true,
        auto: false,
        //开启分片上传
        chunked: true,
        chunkSize: 10 * 1024 * 1024
    });

    // 当有文件被添加进队列的时候
    uploader.on('fileQueued', function (file) {
        console.log(file.name + "等待上传...");
        /* $('#item1').empty();
         $('#item1').html('<div id="' + file.id + '" class="item">' +
             '<a class="upbtn" id="btn" onclick="stop()">[取消上传]</a>' +
             '<p class="info">' + file.name + '</p>' +
             '<p class="state">等待上传...</p></div>'
         );*/
    });

    // 文件上传过程中创建进度条实时显示。
    uploader.on('uploadProgress', function (file, percentage) {
        //console.log(file.name + '上传中 ' + Math.round(percentage * 100) + '%');100
        /* $('#item1').find('p.state').text('上传中 ' + Math.round(percentage * 100) + '%');*/
    });

    uploader.on('uploadSuccess', function (file) {
        console.log(file.name + '已上传');
        // $('#' + file.id).find('p.state').text('已上传');
    });

    uploader.on('uploadError', function (file) {
        console.log(file.name + '上传出错');
        //$('#' + file.id).find('p.state').text('上传出错');
    });

    uploader.on('uploadComplete', function (file) {
        // $('#' + file.id).find('.progress').fadeOut();
    });

    function start() {
        uploader.upload();

    }

    function stop() {
        uploader.stop(true);
        $('#btn').attr("onclick", "start()");
        $('#btn').text("继续上传");
    }

    $("#ctlBtn").on("click", function () {
        start();
    });

});