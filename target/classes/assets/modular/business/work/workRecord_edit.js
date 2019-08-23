layui.use(['form', 'admin', 'ax'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;

    form.render();
    admin.iframeAuto();
    var ajax = new $ax(Feng.ctxPath + "/work/workRecord/detail/" + Feng.getUrlParam("workRecordId"));
    ajax.type = "get";
    var result = ajax.start();
    form.val('workRecordForm',result.data);
    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/work/workRecord/edit", function (data) {
            Feng.success("修改成功！");

            //传给上个页面，刷新table用
            admin.putTempData('formOk', true);

            //关掉对话框
            admin.closeThisDialog();
        }, function (data) {
            Feng.error("修改失败！" + data.message)
        });
        ajax.set(data.field);
        ajax.start();
        return false;
    });
});