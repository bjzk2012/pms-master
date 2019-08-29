layui.use(['form', 'jquery'], function () {
    var $ = layui.jquery;
    var form = layui.form;
    form.verify({
        psw: [
            /^[\S]{6,12}$/,
            '密码必须6到12位，且不能出现空格'
        ],
        act: [
            /^[\w]{5,36}$/,
            '用户账号必须5到36位，只能是单词字符（字母，数字，下划线，中横线）'
        ],
        username: [
            /^(?=.*\d)((?=.*[a-z])|(?=.*[A-Z]))[a-zA-Z\d]{6,20}$/ && !/^\d+?.*$/
            , '6-20位字母加数字，允许大写字母，不能以数字开头'
        ],
        loginname: [
            /^(?=.*\d)((?=.*[a-z])|(?=.*[A-Z]))[a-zA-Z\d]{6,20}$/ && !/^\d+?.*$/
            || /^[a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/
            || /(17[0-9]{9})$|(15[0-9]{9})$|(13[0-9]{9})$|(18[0-9]{9})$|(14[0-9]{9})$|(16[0-9]{9})/
            , '请输入正确的用户名或者手机号码'
        ],
        receiver: [
            /^[a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/
            || /(17[0-9]{9})$|(15[0-9]{9})$|(13[0-9]{9})$|(18[0-9]{9})$|(14[0-9]{9})/
            , '请输入正确的电子邮箱或者手机号码'
        ],
        ipo_name_num: [
            /^(?![0-9]+$)(?![a-zA-Z]+$)[\s\S]{1,100}$/
            , '请输入正确的名称，不能输入纯字母和纯数字'
        ],
        hangOut_dayNum: [
            /^[2-9]\d$|^1[0-7]\d$|^180$/
            , '请输入正确的挂牌天数，挂牌天数不得少于20天且不能超过180天'
        ],
        greaterThanHightest: function (value, param) {
            if (parseInt(value) <= parseInt(param.html())) {
                return '您输入的报价必须大于当前报价'
            }
        },
        lessThanMax: function (value, param) {
            if (parseFloat(value) > parseFloat(param.html())) {
                return '您输入的退款金额必须小于或等于最高退款金额'
            }
        },
        offer_price: [
            /^[1-9]\d*000$/, '您输入的报价必须是1000的整数倍'
        ],
        initial_bid_price: function (value) {  //挂发布牌交易,初始价格价为1万到1亿的整数
            if (value.trim() >= 10000 && value.trim() <= 100000000) {
                return true;
            } else {
                return '您输入的报价须1万到1亿之间，且价格须为整数';
            }
        },
        max_price: function (value) {	//报价价格最大为十亿
            if (value < 1000000000) {
                return true;
            } else {
                return '最高报价为十亿，请不要输入超过十亿';
            }
        },
        selected_type: function (value, element, param) {
            if (!value || value === "请选择服务大类") {
                return '请选择服务大类';
            }
        },
        selected_category: function (value) {
            if (!value || value === "请选择服务小类") {
                return '请选择服务小类';
            }
        },
        classify_box: function (value) {
            if (!value || value === "请选择尼斯分类") {
                return '请选择尼斯分类';
            }
        },
        legalStatus_box: function (value) {
            if (!value || value === "请选择法律状态") {
                return '请选择法律状态';
            }
        },
        assType_box: function (value) {
            if (!value || value === "请选择组合类型") {
                return '请选择组合类型';
            }
        },
        auditOpinion_box: function (value) {
            if (!value || value === "请选择审核状态") {
                return '审核意见不能为空!'
            }
        },
        cityCheck: function (value) {
            if (!value || value === "省/直辖市" || value === "市" || value === "区/县") {
                return '必选字段'
            }
        },
        paid_box: function (value) {
            if (!value || value === "请选择") {
                return '请选择是否有完税证明'
            }
        },
        isPublic_box: function (value) {
            if (!value || value === "请选择") {
                return '请选择类型'
            }
        },
        price: function (value, element, param) {
            var utilClass = new trmUtil();
            if (!value || value == "面议") return true;
            if (value > 10000000) {
                return '请输入10000000以内的金额'
            }
            if (/^([+-]?)((\d{1,3}(,\d{3})*)|(\d+))+(\.\d{1,2})?$/ && /^\d{1,16}(\.\d{1,2})?$/) {
                return true;
            } else {
                return '请输入10000000以内的金额'
            }
        },
        // TODO
        applicationNum: function (value, element, param) {	//判断专利号（20字符以内；小数点前为0-9数字，倒数第二位为小数；最后一位包含0~9,X
            if (!value) {
                return '请输入正确的专利（申请）号，例如：201508081234.5"'
            }
            if (value.split(".")[1] == undefined || value.split(".")[1].length > 1) {
                return '请输入正确的专利（申请）号，例如：201508081234.5'
            }
            return this.optional(element) || /^\d{1,17}?\.\d?(\d$|[\\X])$/.test(value.trim()) || /^CN?\d{1,17}?\.\d?(\d$|[\\X])$/.test(value.trim());
        },
        // TODO
        applicationCNum: function (value, element, param) {	//判断专利号（CN开头，20字符以内；小数点前为0-9数字，倒数第二位为小数；最后一位包含0~9,X
            if (!value) {
                return '请输入正确的专利（申请）号，例如：CN201508081234.5或201508081234.5'
            }
            if (value.split(".")[1] == undefined || value.split(".")[1].length > 1) {
                return '请输入正确的专利（申请）号，例如：CN201508081234.5或201508081234.5'
            }
            return this.optional(element) || /^(CN)?\d{1,17}?\.\d?(\d$|[\\X])$/ig.test(value.trim());
        },
        publicNum: function (value, element, param) {  //判断公开号（50字符以内；以大写CN开头；公开号最后一位共包含：A B C U Y S其中一个；CN与最后一位之间为0-9，最少一位
            if (!value || !/^CN?\d{1,47}?[\\A\\B\\C\\U\\Y\\S]$/) {
                return '请输入正确的公开号，例如：CN1340998A';
            }
        },
        onlyNumber: [
            /^\d{0,20}$/
            , '请输入1-20位数字'
        ],

        postCode: [
            /^\d{6}$/
            , '请输入6位纯数字'
        ],


    });
})