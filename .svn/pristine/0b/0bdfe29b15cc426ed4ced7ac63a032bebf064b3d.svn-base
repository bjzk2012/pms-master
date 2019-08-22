layui.use(['jquery', 'form', 'laydate', 'layedit', 'admin', 'ax', 'util'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var laydate = layui.laydate;
    var layedit = layui.layedit;
    var util = layui.util;
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

    $("#question_kaptcha").click(function(){
        $(this).attr("src", "/kaptcha?cmd=question&t="+new Date().getTime());
    });

    $("#send_authcode").click(function(){
        if ($(this).is("layui-btn-disabled")){
            return;
        }
        var ajax = new $ax(Feng.ctxPath + "/question/kaptcha", function (data) {
            Feng.success("验证码已发送！");
            admin.putTempData('formOk', true);
            admin.closeThisDialog();
            util.countdown(data.data.endTime, data.data.startTime, function(date, serverTime, timer) {
                if (date[3] != 0) {
                    $('#send_authcode').addClass("layui-btn-disabled");
                    $('#send_authcode').html('验证码已发送，' + date[3] + '秒后可再次获取');
                } else {
                    $('#send_authcode').removeClass("layui-btn-disabled");
                    $('#send_authcode').html('点击获取短信验证码');
                }


            });
        }, function (data) {
            Feng.error("验证码发送失败！" + data.message)
        });
        ajax.set({
            kaptcha: $("#kaptcha").val(),
            phone: $("#phone").val()
        });
        ajax.type = "get";
        ajax.start();
    });

    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        data.field.description = layedit.getContent(layeditIndex);
        var ajax = new $ax(Feng.ctxPath + "/question/feedback", function (data) {
            Feng.success("反馈成功！");
            admin.putTempData('formOk', true);
            admin.closeThisDialog();
        }, function (data) {
            Feng.error("反馈失败！" + data.message)
        });
        ajax.set(data.field);
        ajax.start();
        return false;
    });
});