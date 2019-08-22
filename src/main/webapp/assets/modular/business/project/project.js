layui.use(['form', 'table', 'admin', 'element'], function () {
    var $ = layui.$;
    var form = layui.form;
    var table = layui.table;
    var admin = layui.admin;
    var element = layui.element;

    /**
     * 系统管理--项目管理
     */
    var Project = {};

    /**
     * 初始化表格的列
     */
    Project.initColumn = function () {
        return [[
            {title: '序号', type: 'numbers'},
            {field: 'id', hide: true, title: 'id'},
            {field: 'code', title: '编码'},
            {field: 'name', title: '名称'},
            {field: 'time', title: '总工时'},
            {field: 'used', title: '已用工时'},
            {field: 'rate', title: '总进度', templet: '#rateTpl'},
            {field: 'status', title: '状态', templet: '#statusTpl'},
            {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 200}
        ]];
    };

    /**
     * 点击查询按钮
     */
    Project.search = function () {
        Project.table.reload({
            where: {
                condition: $("#condition").val()
            }
        });
    };

    /**
     * 弹出添加项目
     */
    Project.openAdd = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            area: ['600px', '800px'],
            title: '添加项目',
            content: Feng.ctxPath + '/project/project_add',
            end: function () {
                admin.getTempData('formOk') && Project.search();
            }
        });
    };

    /**
     * 点击编辑项目
     *
     * @param data 点击按钮时候的行数据
     */
    Project.openEdit = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            area: ['600px', '800px'],
            title: '修改项目',
            content: Feng.ctxPath + '/project/project_edit?projectId=' + data.id,
            end: function () {
                admin.getTempData('formOk') && Project.search();
            }
        });
    };

    // 渲染表格
    Project.table = table.render({
        elem: '#projectTable',
        url: Feng.ctxPath + '/project/list',
        page: true,
        toolbar: "#toolbar",
        height: "full-30",
        even: true,
        cellMinWidth: 100,
        cols: Project.initColumn(),
        where: {
            condition: ''
        },
        done: function () {
            element.render();
        }
    });
    /**
     * 头工具栏事件
     */
    table.on('toolbar(projectTable)', function (obj) {
        var layEvent = obj.event;
        Project[layEvent]()
    });

    // 工具条点击事件
    table.on('tool(projectTable)', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            Project.openEdit(data);
        } else if (layEvent === 'delete') {
            Feng.doAction({
                id: data.id,
                module: "project",
                action: layEvent,
                title: "删除项目",
                confirm: true,
                finish: function (d) {
                    Project.search();
                }
            });
        }
    });

    // 修改状态
    form.on('switch(status)', function (obj) {
        Feng.doAction({
            id: obj.elem.value,
            module: "project",
            action: obj.elem.checked ? "unfreeze" : "freeze",
            title: obj.elem.checked ? "启用" : "禁用",
            confirm: true,
            finish: function (d) {
                Project.search();
            }
        });
    });
});
