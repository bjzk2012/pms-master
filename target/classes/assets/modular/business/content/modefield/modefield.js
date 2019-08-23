layui.use(['form', 'table', 'admin', 'element'], function () {
    var $ = layui.$;
    var form = layui.form;
    var table = layui.table;
    var admin = layui.admin;
    var element = layui.element;

    /**
     * 系统管理--内容模型管理
     */
    var NodeField = {};

    /**
     * 初始化表格的列
     */
    NodeField.initColumn = function () {
        return [[
            {title: '序号', type: 'numbers'},
            {field: 'id', hide: true, title: 'id'},
            {field: 'code', title: '编码'},
            {field: 'name', title: '名称'},
            {field: 'picture', title: '图片', templet: '#pictureTpl'},
            {field: 'status', title: '状态', templet: '#statusTpl'},
            {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 200}
        ]];
    };

    /**
     * 点击查询按钮
     */
    NodeField.search = function () {
        NodeField.table.reload({
            where: {
                condition: $("#condition").val()
            }
        });
    };

    /**
     * 弹出添加内容模型
     */
    NodeField.openAdd = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            area: ['600px', '800px'],
            title: '添加内容模型',
            content: Feng.ctxPath + '/modefield/modefield_add',
            end: function () {
                admin.getTempData('formOk') && NodeField.search();
            }
        });
    };

    /**
     * 点击编辑内容模型
     *
     * @param data 点击按钮时候的行数据
     */
    NodeField.openEdit = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            area: ['600px', '800px'],
            title: '修改内容模型',
            content: Feng.ctxPath + '/modefield/modefield_edit?modeId=' + data.id,
            end: function () {
                admin.getTempData('formOk') && NodeField.search();
            }
        });
    };

    // 渲染表格
    NodeField.table = table.render({
        elem: '#modefieldTable',
        url: Feng.ctxPath + '/modefield/list',
        page: true,
        toolbar: "#toolbar",
        height: "full-30",
        even: true,
        cols: NodeField.initColumn(),
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
    table.on('toolbar(modefieldTable)', function (obj) {
        var layEvent = obj.event;
        NodeField[layEvent]()
    });

    // 工具条点击事件
    table.on('tool(modefieldTable)', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            NodeField.openEdit(data);
        }
    });

    // 修改状态
    form.on('switch(status)', function (obj) {
        Feng.doAction({
            id: obj.elem.value,
            module: "mode",
            action: obj.elem.checked ? "unfreeze" : "freeze",
            title: obj.elem.checked ? "启用" : "禁用",
            confirm: true,
            elem: obj.elem,
            finish: function (d) {
                NodeField.search();
            }
        });
    });
});
