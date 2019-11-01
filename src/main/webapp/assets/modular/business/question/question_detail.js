layui.use(['jquery', 'layedit'], function () {
    var $ = layui.jquery;
    var layedit = layui.layedit;
    layedit.build('remark',{
        hideTool: [ '|', 'link', 'unlink', 'face', 'image', 'help']
    });
    setTimeout(function () {
        console.log($("iframe[textarea='remark']").contents().find("body").attr("contenteditable", "false"))
    }, 300);
});
