layui.use(['layer', 'form', 'admin', 'ax', 'treeSelect', 'numinput'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var treeSelect = layui.treeSelect;

    var numinput = layui.numinput;
    numinput.init({ rightBtns: true });

    // 让当前iframe弹层高度适应
    admin.iframeAuto();

    //获取菜单信息
    var ajax = new $ax(Feng.ctxPath + "/dict/detail/" + Feng.getUrlParam("dictId"));
    ajax.type = "get";
    var result = ajax.start();
    form.val('dictForm', result.data);

    // 渲染父级菜单
    treeSelect.render({
        elem: '#parentName',
        data: Feng.ctxPath + "/dict/treeSelect",
        type: 'get',
        placeholder: '请选择父级菜单',
        search: true,
        click: function(d,a,b){
            $("#parentId").val(d.current.id);
        },
        success: function (d) {
            if (result.data.parentId) {
                treeSelect.checkNode('tree', result.data.parentId);
            }
        }
    });
    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/dict/edit", function (data) {
            Feng.success("修改成功！");

            //传给上个页面，刷新table用
            admin.putTempData('formOk', true);

            //关掉对话框
            admin.closeThisDialog();
        }, function (data) {
            Feng.error("修改失败！" + data.message)
        });
        ajax.set(data.field);
        ajax.start();
        return false;
    });
});