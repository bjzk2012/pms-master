layui.use(['form', 'table', 'admin', 'element'], function () {
    var $ = layui.$;
    var form = layui.form;
    var table = layui.table;
    var admin = layui.admin;
    var element = layui.element;

    /**
     * 系统管理--问题管理
     */
    var Question = {};

    /**
     * 初始化表格的列
     */
    Question.initColumn = function () {
        return [[
            {title: '序号', type: 'numbers'},
            {field: 'id', hide: true, title: 'id'},
            {field: 'code', title: '编码'},
            {field: 'title', title: '标题'},
            {field: 'projectName', title: '项目'},
            {field: 'categoryMessage', title: '问题类型'},
            {field: 'causeMessage', title: '问题原因'},
            {field: 'statusMessage', title: '状态'},
            {field: 'createTime', title: '发起时间'},
            {field: 'sponsor', title: '发起人'},
            {field: 'liableName', title: '负责人'},
            {field: 'ip', title: 'IP地址'},
            {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 380}
        ]];
    };

    /**
     * 点击查询按钮
     */
    Question.search = function () {
        Question.table.reload({
            where: {
                condition: $("#condition").val()
            }
        });
    };

    /**
     * 弹出添加问题
     */
    Question.openAdd = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            area: ['1200px', '800px'],
            title: '添加问题',
            content: Feng.ctxPath + '/question/question_add',
            end: function () {
                admin.getTempData('formOk') && Question.search();
            }
        });
    };

    /**
     * 点击编辑问题
     *
     * @param data 点击按钮时候的行数据
     */
    Question.openEdit = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            area: ['1200px', '800px'],
            title: '修改问题',
            content: Feng.ctxPath + '/question/question_edit?questionId=' + data.id,
            end: function () {
                admin.getTempData('formOk') && Question.search();
            }
        });
    };

    // 渲染表格
    Question.table = table.render({
        elem: '#questionTable',
        url: Feng.ctxPath + '/question/list',
        page: true,
        toolbar: "#toolbar",
        height: "full-30",
        even: true,
        cellMinWidth: 100,
        cols: Question.initColumn(),
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
    table.on('toolbar(questionTable)', function (obj) {
        var layEvent = obj.event;
        Question[layEvent]()
    });

    // 工具条点击事件
    table.on('tool(questionTable)', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;
        switch (layEvent) {
            case 'confirm':
                Feng.doAction({
                    id: data.id,
                    module: "question",
                    action: layEvent,
                    title: "确认问题",
                    confirm: true,
                    finish: function (d) {
                        Question.search();
                    }
                });
                break;
            case 'appoint':
                Question.openAppoint(data);
                break;
            case 'solve':
                Question.openSolve(data);
                break;
            case 'edit':
                Question.openEdit(data);
                break;
            case 'close':
                Feng.doAction({
                    id: data.id,
                    module: "question",
                    action: layEvent,
                    title: "关闭问题",
                    confirm: true,
                    finish: function (d) {
                        Question.search();
                    }
                });
                break;
            case 'active':
                Question.openActive(data);
                break;
            case 'detail':
                Question.openDetail(data);
                break;
        }
    });
    Question.openAppoint = function(data){
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            area: ['1200px', '800px'],
            title: '指派问题',
            content: Feng.ctxPath + '/question/question_appoint?questionId=' + data.id,
            end: function () {
                admin.getTempData('formOk') && Question.search();
            }
        });
    };
    Question.openDetail = function(data){
        top.layui.admin.open({
            type: 2,
            area: ['1200px', '800px'],
            title: '查看问题',
            content: Feng.ctxPath + '/question/question_detail?questionId=' + data.id,
        });
    };
    Question.openSolve = function(data){
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            area: ['1200px', '800px'],
            title: '解决问题',
            content: Feng.ctxPath + '/question/question_solve?questionId=' + data.id,
            end: function () {
                admin.getTempData('formOk') && Question.search();
            }
        });
    };
    Question.openActive = function(data){
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            area: ['1200px', '800px'],
            title: '激活问题',
            content: Feng.ctxPath + '/question/question_active?questionId=' + data.id,
            end: function () {
                admin.getTempData('formOk') && Question.search();
            }
        });
    };

    // 修改状态
    form.on('switch(status)', function (obj) {
        Feng.doAction({
            id: obj.elem.value,
            module: "question",
            action: obj.elem.checked ? "unfreeze" : "freeze",
            title: obj.elem.checked ? "启用" : "禁用",
            confirm: true,
            finish: function (d) {
                Question.search();
            }
        });
    });
});
