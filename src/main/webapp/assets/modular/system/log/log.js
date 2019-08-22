layui.use(['form', 'jquery', 'table', 'ax', 'laydate'], function () {
    var $ = layui.$;
    var $ax = layui.ax;
    var form = layui.form;
    var table = layui.table;
    var laydate = layui.laydate;

    /**
     * 系统管理--操作日志
     */
    var Log = {
        tableId: "logTable"   //表格id
    };

    /**
     * 初始化表格的列
     */
    Log.initColumn = function () {
        return [[
            {title: '序号', type: 'numbers'},
            {field: 'id', hide: true, sort: true, title: 'id'},
            {field: 'logTypeMessage', sort: true, title: '日志类型'},
            {field: 'logName', sort: true, title: '日志名称'},
            {field: 'userName', title: '用户名称'},
            {field: 'className', title: '类名'},
            {field: 'method', title: '方法名'},
            {field: 'succeedMessage', title: '状态'},
            {field: 'createTime', sort: true, title: '时间'},
            {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 100}
        ]];
    };

    /**
     * 点击查询按钮
     */
    Log.search = function () {
        Log.table.reload({
            where: {
                timeLimit: $("#timeLimit").val(),
                logName: $("#logName").val(),
                logType: $("#logType").val()
            }
        });
    };

    /**
     * 日志详情
     */
    Log.openDetail = function (data) {
        var ajax = new $ax(Feng.ctxPath + "/log/detail/" + data.id, function (data) {
            Feng.infoDetail("日志详情", data.data.message);
        }, function (data) {
            Feng.error("获取详情失败!");
        });
        ajax.type = "get";
        ajax.start();
    };

    /**
     * 清空日志
     */
    Log.clear = function () {
        Feng.confirm("是否清空所有日志?", function () {
            var ajax = new $ax(Feng.ctxPath + "/log/delete", function (data) {
                Feng.success("清空日志成功!");
                Log.search();
            }, function (data) {
                Feng.error("清空日志失败!");
            });
            ajax.start();
        });
    };

    // 渲染表格
    Log.table = table.render({
        elem: '#logTable',
        url: Feng.ctxPath + '/log/list',
        page: true,
        toolbar: '#toolbar',
        height: "full-30",
        even: true,
        cellMinWidth: 100,
        cols: Log.initColumn(),
        where: {
            timeLimit: '',
            logName: '',
            logType: ''
        },
        done: function(d){
            $("#logType").val(this.where.logType);
            form.render('select');
            laydate.render({
                elem: '#timeLimit',
                range: true,
                max: Feng.currentDate()
            });
        }
    });

    /**
     * 头工具栏事件
     */
    table.on('toolbar(logTable)', function (obj) {
        var layEvent = obj.event;
        Log[layEvent]()
    });

    // 工具条点击事件
    table.on('tool(logTable)', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;
        if (layEvent === 'detail') {
            Log.openDetail(data);
        }
    });
});
