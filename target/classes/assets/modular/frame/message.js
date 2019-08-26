layui.use(['element', 'form', 'jquery', 'ax', 'laytpl'], function () {
    var $ = layui.jquery;
    var element = layui.element;
    var form = layui.form;
    var $ax = layui.ax;
    var laytpl = layui.laytpl;
    var ajax = new $ax(Feng.ctxPath + "/messages", function(data){
        $("#notice_item").empty();
        if (data.code != 200 || data.data.length == 0){
            var noticeEmptyTpl = $("#noticeEmptyTpl").html();
            laytpl(noticeEmptyTpl).render({}, function(html){
                $("#notice_item").append(html);
            });
        } else {
            var noticeTpl = $("#noticeTpl").html();
            var noticeCount = 0;
            $.each(data.data, function(i, n){
                if (n.isRead == "UNREAD"){
                    noticeCount ++;
                }
                laytpl(noticeTpl).render(n, function(html){
                    var dom = $(html);
                    dom.click(function(){
                        read(n.id, true);
                    });
                    $("#notice_item").append(dom);
                });
            });
            $("#noticeCount").text(noticeCount);
        }
    }, function (data) {
        Feng.error("加载通知信息失败!" + data.message + "!");
    });
    ajax.type = "get";
    ajax.start();

    var read = function(id, show){
        var readAjax = new $ax(Feng.ctxPath + "/messages/read/"+id, function(data){
            ajax.start();
            if (show) {
                Feng.infoDetail("通知详情", data.data.content);
            }
        }, function (data) {
            Feng.error("通知信息标记已读失败!" + data.message + "!");
        });
        readAjax.start();
    };

    // 清空消息点击事件
    $('.message-btn-clear').click(function () {
        read("all");
    });
});