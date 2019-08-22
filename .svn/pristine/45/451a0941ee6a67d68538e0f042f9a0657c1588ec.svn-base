layui.use(['layer', 'form', 'admin', 'ax'], function () {
    var $ = layui.jquery;
    var layer = layui.layer;
    var form = layui.form;
    var admin = layui.admin;
    var $ax = layui.ax;

    // 让当前iframe弹层高度适应
    admin.iframeAuto();

    // 监听提交
    form.on('submit(btnSubmit)', function (data) {
        console.log(data)
        var ajax = new $ax(Feng.ctxPath + "/center/password", function (data) {
            Feng.success("修改成功!");
            admin.closeThisDialog();
        }, function (data) {
            Feng.error("修改失败!" + data.message + "!");
        });
        ajax.set(data.field);
        ajax.start();
        return false;
    });

    // 添加表单验证方法
    form.verify({
        psw: [/^[\S]{6,12}$/, '密码必须6到12位，且不能出现空格'],
        repsw: function (t) {
            if (t !== $('#passwordForm input[name=newPassword]').val()) {
                return '两次密码输入不一致';
            }
        }
    });
});