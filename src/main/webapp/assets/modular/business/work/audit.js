layui.use(['form', 'jquery', 'table', 'laydate'], function () {
    var $ = layui.$;
    var table = layui.table;
    var laydate = layui.laydate;
    var form = layui.form;

    /**
     * 系统管理--项目管理
     */
    var WorkAudit = {
        condition: {
            workId: null
        }
    };

    /**
     * 初始化表格的列
     */
    WorkAudit.initWorkRecordColumn = function () {
        return [[
            {type:'checkbox'},
            {title: '序号', type: 'numbers'},
            {field: 'todayRemark', title: '工作时间', sort: true},
            {field: 'submitUserName', title: '姓名'},
            {field: 'projectName', title: '项目'},
            {field: 'content', title: '工作内容'},
            {field: 'time', title: '工时(h)', sort: true},
            {field: 'submitTime', title: '提交时间', sort: true},
            {field: 'lastAuditTime', title: '终审时间', sort: true},
            {field: 'statusMessage', title: '状态', templet: '#statusTpl'},
            {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 200}
        ]];
    };

    WorkAudit.table = table.render({
        elem: '#workAuditTable',
        url: Feng.ctxPath + '/work/audits',
        page: true,
        toolbar: '#toolbar',
        height: "full-30",
        even: true,
        cellMinWidth: 100,
        cols: WorkAudit.initWorkRecordColumn(),
        where: {
            timeLimit: '',
            submitTimeLimit: '',
            auditTimeLimit: '',
            status: '',
            condition: ''
        },
        done:function(d){
            $("#status").val(this.where.status);
            form.render('select');
            laydate.render({
                elem: '#timeLimit',
                range: true,
                max: Feng.currentDate()
            });
            laydate.render({
                elem: '#submitTimeLimit',
                range: true,
                max: Feng.currentDate()
            });
            laydate.render({
                elem: '#auditTimeLimit',
                range: true,
                max: Feng.currentDate()
            });
        }
    });

    WorkAudit.search = function () {
        WorkAudit.table.reload({
            where: {
                timeLimit: $("#timeLimit").val(),
                submitTimeLimit: $("#submitTimeLimit").val(),
                auditTimeLimit: $("#auditTimeLimit").val(),
                status: $("#status").val(),
                condition: $("#condition").val()
            }
        });
    };

    WorkAudit.openDetail = function (id) {
        top.layui.admin.open({
            type: 2,
            area: ['600px', '800px'],
            title: '查看工作日志',
            content: Feng.ctxPath + '/work/workRecord_detail/' + id,
        });
    };

    WorkAudit.adopt = function(){
        var nodes = table.checkStatus('workAuditTable');
        if (nodes.data.length == 0){
            Feng.error("未选择工作日志");
            return;
        }
        var ids = $.map(nodes.data, function(n){return n.id;}).join(",");
        WorkAudit.audit({ids:ids, title: "批量通过审核",flag: true});
    };

    WorkAudit.refuse = function(){
        var nodes = table.checkStatus('workAuditTable');
        if (nodes.data.length == 0){
            Feng.error("未选择工作日志");
            return;
        }
        var ids = $.map(nodes.data, function(n){return n.id;}).join(",");
        WorkAudit.audit({ids:ids, title: "批量审核拒绝",flag: false});
    };

    WorkAudit.audit = function(params){
        Feng.doAction({
            module: "work/workRecord",
            action: "audit",
            title: params.title,
            confirm: true,
            finish: function (d) {
                WorkAudit.search();
            },
            params: params
        });
    };

    /**
     * 头工具栏事件
     */
    table.on('toolbar(workAuditTable)', function (obj) {
        var layEvent = obj.event;
        WorkAudit[layEvent]()
    });

    table.on('tool(workAuditTable)', function (obj) {
        if (obj.event === 'adopt') {
            WorkAudit.audit({ids:obj.data.id, title: "通过审核",flag: true});
        }
        if (obj.event === 'refuse') {
            WorkAudit.audit({ids:obj.data.id, title: "审核拒绝",flag: false});
        }
        if (obj.event === 'detail') {
            WorkAudit.openDetail(obj.data.id);
        }
    });
});
