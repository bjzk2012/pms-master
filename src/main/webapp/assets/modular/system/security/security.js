layui.use(['jquery', 'ax', 'form'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    form.on("submit(saltSubmit)", function(){
        var ajax = new $ax(Feng.ctxPath + "/security/salt", function(res){
            $("#salt_result").text(res.data);
        });
        ajax.start();
        return false;
    });
    form.on("submit(md5Submit)", function(data){
        var ajax = new $ax(Feng.ctxPath + "/security/md5", function(res){
            $("#md5_result").text(res.data);
        });
        ajax.set(data.field);
        ajax.start();
        return false;
    });
});