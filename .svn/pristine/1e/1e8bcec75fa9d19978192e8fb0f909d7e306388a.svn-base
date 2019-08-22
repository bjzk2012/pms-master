layui.use(['treeSelect', 'jquery', 'ztree', 'form', 'admin', 'ax', 'numinput'], function () {
    var $ax = layui.ax;
    var $ = layui.jquery;
    var form = layui.form;
    var admin = layui.admin;
    var treeSelect = layui.treeSelect;

    var numinput = layui.numinput;
    numinput.init({ rightBtns: true });

    // 让当前iframe弹层高度适应
    admin.iframeAuto();

    //获取部门信息
    var ajax = new $ax(Feng.ctxPath + "/dept/detail/" + Feng.getUrlParam("deptId"));
    ajax.type = "get";
    var result = ajax.start();
    form.val('deptForm', result);

    // 渲染父级部门信息
    treeSelect.render({
        elem: '#pName',
        data: Feng.ctxPath + "/dept/treeSelect",
        type: 'get',
        placeholder: '请选择上级部门',
        search: true,
        click: function(d){
            $("#pid").val(d.current.id);
        },
        success: function (d) {
            treeSelect.checkNode('tree', result.parentId);
        }
    });
    // 添加表单验证方法
    form.verify({
        simpleName: [/^.{2,20}$/, '简称长度必须为2到20位'],
        fullName: [/^.{2,255}$/, '全称长度必须为2到255位']
    });

    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/dept/edit", function (data) {
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