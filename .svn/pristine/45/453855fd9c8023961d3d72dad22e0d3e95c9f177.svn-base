layui.use(['table', 'treetable', 'admin'], function () {
    var $ = layui.$;
    var table = layui.table;
    var treetable = layui.treetable;
    var admin = layui.admin;

    /**
     * 系统管理--文章目录管理
     */
    var Catalogue = {
        tableId: "catalogueTable",
        condition: {
            catalogueId: ""
        }
    };

    /**
     * 初始化表格的列
     */
    Catalogue.initColumn = function () {
        return [[
            {title: '序号', type: 'numbers'},
            {field: 'id', hide: true, title: 'id'},
            {field: 'simpleName', title: '目录简称'},
            {field: 'code', title: '编码'},
            {field: 'fullName', title: '目录全称'},
            {field: 'sort', title: '排序'},
            {field: 'description', title: '备注'},
            {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 200}
        ]];
    };

    Catalogue.reload = function(data){
        treetable.render({
            elem: '#catalogueTable',
            url: Feng.ctxPath + '/catalogue/list',
            page: false,
            toolbar: '#toolbar',
            height: "full-30",
            even: true,
            cellMinWidth: 100,
            cols: Catalogue.initColumn(),
            treeColIndex: 2,
            treeSpid: "0",
            treeIdName: 'id',
            treePidName: 'pId',
            treeDefaultClose: false,
            treeLinkage: true,
            where: data
        });
    };
    Catalogue.reload({
        name: ''
    });

    /**
     * 点击查询按钮
     */
    Catalogue.search = function () {
        Catalogue.reload({
            name: $("#name").val()
        });
    };

    /**
     * 弹出添加
     */
    Catalogue.openAdd = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            area: ['600px', '800px'],
            title: '添加文章目录',
            content: Feng.ctxPath + '/catalogue/catalogue_add?parentId=' + Catalogue.condition.catalogueId,
            end: function () {
                admin.getTempData('formOk') && Catalogue.search();
            }
        });
    };

    /**
     * 点击编辑文章目录
     *
     * @param data 点击按钮时候的行数据
     */
    Catalogue.openEdit = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            area: ['600px', '800px'],
            title: '修改文章目录',
            content: Feng.ctxPath + '/catalogue/catalogue_edit?catalogueId=' + data.id,
            end: function () {
                admin.getTempData('formOk') && Catalogue.search();
            }
        });
    };

    Catalogue.expandAll = function(){
        treetable.expandAll('#catalogueTable');
    };

    Catalogue.foldAll = function(){
        treetable.foldAll('#catalogueTable');
    };

    /**
     * 头工具栏事件
     */
    table.on('toolbar(catalogueTable)', function (obj) {
        var layEvent = obj.event;
        Catalogue[layEvent]()
    });

    // 工具条点击事件
    table.on('tool(catalogueTable)', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            Catalogue.openEdit(data);
        }
    });
});
