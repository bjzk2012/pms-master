layui.use(['form', 'admin', 'ax', 'numinput'], function () {
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;

    var numinput = layui.numinput;
    numinput.init({ rightBtns: true });

    // 让当前iframe弹层高度适应
    admin.iframeAuto();

    //初始化角色的详情数据
    var ajax = new $ax(Feng.ctxPath + "/project/detail/" + Feng.getUrlParam("projectId"));
    ajax.type = "get";
    var result = ajax.start();
    form.val('projectForm',result.data);

    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/project/edit", function (data) {
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