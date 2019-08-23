layui.use(['form', 'upload', 'element', 'ax', 'laydate', 'cropper', 'croppers'], function () {
    var $ = layui.jquery;
    var form = layui.form;
    var upload = layui.upload;
    var element = layui.element;
    var $ax = layui.ax;
    var laydate = layui.laydate;
    var croppers = layui.croppers;

    form.render()
    //渲染时间选择框
    laydate.render({
        elem: '#birthday'
    });

    //表单提交事件
    form.on('submit(userInfoSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/center/user_info", function (data) {
            Feng.success("更新基本信息成功!");
            window.location.reload();
        }, function (data) {
            Feng.error("更新基本信息失败!" + data.message + "!");
        });
        ajax.set(data.field);
        ajax.start();
        return false;
    });

    $("[_o]").click(function(){
        Feng.error("暂未开通，敬请期待！")
    });

    croppers.render({
        elem: '#imgHead',
        saveW: 150,
        saveH: 150,
        mark: 1 / 1,
        area: '900px',
        url: Feng.ctxPath + '/system/upload',
        done: function (url) {
            $("#imgHead img").attr("src", url);
            $("#avatar").val(url);
            var ajax = new $ax(Feng.ctxPath + "/center/user_avatar", function (d) {
                Feng.success("修改图像成功!");
                top.location.reload();
            }, function (data) {
                Feng.error("修改图像失败!" + data.message + "!");
            });
            ajax.set({avatar: url});
            ajax.start();
        }
    });
});