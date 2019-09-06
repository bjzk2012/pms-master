layui.use(['form', 'upload', 'table', 'element', 'ax', 'laydate', 'cropper', 'croppers'], function () {
    var $ = layui.jquery;
    var form = layui.form;
    var upload = layui.upload;
    var table = layui.table;
    var element = layui.element;
    var $ax = layui.ax;
    var laydate = layui.laydate;
    var croppers = layui.croppers;
    var MyOriginate = {};
    // 我的发起和我的处理
    MyOriginate.initColumn = function () {
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
        ]];
    };

    MyOriginate.search = function(){
        MyOriginate.table.reload({
            where: {
                condition: $("#mydisposalTablecondition").val(),
                projectId: $("#mydisposalTableprojectId").val(),
                category: $("#mydisposalTablecategory").val(),
                submitTimeLimit: $("#mydisposalTablesubmitTimeLimit").val(),
                phone: $("#mydisposalTablephone").val(),
                status: $("#mydisposalTablestatus").val()
            }
        });
    };

    // 渲染表格
    MyOriginate.table = table.render({
        elem: '#mydisposalTable',
        domId: 'mydisposalTable',
        url: Feng.ctxPath + '/center/mydisposal/list',
        page: true,
        toolbar: "#toolbar",
        height: "full-132",
        even: true,
        cols: MyOriginate.initColumn(),
        where: {
            condition: '',
            projectId: '',
            category: '',
            submitTimeLimit: '',
            phone: '',
            status: ''
        },
        done: function () {
            element.render();
            laydate.render({
                elem: '#mydisposalTablesubmitTimeLimit',
                range: true,
                max: Feng.currentDate()
            });
        }
    });
    table.on('toolbar(mydisposalTable)', function (obj) {
        var layEvent = obj.event;
        MyOriginate[layEvent]();
    });
});