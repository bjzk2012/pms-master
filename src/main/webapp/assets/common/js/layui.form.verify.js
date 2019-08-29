layui.use(['form', 'jquery'], function () {
    var $ = layui.jquery;
    var form = layui.form;
    form.verify({
        phone: [
            /(^\.{0}$)|(^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$)|(^0?[1][358][0-9]{9}$)/,
            "请输入正确格式的固定电话或移动电话"
        ],
        telphone: [
            /(^\.{0}$)|(^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$)/,
            '请输入正确格式的固定电话'
        ],
        mobile: [
            /(^\.{0}$)|(^0?[1][358][0-9]{9}$)/,
            '请输入正确格式的手机号码'
        ],
        email: [
            /(^\.{0}$)|(^[a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$)/,
            '请输入正确格式的电子邮件'
        ],
        url: [
            /(^\.{0}$)|(^(?:(?:(?:https?|ftp):)?\/\/)(?:\S+(?::\S*)?@)?(?:(?!(?:10|127)(?:\.\d{1,3}){3})(?!(?:169\.254|192\.168)(?:\.\d{1,3}){2})(?!172\.(?:1[6-9]|2\d|3[0-1])(?:\.\d{1,3}){2})(?:[1-9]\d?|1\d\d|2[01]\d|22[0-3])(?:\.(?:1?\d{1,2}|2[0-4]\d|25[0-5])){2}(?:\.(?:[1-9]\d?|1\d\d|2[0-4]\d|25[0-4]))|(?:(?:[a-z\u00a1-\uffff0-9]-*)*[a-z\u00a1-\uffff0-9]+)(?:\.(?:[a-z\u00a1-\uffff0-9]-*)*[a-z\u00a1-\uffff0-9]+)*(?:\.(?:[a-z\u00a1-\uffff]{2,})).?)(?::\d{2,5})?(?:[/?#]\S*)?$)/,
            '请输入正确格式的网址'
        ],
        dateISO: [
            /(^\.{0}$)|(^\d{4}[\/\-](0?[1-9]|1[012])[\/\-](0?[1-9]|[12][0-9]|3[01])$)/,
            '请输入正确格式的日期 (ISO)'
        ],
        digits: [
            /(^\.{0}$)|(^\d+$)/,
            '请输入正确格式的整数'
        ],
        isChinese: [
            /(^\.{0}$)|(^[\u4e00-\u9fa5]+$)/,
            '内容必须是中文字符'
        ],
        exclusiveChinese: [
            /(^\.{0}$)|(^[^\u4e00-\u9fa5]+$)/,
            '内容不能包含中文字符'
        ],
        equlse: function (value, element) {
            if (!value) {
                return;
            }
            var domId = $(element).attr("lay-equlse");
            var eqvalue = $("#" + domId).val();
            if (!eqvalue) {
                return;
            }
            if (value != eqvalue) {
                return "两次输入不一致"
            }
        },
        checkboxRequired: function (value, element) {
            var elem = $(element);
            var verifyName = elem.attr('name');
            var formElem = elem.parents('.layui-form');
            var verifyElem = formElem.find('input[name=' + verifyName + ']');
            var isTrue = verifyElem.is(':checked');
            var focusElem = verifyElem.next().find('i.layui-icon');
            if (!isTrue || !value) {
                focusElem.css({"border-color": "#FF5722"});
                focusElem.first().attr("tabIndex", "1").css("outline", "0").blur(function () {
                    focusElem.css({"border-color": ""});
                }).focus();
                return '必填项不能为空';
            }
        },
        filesRequired: function (value, element) {
            var elem = $(element);
            var verifyElem = elem.prev();
            if (!elem.val()) {
                verifyElem.css({"border-color": "#FF5722"});
                verifyElem.first().attr("tabIndex", "1").css("outline", "0").blur(function () {
                    verifyElem.css({"border-color": "rgba(0,0,0,0)"});
                }).focus();
                return '必填项不能为空';
            }
        },
        imageRequired: function (value, element) {
            var elem = $(element);
            var verifyElem = elem.next().find(".layui-upload-img");
            if (!elem.val()) {
                verifyElem.css({"border-color": "#FF5722"});
                verifyElem.first().attr("tabIndex", "1").css("outline", "0").blur(function () {
                    verifyElem.css({"border-color": "#eee"});
                }).focus();
                return '必填项不能为空';
            }
        },
        redioRequired: function (value, element) {
            var elem = $(element);
            var verifyName = elem.attr('name');
            var formElem = elem.parents('.layui-form');//获取当前所在的form元素，如果存在的话
            var verifyElem = formElem.find('input[name=' + verifyName + ']');//获取需要校验的元素
            var isTrue = verifyElem.is(':checked');//是否命中校验
            var focusElem = verifyElem.next().find('i.layui-icon');//焦点元素
            if (!isTrue || !value) {
                focusElem.css({"color": "#FF5722"});
                focusElem.first().attr("tabIndex", "1").css("outline", "0").blur(function () {
                    focusElem.css({"color": ""});
                }).focus();
                return '必填项不能为空';
            }
        },
        treeRequired: function (value, element) {
            var elem = $(element);
            var verifyElem = elem.next().find(".layui-input");//获取需要校验的元素
            if (!elem.prev().val()) {
                verifyElem.css({"border-color": "#FF5722"});
                verifyElem.first().attr("tabIndex", "1").css("outline", "0").blur(function () {
                    elem.removeClass("layui-form-danger")
                    verifyElem.css({"border-color": ""});
                }).focus();
                return '必填项不能为空';
            }
        },
        fileRequired: function (value, element) {
            var elem = $(element);
            var verifyElem = elem.parent().find(".layui-form-mid");//获取需要校验的元素
            if (!elem.val()) {
                verifyElem.css({"border-color": "#FF5722"});
                verifyElem.first().attr("tabIndex", "1").css("outline", "0").blur(function () {
                    verifyElem.css({"border-color": "rgba(0,0,0,0)"});
                }).focus();
                return '必填项不能为空';
            }
        }
    });
})