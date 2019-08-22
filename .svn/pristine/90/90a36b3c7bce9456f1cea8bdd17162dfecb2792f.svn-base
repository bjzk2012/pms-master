layui.use(['form', 'admin', 'ax', 'numinput', 'upload', 'layedit', 'formSelects'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var upload = layui.upload;
    var layedit = layui.layedit;

    var layeditIndex = layedit.build('content',{
        uploadImage: {
            url: '/system/upload'
        }
    });
    form.render();
    $("#userId-item").hide();
    $("#roleId-item").hide();
    admin.iframeAuto();
    form.on('select(totype)', function(data){
        switch(data.value){
            case "1":
                $("#userId-item").hide();
                $("#roleId-item").hide();
                break;
            case "2":
                $("#userId-item").hide();
                $("#roleId-item").show();
                break;
            case "3":
                $("#userId-item").show();
                $("#roleId-item").hide();
                break;
        }
        admin.iframeAuto();
    });

    var uploadInst = upload.render({
        elem: '#picture_btn',
        url: Feng.ctxPath + '/system/upload',
        accept: 'images',
        before: function(obj){
            obj.preview(function(index, file, r){
                $('#picture_img').attr('src', r); //图片链接（base64）
            });
        },
        done: function(r){
            $("#picture").val(r.data.src);
        },
        error: function(){
            //演示失败状态，并实现重传
            var demoText = $('#picture_text');
            demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
            demoText.find('.demo-reload').on('click', function(){
                uploadInst.upload();
            });
        }
    });

    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        data.field.content = layedit.getContent(layeditIndex);
        var ajax = new $ax(Feng.ctxPath + "/notice/add", function (data) {
            Feng.success("添加成功！");

            //传给上个页面，刷新table用
            admin.putTempData('formOk', true);

            //关掉对话框
            admin.closeThisDialog();
        }, function (data) {
            Feng.error("添加失败！" + data.message)
        });
        ajax.set(data.field);
        ajax.start();
        return false;
    });
});