layui.use(['form', 'admin', 'ax', 'numinput'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;

    var numinput = layui.numinput;
    numinput.init({rightBtns: true});

    var ajax = new $ax(Feng.ctxPath + "/modefield/detail/" + Feng.getUrlParam("modefieldId"));
    ajax.type = "get";
    var result = ajax.start();
    result.data.custom = result.data.custom + "";
    form.val('modefieldForm', result.data);
    $(".attr").hide();
    $("." + result.data.type).show();

    if (result.data.custom == "false"){
        $(".custom").hide();
        $(".dict").show();
    }
    form.render("select");
    form.render();

    admin.iframeAuto();

    form.on('select(type)', function (obj) {
        $(".attr").hide();
        $("." + obj.value).show();
        form.val("modefieldForm", {custom: "true"});
        admin.iframeAuto();
    });
    form.on('radio(radioCustom)', function (obj) {
        if (obj.value == "true") {
            $(".custom").show();
            $(".dict").hide();
        } else {
            $(".custom").hide();
            $(".dict").show();
        }
        admin.iframeAuto();
    });

    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/modefield/edit", function (data) {
            Feng.success("编辑成功！");

            //传给上个页面，刷新table用
            admin.putTempData('formOk', true);

            //关掉对话框
            admin.closeThisDialog();
        }, function (data) {
            Feng.error("编辑失败！" + data.message)
        });
        ajax.set(data.field);
        ajax.start();
        return false;
    });
});