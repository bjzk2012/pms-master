layui.use(['form', 'table', 'admin', 'element', 'dropdown'], function () {
    var $ = layui.$;
    var form = layui.form;
    var table = layui.table;
    var admin = layui.admin;
    var element = layui.element;
    var dropdown = layui.dropdown;

    var Content = {
        condition: {
            modeId: null
        }
    };

    /**
     * 初始化表格的列
     */
    Content.initColumn = function () {
        return [[
            {title: '序号', type: 'numbers'},
            {field: 'id', hide: true, title: 'id'},
            {field: 'subject', title: '标题'},
            {field: 'author', title: '编辑'},
            {field: 'origin', title: '来源'},
            {field: 'publishTime', title: '发布时间'},
            {field: 'status', title: '状态', templet: '#statusTpl'},
            {align: 'center', toolbar: '#tableBar', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    Content.search = function () {
        Content.table.reload({
            where: {
                condition: $("#condition").val()
            }
        });
    };

    /**
     * 弹出添加内容
     */
    Content.openAdd = function (modeId) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            area: ['600px', '800px'],
            title: '添加内容',
            content: Feng.ctxPath + '/content/content_add?modeId=' + modeId,
            end: function () {
                admin.getTempData('formOk') && Content.search();
            }
        });
    };
    Content.showModeNav = function(){
        $("#modeNav").show();
    };
    $(document).click(function(event){
        $("#modeNav").hide();
    });

    /**
     * 点击编辑内容
     *
     * @param data 点击按钮时候的行数据
     */
    Content.openEdit = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            area: ['600px', '800px'],
            title: '修改内容',
            content: Feng.ctxPath + '/content/content_edit?modeId=' + data.id,
            end: function () {
                admin.getTempData('formOk') && Content.search();
            }
        });
    };

    // 渲染表格
    Content.table = table.render({
        elem: '#contentTable',
        url: Feng.ctxPath + '/content/list',
        page: true,
        toolbar: "#toolbar",
        height: "full-30",
        even: true,
        cols: Content.initColumn(),
        where: {
            condition: ''
        }
    });
    table.on('toolbar(contentTable)', function (obj) {
        var layEvent = obj.event;
        Content[layEvent]()
    });

    $("[lay-filter='openAdd']").click(function () {
        var modeId = $(this).attr("lay-data");
        Content.openAdd(modeId);
    });

    // 工具条点击事件
    table.on('tool(contentTable)', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            Content.openEdit(data);
        }
    });
});
