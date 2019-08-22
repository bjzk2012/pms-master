layui.use(['form', 'laydate', 'layedit', 'admin', 'ax'], function () {
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var laydate = layui.laydate;
    var layedit = layui.layedit;
    laydate.render({
        elem:'#time',
        type: 'datetime'
    });
    var layeditIndex = layedit.build('description',{
        uploadImage: {
            url: '/system/upload'
        }
    });
    admin.iframeAuto();
    // 添加表单验证方法
    form.verify({
        phone: [/^0?[1][358][0-9]{9}$/, '请输入正确的手机号码'],
    });

    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        data.field.description = layedit.getContent(layeditIndex);
        var ajax = new $ax(Feng.ctxPath + "/question/add", function (data) {
            Feng.success("添加成功！");
            admin.putTempData('formOk', true);
            admin.closeThisDialog();
        }, function (data) {
            Feng.error("添加失败！" + data.message)
        });
        ajax.set(data.field);
        ajax.start();
        return false;
    });
});