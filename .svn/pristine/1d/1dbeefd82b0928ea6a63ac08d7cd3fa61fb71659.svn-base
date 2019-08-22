layui.use(['admin', 'table', 'treetable', 'jquery', 'form'], function () {
    var $ = layui.jquery;
    var admin = layui.admin;
    var table = layui.table;
    var treetable = layui.treetable;
    var form = layui.form;

    /**
     * 系统管理--菜单管理
     */
    var Menu = {};

    /**
     * 初始化表格的列
     */
    Menu.initColumn = function () {
        return [[
            {title: '序号', type: 'numbers'},
            {field: 'id', hide: true, title: 'id'},
            {field: 'name', title: '菜单名称'},
            {field: 'code', title: '菜单编码'},
            {field: 'icon', title: '图标', templet: '#iconTpl', align: 'center'},
            {field: 'url', title: '请求地址'},
            {field: 'sort', sort: true, title: '排序'},
            {field: 'levels', sort: true, title: '层级'},
            {field: 'menuFlagMessage', title: '是否是菜单'},
            {field: 'status', sort: true, templet: '#statusTpl', title: '状态'},
            {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 200}
        ]];
    };

    Menu.reload = function(data){
        treetable.render({
            elem: '#menuTable',
            url: Feng.ctxPath + '/menu/list',
            page: false,
            toolbar: '#toolbar',
            height: "full-30",
            even: true,
            cellMinWidth: 100,
            cols: Menu.initColumn(),
            treeColIndex: 2,
            treeSpid: "0",
            treeIdName: 'id',
            treePidName: 'pId',
            treeDefaultClose: false,
            treeLinkage: true,
            where: data
        });
    };
    Menu.reload({
        menuName: ''
    });
    /**
     * 点击查询按钮
     */
    Menu.search = function () {
        Menu.reload({
            menuName: $("#menuName").val()
        });
    };

    /**
     * 弹出添加菜单对话框
     */
    Menu.openAdd = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            area: ['600px', '800px'],
            title: '添加菜单',
            content: Feng.ctxPath + '/menu/menu_add',
            end: function () {
                admin.getTempData('formOk') && Menu.search();
            }
        });
    };

    /**
     * 点击编辑菜单按钮时
     *
     * @param data 点击按钮时候的行数据
     */
    Menu.openEdit = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            area: ['600px', '800px'],
            title: '编辑菜单',
            content: Feng.ctxPath + '/menu/menu_edit?menuId=' + data.id,
            end: function () {
                admin.getTempData('formOk') && Menu.search();
            }
        });
    };

    Menu.expandAll = function(){
        treetable.expandAll('#menuTable');
    };

    Menu.foldAll = function(){
        treetable.foldAll('#menuTable');
    };

    /**
     * 头工具栏事件
     */
    table.on('toolbar(menuTable)', function (obj) {
        var layEvent = obj.event;
        Menu[layEvent]()
    });

    // 工具条点击事件
    table.on('tool(menuTable)', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            Menu.openEdit(data);
        } else if (layEvent === 'delete') {
            Feng.doAction({
                id: data.id,
                module: "menu",
                action: layEvent,
                title: "删除",
                confirm: true,
                finish: function (d) {
                    Menu.search();
                }
            });
        }
    });

    // 修改状态
    form.on('switch(status)', function (obj) {
        Feng.doAction({
            id: obj.elem.value,
            module: "menu",
            action: obj.elem.checked ? "unfreeze" : "freeze",
            title: obj.elem.checked ? "启用" : "禁用",
            finish: function (d) {
                Menu.search();
            }
        });
    });
});
