layui.use(['form', 'admin', 'ax', 'treeSelect', 'numinput'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var treeSelect = layui.treeSelect;

    var numinput = layui.numinput;
    numinput.init({ rightBtns: true });

    // 让当前iframe弹层高度适应
    admin.iframeAuto();

    // 渲染父级字典
    treeSelect.render({
        elem: '#parentName',
        data: Feng.ctxPath + "/dict/treeSelect",
        type: 'get',
        placeholder: '请选择父级字典',
        search: true,
        click: function(d,a,b){
            $("#parentId").val(d.current.id);
        },
        success: function (d) {
        }
    });

    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        if (!data.field.parentId) {
            layer.msg("父级字典未选择", {icon: 5, anim: 6});
            return false;
        }
        var ajax = new $ax(Feng.ctxPath + "/dict/add", function (data) {
            Feng.success("添加成功！");

            //传给上个页面，刷新table用
            admin.putTempData('formOk', true);

            //关掉对话框
            admin.closeThisDialog();
        }, function (data) {
            Feng.error("添加失败！" + data.message)
        });
        ajax.set(data.field);
        ajax.start();
        return false;
    });
});