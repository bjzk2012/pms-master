layui.use(['jquery', 'ax', 'form', 'numinput'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var numinput = layui.numinput;
    numinput.init({ rightBtns: true });
    form.on("submit(salt_submit)", function(data){
        var ajax = new $ax(Feng.ctxPath + "/security/salt", function(res){
            $("#salt_result").empty().text(res.data);
        });
        ajax.set(data.field);
        ajax.start();
        return false;
    });
    form.on("submit(md5_submit)", function(data){
        var ajax = new $ax(Feng.ctxPath + "/security/md5", function(res){
            $("#md5_result").empty().text(res.data);
        });
        ajax.set(data.field);
        ajax.start();
        return false;
    });
    form.on("submit(saltmd5_submit)", function(data){
        var ajax = new $ax(Feng.ctxPath + "/security/saltmd5", function(res){
            $("#saltmd5_result").empty().text(res.data);
        });
        ajax.set(data.field);
        ajax.start();
        return false;
    });
    form.on("submit(dese_submit)", function(data){
        var ajax = new $ax(Feng.ctxPath + "/security/dese", function(res){
            $("#des_result").empty().text(res.data);
        });
        ajax.set(data.field);
        ajax.start();
        return false;
    });
    form.on("submit(desd_submit)", function(data){
        var ajax = new $ax(Feng.ctxPath + "/security/desd", function(res){
            $("#des_result").empty().text(res.data);
        });
        ajax.set(data.field);
        ajax.start();
        return false;
    });
    form.on("submit(3dese_submit)", function(data){
        var ajax = new $ax(Feng.ctxPath + "/security/3dese", function(res){
            $("#3des_result").empty().text(res.data);
        });
        ajax.set(data.field);
        ajax.start();
        return false;
    });
    form.on("submit(3desd_submit)", function(data){
        var ajax = new $ax(Feng.ctxPath + "/security/3desd", function(res){
            $("#3des_result").empty().text(res.data);
        });
        ajax.set(data.field);
        ajax.start();
        return false;
    });
    form.on("submit(base64e_submit)", function(data){
        var ajax = new $ax(Feng.ctxPath + "/security/base64e", function(res){
            $("#base64_result").empty().text(res.data);
        });
        ajax.set(data.field);
        ajax.start();
        return false;
    });
    form.on("submit(base64d_submit)", function(data){
        var ajax = new $ax(Feng.ctxPath + "/security/base64d", function(res){
            $("#base64_result").empty().text(res.data);
        });
        ajax.set(data.field);
        ajax.start();
        return false;
    });
    form.on("submit(urle_submit)", function(data){
        var ajax = new $ax(Feng.ctxPath + "/security/urle", function(res){
            $("#url_result").empty().text(res.data);
        });
        ajax.set(data.field);
        ajax.start();
        return false;
    });
    form.on("submit(urld_submit)", function(data){
        var ajax = new $ax(Feng.ctxPath + "/security/urld", function(res){
            $("#url_result").empty().text(res.data);
        });
        ajax.set(data.field);
        ajax.start();
        return false;
    });
});
