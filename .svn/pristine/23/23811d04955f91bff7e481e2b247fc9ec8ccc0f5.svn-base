layui.use(['admin', 'table', 'treetable', 'jquery'], function () {
    var $ = layui.jquery;
    var admin = layui.admin;
    var table = layui.table;
    var treetable = layui.treetable;

    /**
     * 系统管理--菜单管理
     */
    var Dict = {};

    /**
     * 初始化表格的列
     */
    Dict.initColumn = function () {
        return [[
            {title: '序号', type: 'numbers'},
            {field: 'id', hide: true, title: 'id'},
            {field: 'name', title: '名称'},
            {field: 'code', title: '字典编码'},
            {field: 'detail', title: '详情'},
            {field: 'description', title: '描述'},
            {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 200}
        ]];
    };

    /**
     * 弹出添加菜单对话框
     */
    Dict.openAdd = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            area: ['600px', '800px'],
            title: '添加字典',
            content: Feng.ctxPath + '/dict/dict_add',
            end: function () {
                admin.getTempData('formOk') && Dict.search();
            }
        });
    };

    /**
     * 点击编辑菜单按钮时
     *
     * @param data 点击按钮时候的行数据
     */
    Dict.openEdit = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            area: ['600px', '800px'],
            title: '编辑字典',
            content: Feng.ctxPath + '/dict/dict_edit?dictId=' + data.id,
            end: function () {
                admin.getTempData('formOk') && Dict.search();
            }
        });
    };

    /**
     * 初始化表格
     */
    Dict.reload = function (data) {
        return treetable.render({
            elem: '#dictTable',
            url: Feng.ctxPath + '/dict/list',
            where: data,
            page: false,
            toolbar: '#toolbar',
            height: "full-30",
            even: true,
            cellMinWidth: 100,
            cols: Dict.initColumn(),
            treeColIndex: 2,
            treeSpid: "0",
            treeIdName: 'id',
            treePidName: 'pId',
            treeDefaultClose: false,
            treeLinkage: true
        });
    };

    // 渲染表格
    Dict.reload({
        condition: ''
    });
    Dict.search = function(){
        Dict.reload({
            condition: $("#condition").val()
        });
    };
    Dict.expandAll = function () {
        treetable.expandAll('#dictTable');
    };
    Dict.foldAll = function () {
        treetable.foldAll('#dictTable');
    };

    /**
     * 头工具栏事件
     */
    table.on('toolbar(dictTable)', function (obj) {
        var layEvent = obj.event;
        Dict[layEvent]()
    });

    // 工具条点击事件
    table.on('tool(dictTable)', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            Dict.openEdit(data);
        } else if (layEvent === 'delete') {
            Feng.doAction({
                id: data.id,
                module: "dict",
                action: layEvent,
                title: "删除",
                confirm: true,
                finish: function (d) {
                    Dict.search();
                }
            });
        }
    });
});
