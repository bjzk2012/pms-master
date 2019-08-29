layui.use(['form', 'table', 'admin', 'element'], function () {
    var $ = layui.$;
    var form = layui.form;
    var table = layui.table;
    var admin = layui.admin;
    var element = layui.element;

    /**
     * 系统管理--内容模型管理
     */
    var Mode = {
        condition: {
            modeId: null
        }
    };

    /**
     * 初始化表格的列
     */
    Mode.initColumn = function () {
        return [[
            {title: '序号', type: 'numbers'},
            {field: 'id', hide: true, title: 'id'},
            {field: 'code', title: '编码'},
            {field: 'name', title: '名称'},
            {field: 'picture', title: '图片', templet: '#pictureTpl'},
            {field: 'cols', title: '单行属性数'},
            {field: 'status', title: '状态', templet: '#statusTpl'},
            {align: 'center', toolbar: '#tableBar', title: '操作'}
        ]];
    };

    Mode.initFieldColumn = function () {
        return [[
            {title: '序号', type: 'numbers'},
            {field: 'id', hide: true, title: 'id'},
            {field: 'field', title: '字段'},
            {field: 'label', title: '名称'},
            {field: 'typeMessage', title: '字段类型'},
            {field: 'single', title: '独占一行', templet: '#singleTpl'},
            {field: 'sort', title: '排序', edit: 'text'},
            {field: 'status', title: '状态', templet: '#fieldStatusTpl'},
            {align: 'center', toolbar: '#tableBar', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    Mode.search = function () {
        Mode.table.reload({
            where: {
                condition: $("#condition").val()
            }
        });
        Mode.condition.modeId = null;
        $("#fieldCondition").val("");
        Mode.fieldSearch();
    };

    Mode.fieldSearch = function () {
        Mode.fieldTable.reload({
            where: {
                modeId: Mode.condition.modeId,
                condition: $("#fieldCondition").val()
            }
        });
    };

    /**
     * 弹出添加内容模型
     */
    Mode.openAdd = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            area: ['600px', '800px'],
            title: '添加内容模型',
            content: Feng.ctxPath + '/mode/mode_add',
            end: function () {
                admin.getTempData('formOk') && Mode.search();
            }
        });
    };

    Mode.openFieldAdd = function () {
        if (!Mode.condition.modeId) {
            Feng.error("请选择内容模板!");
            return;
        }
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            area: ['600px', '800px'],
            title: '添加内容模型属性',
            content: Feng.ctxPath + '/modefield/modefield_add?modeId=' + Mode.condition.modeId,
            end: function () {
                admin.getTempData('formOk') && Mode.fieldSearch();
            }
        });
    };

    /**
     * 点击编辑内容模型
     *
     * @param data 点击按钮时候的行数据
     */
    Mode.openEdit = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            area: ['600px', '800px'],
            title: '修改内容模型',
            content: Feng.ctxPath + '/mode/mode_edit?modeId=' + data.id,
            end: function () {
                admin.getTempData('formOk') && Mode.search();
            }
        });
    };


    Mode.openFieldEdit = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            area: ['600px', '800px'],
            title: '修改内容模型属性',
            content: Feng.ctxPath + '/modefield/modefield_edit?modefieldId=' + data.id,
            end: function () {
                admin.getTempData('formOk') && Mode.fieldSearch();
            }
        });
    };

    // 渲染表格
    Mode.table = table.render({
        elem: '#modeTable',
        url: Feng.ctxPath + '/mode/list',
        page: true,
        toolbar: "#toolbar",
        height: "full-30",
        even: true,
        cols: Mode.initColumn(),
        where: {
            condition: ''
        }
    });

    Mode.fieldTable = table.render({
        elem: '#modeFieldTable',
        url: Feng.ctxPath + '/modefield/list',
        page: true,
        toolbar: "#fieldToolbar",
        height: "full-30",
        even: true,
        cols: Mode.initFieldColumn(),
        where: {
            condition: ''
        }
    });
    table.on('edit(modeFieldTable)', function (obj) {
        Feng.doAction({
            id: obj.data.id,
            module: "modefield",
            action: "sort",
            title: "排序",
            msg: false,
            finish: function (d) {
                Mode.fieldSearch();
            },
            params: {sort: obj.value}
        });
    });
    /**
     * 头工具栏事件
     */
    table.on('toolbar(modeTable)', function (obj) {
        var layEvent = obj.event;
        Mode[layEvent]()
    });
    table.on('toolbar(modeFieldTable)', function (obj) {
        var layEvent = obj.event;
        Mode[layEvent]()
    });

    // 工具条点击事件
    table.on('tool(modeTable)', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            Mode.openEdit(data);
        }
    });

    // 工具条点击事件
    table.on('tool(modeFieldTable)', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            Mode.openFieldEdit(data);
        }
    });

    table.on('row(modeTable)', function (obj) {
        var data = obj.data;
        Mode.condition.modeId = data.id;
        Mode.fieldSearch();
        obj.tr.addClass('layui-table-click').siblings().removeClass('layui-table-click');
    });

    // 修改独占一行
    form.on('switch(single)', function (obj) {
        Feng.doAction({
            id: obj.elem.value,
            module: "modefield",
            action: obj.elem.checked ? "single" : "unsingle",
            title: obj.elem.checked ? "独占一行" : "取消独占",
            confirm: true,
            elem: obj.elem,
            finish: function (d) {
                Mode.fieldSearch();
            }
        });
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
                Mode.search();
            }
        });
    });

    // 修改状态
    form.on('switch(fieldStatus)', function (obj) {
        Feng.doAction({
            id: obj.elem.value,
            module: "modefield",
            action: obj.elem.checked ? "unfreeze" : "freeze",
            title: obj.elem.checked ? "启用" : "禁用",
            confirm: true,
            elem: obj.elem,
            finish: function (d) {
                Mode.fieldSearch();
            }
        });
    });
});
