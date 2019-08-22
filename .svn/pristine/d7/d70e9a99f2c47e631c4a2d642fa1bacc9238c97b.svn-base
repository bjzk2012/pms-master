layui.use(['table', 'admin', 'element', 'ax'], function () {
    var $ = layui.$;
    var table = layui.table;
    var admin = layui.admin;
    var $ax = layui.ax;

    /**
     * 业务管理--令牌管理
     */
    var Key = {};

    /**
     * 初始化表格的列
     */
    Key.initColumn = function () {
        return [[
            {title: '序号', type: 'numbers'},
            {field: 'id', hide: true, title: 'id'},
            {field: 'name', title: '名称'},
            {field: 'projectName', title: '项目名称'},
            {field: 'useway', title: '用途'},
            {field: 'account', title: '账号'},
            {field: 'password', title: '密码', templet: '#passwordTpl'},
            {field: 'managerName', title: '管理人'},
            {field: 'backupManagerName', title: '备用管理人'},
            {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 200}
        ]];
    };

    /**
     * 点击查询按钮
     */
    Key.search = function () {
        Key.table.reload({
            where: {
                condition: $("#condition").val()
            }
        });
    };

    /**
     * 弹出添加项目
     */
    Key.openAdd = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            area: ['600px', '800px'],
            title: '添加口令',
            content: Feng.ctxPath + '/key/key_add',
            end: function () {
                admin.getTempData('formOk') && Key.search();
            }
        });
    };

    /**
     * 点击编辑项目
     *
     * @param data 点击按钮时候的行数据
     */
    Key.openEdit = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            area: ['600px', '800px'],
            title: '修改口令',
            content: Feng.ctxPath + '/key/key_edit?keyId=' + data.id,
            end: function () {
                admin.getTempData('formOk') && Key.search();
            }
        });
    };

    Key.openPassword = function(data){
        top.layer.prompt({title: '请输入口令', formType: 1}, function(pass, index){
            top.layer.close(index);
            var ajax = new $ax(Feng.ctxPath + "/key/password/"+data, function (data) {
                top.layer.open({
                    title: "口令密码",
                    type: 1,
                    skin: 'layui-layer-rim',
                    area: ['350px', '300px'],
                    content: '<div style="padding: 20px;">' + data.data + '</div>'
                });
                //关掉对话框
                admin.closeThisDialog();
            }, function (data) {
                Feng.error(data.message)
            });
            ajax.set({password:pass});
            ajax.start()
        });
    };

    // 渲染表格
    Key.table = table.render({
        elem: '#keyTable',
        url: Feng.ctxPath + '/key/list',
        page: true,
        toolbar: "#toolbar",
        height: "full-30",
        even: true,
        cellMinWidth: 100,
        cols: Key.initColumn(),
        where: {
            condition: ''
        },
        done: function () {
            $(".read_password").click(function(){
                Key.openPassword($(this).attr("lay-data"))
            });
        }
    });
    /**
     * 头工具栏事件
     */
    table.on('toolbar(keyTable)', function (obj) {
        var layEvent = obj.event;
        Key[layEvent]()
    });

    // 工具条点击事件
    table.on('tool(keyTable)', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            Key.openEdit(data);
        } else if (layEvent === 'delete') {
            Feng.doAction({
                id: data.id,
                module: "key",
                action: layEvent,
                title: "删除口令",
                confirm: true,
                finish: function (d) {
                    Key.search();
                }
            });
        }
    });
});
