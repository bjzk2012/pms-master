layui.use(['form', 'admin', 'ax'], function () {
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;

    // 让当前iframe弹层高度适应
    admin.iframeAuto();

    //初始化角色的详情数据
    var ajax = new $ax(Feng.ctxPath + "/key/detail/" + Feng.getUrlParam("keyId"));
    ajax.type = "get";
    var result = ajax.start();
    form.val('keyForm',result.data);

    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var func = function(){
            var ajax = new $ax(Feng.ctxPath + "/key/edit", function (data) {
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
        };
        if (!data.field.password) {
            Feng.confirm("未输入密码则不修改密码，请确认是否提交？", function(r){
                func();
            });
        } else {
            func();
        }
        return false;
    });
});