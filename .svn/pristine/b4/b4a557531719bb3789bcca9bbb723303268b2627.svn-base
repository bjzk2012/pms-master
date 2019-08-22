layui.use(['form', 'admin', 'laydate', 'ax', 'treeSelect', 'jquery', 'formSelects'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var laydate = layui.laydate;
    var treeSelect = layui.treeSelect;
    form.render();
    admin.iframeAuto();

    // 渲染部门信息
    treeSelect.render({
        elem: '#deptName',
        data: Feng.ctxPath + "/dept/treeSelect",
        type: 'get',
        placeholder: '请选择部门',
        search: true,
        click: function(d){
            $("#deptId").val(d.current.id);
        },
        success: function (d) {
        }
    });

    // 添加表单验证方法
    form.verify({
        act: [/^[\w]{5,36}$/, '用户账号必须5到36位，只能是单词字符（字母，数字，下划线，中横线）'],
        psw: [/^[\S]{6,12}$/, '密码必须6到12位，且不能出现空格'],
        phone: [/(^\.{0}$)|(^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$)|(^0?[1][358][0-9]{9}$)/, "电话必须是固定电话或手机号码"],
        repsw: function (value) {
            if (value !== $('#userForm input[name=password]').val()) {
                return '两次密码输入不一致';
            }
        }
    });

    // 渲染时间选择框
    laydate.render({
        elem: '#birthday'
    });

    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        if (!data.field.deptId) {
            layer.msg("部门未选择", {icon: 5, anim: 6});
            return false;
        }
        if (!data.field.roleId) {
            layer.msg("角色未选择", {icon: 5, anim: 6});
            return false;
        }
        var ajax = new $ax(Feng.ctxPath + "/mgr/add", function (data) {
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