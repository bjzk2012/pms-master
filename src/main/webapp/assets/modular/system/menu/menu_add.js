layui.use(['form', 'admin', 'ax', 'treeSelect', 'iconPicker', 'numinput'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var treeSelect = layui.treeSelect;
    var iconPicker = layui.iconPicker;
    form.render();
    iconPicker.render({
        elem: '#iconPicker',
        type: 'fontClass',
        search: true,
        page: true,
        click: function (data) {
            $("#icon").val("layui-icon " + data.icon);
        }
    });
    iconPicker.checkIcon("iconPicker", "");

    var numinput = layui.numinput;
    numinput.init({ rightBtns: true });

    form.render();
    admin.iframeAuto();

    // 渲染父级菜单
    treeSelect.render({
        elem: '#parentName',
        data: Feng.ctxPath + "/menu/treeSelect",
        type: 'get',
        placeholder: '请选择父级菜单',
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
            layer.msg("父级菜单未选择", {icon: 5, anim: 6});
            return false;
        }
        var ajax = new $ax(Feng.ctxPath + "/menu/add", function (data) {
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