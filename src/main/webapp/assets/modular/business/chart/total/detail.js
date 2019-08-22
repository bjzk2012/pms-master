layui.use(['table'], function () {
    var table = layui.table;
    /**
     * 创建对象
     */
    var ChartTotalDetail = {};
    ChartTotalDetail.table = table.init('recordTable', {
        page: false
    });
    table.on('tool(recordTable)', function (obj) {
        if (obj.event === 'read') {
            ChartTotalDetail.openDetail(obj.data.id);
        }
    });
    ChartTotalDetail.openDetail = function(id){
        top.layui.admin.open({
            type: 2,
            area: ['1100px', '480px'],
            title: '查看审核流程',
            content: Feng.ctxPath + '/chart/total/audits?workRecordId=' + id
        });
    };
});
