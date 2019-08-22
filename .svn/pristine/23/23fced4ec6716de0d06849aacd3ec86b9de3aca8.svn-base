layui.use(['jquery', 'table', 'admin', 'laydate'], function () {
    var $ = layui.$;
    var table = layui.table;
    var admin = layui.admin;
    var laydate = layui.laydate;

    /**
     * 系统管理--项目管理
     */
    var WorkApply = {
        condition: {
            workId: null
        }
    };

    /**
     * 初始化表格的列
     */
    WorkApply.initWorkColumn = function () {
        return [[
            {title: '序号', type: 'numbers'},
            {field: 'todayRemark', title: '时间'},
            {field: 'code', title: '编码', minWidth:200},
            {field: 'statusMessage', title: '状态', templet: '#workStatusTpl'}
        ]];
    };

    /**
     * 初始化表格的列
     */
    WorkApply.initWorkRecordColumn = function () {
        return [[
            {title: '序号', type: 'numbers'},
            {field: 'projectName', title: '项目'},
            {field: 'content', title: '工作内容'},
            {field: 'time', title: '工时'},
            {field: 'statusMessage', title: '状态', templet: '#workRecordstatusTpl'},
            {align: 'center', toolbar: '#workRecordTableBar', title: '操作', minWidth: 200}
        ]];
    };

    // 渲染表格
    WorkApply.workTable = table.render({
        elem: '#workTable',
        url: Feng.ctxPath + '/work/list',
        page: true,
        toolbar: '#workToolbar',
        height: "full-30",
        even: true,
        cellMinWidth: 100,
        cols: WorkApply.initWorkColumn(),
        where: {
            timeLimit: ''
        },
        done: function () {
            laydate.render({
                elem: '#timeLimit',
                range: true,
                max: Feng.currentDate()
            });
            WorkApply.condition.workId = null;
            WorkApply.workRecordSearch();
        }
    });

    WorkApply.workRecordTable = table.render({
        elem: '#workRecordTable',
        url: Feng.ctxPath + '/work/records',
        page: true,
        toolbar: '#workRecordToolbar',
        height: "full-30",
        even: true,
        cellMinWidth: 100,
        cols: WorkApply.initWorkRecordColumn()
    });

    WorkApply.openAdd = function(){
        if(!WorkApply.condition.workId){
            Feng.error("请选择日期!");
            return;
        }
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            area: ['600px', '800px'],
            title: '添加工作日志',
            content: Feng.ctxPath + '/work/workRecord_add?workId=' + WorkApply.condition.workId,
            end: function () {
                admin.getTempData('formOk') && WorkApply.workRecordSearch();
            }
        });
    };

    WorkApply.openEdit = function(workRecordId){
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            area: ['600px', '800px'],
            title: '修改工作日志',
            content: Feng.ctxPath + '/work/workRecord_edit?workRecordId=' + workRecordId,
            end: function () {
                admin.getTempData('formOk') && WorkApply.workRecordSearch();
            }
        });
    };

    WorkApply.search = function(){
        WorkApply.workTable.reload({where: {timeLimit: $("#timeLimit").val()}});
    };

    WorkApply.help = function(){
        top.layui.admin.open({
            type: 2,
            area: ['600px', '800px'],
            title: '操作说明',
            content: Feng.ctxPath + '/work/workRecord_help'
        });
    };

    WorkApply.workRecordSearch = function(){
        WorkApply.workRecordTable.reload({where: {workId: WorkApply.condition.workId}});
    };

    WorkApply.submitWork = function(workId){
        if(!WorkApply.condition.workId){
            Feng.error("请选择日期!");
            return;
        }
        Feng.doAction({
            id: WorkApply.condition.workId,
            module: "work/workRecord",
            action: "submit",
            title: "提交",
            confirm: true,
            finish: function (d) {
                WorkApply.workRecordSearch();
            }
        });
    };

    /**
     * 头工具栏事件
     */
    table.on('toolbar(workTable)', function (obj) {
        var layEvent = obj.event;
        WorkApply[layEvent]()
    });

    /**
     * 头工具栏事件
     */
    table.on('toolbar(workRecordTable)', function (obj) {
        var layEvent = obj.event;
        WorkApply[layEvent]()
    });

    table.on('row(workTable)', function (obj) {
        var data = obj.data;
        WorkApply.condition.workId = data.id;
        WorkApply.workRecordSearch();
        obj.tr.addClass('layui-table-click').siblings().removeClass('layui-table-click');
    });

    table.on('tool(workRecordTable)', function (obj) {
        if (obj.event === 'edit') {
            WorkApply.openEdit(obj.data.id);
        }
        if (obj.event === 'delete') {
            Feng.doAction({
                id: obj.data.id,
                module: "work/workRecord",
                action: "delete",
                title: "删除",
                confirm: true,
                finish: function (d) {
                    WorkApply.workRecordSearch();
                }
            });
        }
    });
});
