layui.use(['form', 'admin', 'layedit', 'ax'], function () {
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var layedit = layui.layedit;
    var layeditIndex = layedit.build('description',{
        uploadImage: {
            url: '/system/upload'
        }
    });
    admin.iframeAuto();

    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        data.field.description = layedit.getContent(layeditIndex);
        var ajax = new $ax(Feng.ctxPath + "/question/appoint", function (data) {
            Feng.success("指派成功！");
            admin.putTempData('formOk', true);
            admin.closeThisDialog();
        }, function (data) {
            Feng.error("指派失败！" + data.message)
        });
        ajax.set(data.field);
        ajax.start();
        return false;
    });
});