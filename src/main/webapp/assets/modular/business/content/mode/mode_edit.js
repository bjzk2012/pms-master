layui.use(['form', 'admin', 'ax', 'numinput', 'upload', 'jquery'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var upload = layui.upload;

    var numinput = layui.numinput;
    numinput.init({ rightBtns: true });

    form.render();
    admin.iframeAuto();

    //初始化角色的详情数据
    var ajax = new $ax(Feng.ctxPath + "/mode/detail/" + Feng.getUrlParam("modeId"));
    ajax.type = "get";
    var result = ajax.start();
    if (!result.data.picture) {
        result.data.picture = "/assets/common/images/default.png"
    }
    form.val('modeForm',result.data);
    $('#picture_img').attr('src', result.data.picture);
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
        var ajax = new $ax(Feng.ctxPath + "/mode/edit", function (data) {
            Feng.success("修改成功!");

            //传给上个页面，刷新table用
            admin.putTempData('formOk', true);

            //关掉对话框
            admin.closeThisDialog();
        }, function (data) {
            Feng.error("修改失败!" + data.message + "!");
        });
        ajax.set(data.field);
        ajax.start();
        return false;
    });
});