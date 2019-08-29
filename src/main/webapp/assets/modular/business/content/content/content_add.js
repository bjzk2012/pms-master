layui.use(['jquery', 'form', 'admin', 'ax', 'numinput', 'upload', 'layedit'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var upload = layui.upload;
    var layedit = layui.layedit;
    var layeditIndexs = [];

    var numinput = layui.numinput;
    numinput.init({ rightBtns: true });
    if ($(".layui-layedit").length > 0){
        form.verify({
            richtextRequired:function(value, items){
                var doms = $.map(layeditIndexs, function(n, i){
                    if(n.domId == $(items).attr("id")){
                        return n.index;
                    }
                });
                if (!layedit.getContent(doms[0])){
                    var focusElem = $(items).next();
                    focusElem.css({"border-color": "#FF5722"});
                    //对非输入框设置焦点
                    focusElem.first().attr("tabIndex", "1").css("outline", "0").blur(function () {
                        focusElem.css({"border-color": ""});
                    }).focus();
                    return "必填项不能为空";
                }
            }
        });
    }

    $(".layui-layedit").each(function(i, n){
        var domId = $(n).attr("id");
        var index = layedit.build(domId,{
            uploadImage: {
                url: '/system/upload'
            }
        });
        layeditIndexs.push({
            index : index,
            domId : domId
        });
    });

    form.render();
    admin.iframeAuto();

    var uploadInst = upload.render({
        elem: '#picture_btn',
        url: Feng.ctxPath + '/system/upload',
        accept: 'images',
        before: function(obj){
            obj.preview(function(index, file, r){
                $('#picture_img').attr('src', r); //图片链接（base64）
            });
        },
        done: function(r){
            $("#picture").val(r.data.src);
        },
        error: function(){
            //演示失败状态，并实现重传
            var demoText = $('#picture_text');
            demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
            demoText.find('.demo-reload').on('click', function(){
                uploadInst.upload();
            });
        }
    });

    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        if (layeditIndexs.length > 0){
            for (var i = 0; i < layeditIndexs.length; i++) {
                data.field[layeditIndexs[i].domId] = layedit.getContent(layeditIndexs[i].index);
            }
        }
        var ajax = new $ax(Feng.ctxPath + "/content/add", function (data) {
            Feng.success("添加成功！");

            //传给上个页面，刷新table用
            admin.putTempData('formOk', true);

            //关掉对话框
            admin.closeThisDialog();
        }, function (data) {
            Feng.error("添加失败！" + data.message)
        });
        ajax.set(data.field);
        ajax.start();
        return false;
    });
});