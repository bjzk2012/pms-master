layui.use(['layer', 'form', 'admin', 'ax', 'treeSelect', 'numinput'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var layer = layui.layer;
    var treeSelect = layui.treeSelect;
    var numinput = layui.numinput;
    numinput.init({ rightBtns: true });

    // 让当前iframe弹层高度适应
    admin.iframeAuto();

    //初始化角色的详情数据
    var ajax = new $ax(Feng.ctxPath + "/role/detail/" + Feng.getUrlParam("roleId"));
    ajax.type = "get";
    var result = ajax.start();
    form.val('roleForm',result.data);

    // 渲染父级菜单
    treeSelect.render({
        elem: '#menuName',
        data: Feng.ctxPath + "/menu/treeSelect",
        type: 'get',
        placeholder: '请选择权限菜单',
        search: true,
        checkbox: true,
        click: function(d){
        },
        check: function(d, e){
            $("#menuId").val($.map(d, function(n){return n.id}).join(","));
        },
        success: function (d) {
            if (result.data.menuId) {
                treeSelect.checkNodes('tree', result.data.menuId.split(","));
            }
        }
    });

    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        if (!data.field.menuId) {
            layer.msg("权限菜单未选择", {icon: 5, anim: 6});
            return false;
        }
        var ajax = new $ax(Feng.ctxPath + "/role/edit", function (data) {
            Feng.success("修改成功!");

            //传给上个页面，刷新table用
            admin.putTempData('formOk', true);

            //关掉对话框
            admin.closeThisDialog();
        }, function (data) {
            Feng.error("修改失败!" + data.message + "!");
        });
        ajax.set(data.field);
        ajax.start();
        return false;
    });
});