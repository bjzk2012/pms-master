layui.use(['table', 'treetable', 'admin'], function () {
    var $ = layui.$;
    var table = layui.table;
    var treetable = layui.treetable;
    var admin = layui.admin;

    /**
     * 系统管理--部门管理
     */
    var Dept = {
        tableId: "deptTable",
        condition: {
            deptId: ""
        }
    };

    /**
     * 初始化表格的列
     */
    Dept.initColumn = function () {
        return [[
            {title: '序号', type: 'numbers'},
            {field: 'id', hide: true, title: 'id'},
            {field: 'simpleName', sort: true, title: '部门简称'},
            {field: 'fullName', sort: true, title: '部门全称'},
            {field: 'sort', sort: true, title: '排序'},
            {field: 'description', title: '备注'},
            {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 200}
        ]];
    };

    Dept.reload = function(data){
        treetable.render({
            elem: '#deptTable',
            url: Feng.ctxPath + '/dept/list',
            page: false,
            toolbar: '#toolbar',
            height: "full-30",
            even: true,
            cellMinWidth: 100,
            cols: Dept.initColumn(),
            treeColIndex: 2,
            treeSpid: "0",
            treeIdName: 'id',
            treePidName: 'pId',
            treeDefaultClose: false,
            treeLinkage: true,
            where: data
        });
    };
    Dept.reload({
        name: ''
    });

    /**
     * 点击查询按钮
     */
    Dept.search = function () {
        Dept.reload({
            name: $("#name").val()
        });
    };

    /**
     * 弹出添加
     */
    Dept.openAdd = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            area: ['600px', '800px'],
            title: '添加部门',
            content: Feng.ctxPath + '/dept/dept_add?parentId=' + Dept.condition.deptId,
            end: function () {
                admin.getTempData('formOk') && Dept.search();
            }
        });
    };

    /**
     * 点击编辑部门
     *
     * @param data 点击按钮时候的行数据
     */
    Dept.openEdit = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            area: ['600px', '800px'],
            title: '修改部门',
            content: Feng.ctxPath + '/dept/dept_edit?deptId=' + data.id,
            end: function () {
                admin.getTempData('formOk') && Dept.search();
            }
        });
    };

    Dept.expandAll = function(){
        treetable.expandAll('#deptTable');
    };

    Dept.foldAll = function(){
        treetable.foldAll('#deptTable');
    };

    /**
     * 头工具栏事件
     */
    table.on('toolbar(deptTable)', function (obj) {
        var layEvent = obj.event;
        Dept[layEvent]()
    });

    // 工具条点击事件
    table.on('tool(deptTable)', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            Dept.openEdit(data);
        } else if (layEvent === 'delete') {
            Feng.doAction({
                id: data.id,
                module: "dept",
                action: layEvent,
                title: "删除部门",
                confirm: true,
                finish: function (d) {
                    Dept.search();
                }
            });
        }
    });
});
