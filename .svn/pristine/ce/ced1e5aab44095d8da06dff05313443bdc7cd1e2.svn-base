layui.use(['jquery', 'form', 'table', 'admin'], function () {
    var $ = layui.$;
    var form = layui.form;
    var table = layui.table;
    var admin = layui.admin;

    /**
     * 系统管理--角色管理
     */
    var Role = {};

    /**
     * 初始化表格的列
     */
    Role.initColumn = function () {
        return [[
            {title: '序号', type: 'numbers'},
            {field: 'roleId', hide: true, sort: true, title: '角色id'},
            {field: 'name', title: '名称'},
            {field: 'code', title: '标识'},
            {field: 'status', sort: true, templet: '#statusTpl', title: '状态'},
            {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 200}
        ]];
    };

    Role.table = table.render({
        elem: '#roleTable',
        url: Feng.ctxPath + '/role/list',
        page: true,
        toolbar: '#toolbar',
        height: "full-30",
        even: true,
        cellMinWidth: 100,
        cols: Role.initColumn(),
        where: {
            roleName: ''
        }
    });

    /**
     * 点击查询按钮
     */
    Role.search = function () {
        Role.table.reload({where: {roleName:$("#roleName").val()}});
    };

    /**
     * 弹出添加角色
     */
    Role.openAdd = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            area: ['600px', '800px'],
            title: '添加角色',
            content: Feng.ctxPath + '/role/role_add',
            end: function () {
                admin.getTempData('formOk') && Role.search();
            }
        });
    };

    /**
     * 点击编辑角色
     *
     * @param data 点击按钮时候的行数据
     */
    Role.openEdit = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            area: ['600px', '800px'],
            title: '修改角色',
            content: Feng.ctxPath + '/role/role_edit?roleId=' + data.id,
            end: function () {
                admin.getTempData('formOk') && Role.search();
            }
        });
    };

    /**
     * 头工具栏事件
     */
    table.on('toolbar(roleTable)', function (obj) {
        var layEvent = obj.event;
        Role[layEvent]()
    });

    // 工具条点击事件
    table.on('tool(roleTable)', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            Role.openEdit(data);
        } else if (layEvent === 'delete') {
            Feng.doAction(data.id, "delete", "删除", true);
            Feng.doAction({
                id: data.id,
                module: "role",
                action: layEvent,
                title: "删除角色",
                confirm: true,
                finish: function (d) {
                    Role.search();
                }
            });
        }
    });

    // 修改状态
    form.on('switch(status)', function (obj) {
        Feng.doAction({
            id: obj.elem.value,
            module: "role",
            action: obj.elem.checked ? "unfreeze" : "freeze",
            title: obj.elem.checked ? "启用" : "禁用",
            finish: function (d) {
                Role.search();
            }
        });
    });
});
