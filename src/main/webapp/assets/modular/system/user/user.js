layui.use(['form', 'table', 'laydate', 'admin', 'jquery'], function () {
    var form = layui.form;
    var table = layui.table;
    var $ = layui.jquery;
    var laydate = layui.laydate;
    var admin = layui.admin;
    /**
     * 创建对象
     */
    var MgrUser = {};
    /**
     * 初始化表格的列
     */
    MgrUser.initColumn = function () {
        return [[
            {title: '序号', type: 'numbers'},
            {field: 'id', hide: true, title: '用户id'},
            {field: 'account', sort: true, title: '账号'},
            {field: 'name', sort: true, title: '姓名'},
            {field: 'avatar', templet: '#avatarTpl', title: '图像', align: 'center'},
            {field: 'sexMessage', title: '性别'},
            {field: 'roleName', title: '角色'},
            {field: 'deptName', title: '部门'},
            {field: 'email', title: '邮箱'},
            {field: 'phone', title: '电话'},
            {field: 'createTime', sort: true, title: '创建时间'},
            {field: 'status', templet: '#statusTpl', title: '状态'},
            {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 280}
        ]];
    };
    /**
     * 检索
     */
    MgrUser.search = function () {
        MgrUser.table.reload({
            where: {
                name: $("#name").val(),
                timeLimit: $("#timeLimit").val()
            }
        });
    };
    /**
     * 弹出添加对话框
     */
    MgrUser.openAdd = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            area: ['600px', '800px'],
            title: '添加用户',
            content: Feng.ctxPath + '/mgr/user_add',
            end: function () {
                admin.getTempData('formOk') && MgrUser.search();
            }
        });
    };
    /**
     * 弹出编辑对话框
     *
     */
    MgrUser.openEdit = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            area: ['600px', '800px'],
            title: '编辑用户',
            content: Feng.ctxPath + '/mgr/user_edit?userId=' + data.id,
            end: function () {
                admin.getTempData('formOk') && MgrUser.search();
            }
        });
    };
    /**
     * 渲染表格
     */
    MgrUser.table = table.render({
        elem: '#userTable',
        url: Feng.ctxPath + '/mgr/list',
        page: true,
        toolbar: "#toolbar",
        height: "full-30",
        even: true,
        cellMinWidth: 100,
        cols: MgrUser.initColumn(),
        where: {
            name: '',
            timeLimit: ''
        },
        done: function () {
            laydate.render({
                elem: '#timeLimit',
                range: true,
                max: Feng.currentDate()
            });
        }
    });
    /**
     * 头工具栏事件
     */
    table.on('toolbar(userTable)', function (obj) {
        var layEvent = obj.event;
        MgrUser[layEvent]()
    });
    /**
     * 行工具栏事件
     */
    table.on('tool(userTable)', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            MgrUser.openEdit(data);
        } else {
            Feng.doAction({
                id: data.id,
                module: "mgr",
                action: layEvent,
                title: layEvent == "delete" ? "删除用户" : "重置用户密码",
                confirm: true,
                finish: function (d) {
                    MgrUser.search();
                }
            });
        }
    });
    /**
     * 修改状态
     */
    form.on('switch(status)', function (obj) {
        Feng.doAction({
            id: obj.elem.value,
            module: "mgr",
            action: obj.elem.checked ? "unfreeze" : "freeze",
            title: obj.elem.checked ? "解冻" : "冻结",
            finish: function (d) {
                MgrUser.search();
            }
        });
    });
});
