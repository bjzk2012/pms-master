layui.use(['form', 'table', 'admin', 'element'], function () {
    var $ = layui.$;
    var form = layui.form;
    var table = layui.table;
    var admin = layui.admin;
    var element = layui.element;

    /**
     * 系统管理--内容模型管理
     */
    var Notice = {};

    /**
     * 初始化表格的列
     */
    Notice.initColumn = function () {
        return [[
            {title: '序号', type: 'numbers'},
            {field: 'id', hide: true, title: 'id'},
            {field: 'title', title: '标题'},
            {field: 'typeMessage', title: '类型'},
            {field: 'content', title: '内容'},
            {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 200}
        ]];
    };

    /**
     * 点击查询按钮
     */
    Notice.search = function () {
        Notice.table.reload({
            where: {
                condition: $("#condition").val()
            }
        });
    };

    /**
     * 弹出添加内容模型
     */
    Notice.openAdd = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            area: ['600px', '800px'],
            title: '发送站内信',
            content: Feng.ctxPath + '/notice/notice_add',
            end: function () {
                admin.getTempData('formOk') && Notice.search();
            }
        });
    };

    // 渲染表格
    Notice.table = table.render({
        elem: '#noticeTable',
        url: Feng.ctxPath + '/notice/list',
        page: true,
        toolbar: "#toolbar",
        height: "full-30",
        even: true,
        cols: Notice.initColumn(),
        where: {
            condition: ''
        },
        done: function () {
            element.render();
        }
    });

    Notice.openDetail = function(data){
        Feng.infoDetail("站内信详情", data.content);
    };
    /**
     * 头工具栏事件
     */
    table.on('toolbar(noticeTable)', function (obj) {
        var layEvent = obj.event;
        Notice[layEvent]()
    });

    // 工具条点击事件
    table.on('tool(noticeTable)', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'detail') {
            Notice.openDetail(data);
        }
    });
});
