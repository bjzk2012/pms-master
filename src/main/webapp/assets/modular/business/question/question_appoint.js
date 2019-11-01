layui.use(['form', 'admin', 'layedit', 'ax'], function () {
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var layedit = layui.layedit;
    var layeditIndex = layedit.build('remark',{
        uploadImage: {
            url: '/system/upload'
        }
    });

    form.render();
    admin.iframeAuto();
    var ajax = new $ax(Feng.ctxPath + "/question/detail/" + Feng.getUrlParam("questionId"));
    ajax.type = "get";
    var result = ajax.start();
    form.val('questionForm', result.data);
    try {
        layedit.setContent(layeditIndex, result.data.remark, false);
    } catch (e) { }

    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        data.field.remark = layedit.getContent(layeditIndex);
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
