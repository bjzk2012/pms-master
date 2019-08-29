
layui.use(['admin'], function () {
    var admin = layui.admin;
    //由于字段内容均通过花括号形式在html代码中填充（注意html中name必须和字段名称相同），
    // 此处只需要将iframe弹框大小重新自动设置
    admin.iframeAuto();
});