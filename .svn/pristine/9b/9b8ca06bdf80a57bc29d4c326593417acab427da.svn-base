layui.use(['jquery', 'layer', 'ax'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    // 初始化PV
    var allPvAjax = new $ax(Feng.ctxPath + "/system/pv", function (data) {
        if (data.code == 200) {
            $.each(data.data, function (i, n) {
                $("#" + i + "_pv").text(n);
            })
        }
    }, function (data) {
        Feng.error("获取PV信息错误!" + data.message + "!");
    });

    // 渲染活动情况预测
    var myCharts1 = echarts.init(document.getElementById('hdqkyc'), myEchartsTheme);
    var hourPvAjax = new $ax(Feng.ctxPath + "/system/hourpv", function (data) {
        if (data.code == 200) {
            var xAxis = $.map(data.data, function (n) {
                return n.name + ":00";
            });
            var series = $.map(data.data, function (n) {
                return n.count;
            });
            var option1 = {
                grid: {
                    top: '20'
                },
                tooltip: {
                    trigger: "axis"
                },
                xAxis: [{
                    type: "category",
                    data: xAxis
                }],
                yAxis: [{
                    type: "value"
                }],
                series: [{
                    name: "访问量",
                    type: "line",
                    itemStyle: {
                        normal: {
                            areaStyle: {
                                type: "default"
                            }
                        }
                    },
                    data: series
                }]
            };
            myCharts1.setOption(option1, true);
        }
    }, function (data) {
        Feng.error("获取PV信息错误!" + data.message + "!");
    });

    // 渲染访问有效率图表
    var myCharts2 = echarts.init(document.getElementById('hjxl'), myEchartsTheme);
    var option2 = {
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b}: {c} ({d}%)"
        },
        grid: {
            top : '0'
        },
        series: [
            {
                name:'访问来源',
                type:'pie',
                radius: [0, '30%'],
                label: {
                    show: false
                },
                data:[
                    {value:335, name:'直达'},
                    {value:679, name:'营销广告'},
                    {value:1548, name:'搜索引擎'}
                ]
            },
            {
                name:'访问来源',
                type:'pie',
                radius: ['40%', '55%'],
                data:[
                    {value:335, name:'直达'},
                    {value:310, name:'邮件营销'},
                    {value:234, name:'联盟广告'},
                    {value:135, name:'视频广告'},
                    {value:1048, name:'百度'},
                    {value:251, name:'谷歌'},
                    {value:147, name:'必应'},
                    {value:102, name:'其他'}
                ]
            }
        ]
    };

    var myCharts3 = echarts.init(document.getElementById('map'), myEchartsTheme);
    var myCharts3Init = function() {
        $.getJSON("/assets/common/json/data-china.json", function (geoJson) {
            echarts.registerMap('zhongguo', geoJson);
            var option3 = {
                title: {
                    text: '访问量分布',
                    x: 'center'
                },
                tooltip: {
                    trigger: 'item',
                    formatter: function (params) {
                        return params.name + ' : ' + params.value[2];
                    }
                },
                geo: {
                    show: true,
                    map: 'zhongguo',
                    label: {
                        normal: {
                            show: false
                        },
                        emphasis: {
                            show: true,
                            textStyle: {
                                color: '#FFF'
                            }
                        }
                    },
                    itemStyle: {
                        normal: {
                            areaColor: '#05988b',
                            borderColor: '#eee'
                        },
                        emphasis: {
                            areaColor: '#05988b',
                            borderColor: '#eee'
                        }
                    }
                },
                series: [{
                    name: '访问量',
                    type: 'effectScatter',
                    coordinateSystem: 'geo',
                    showEffectOn: 'render',
                    rippleEffect: {
                        brushType: 'stroke'
                    },
                    hoverAnimation: true,
                    label: {
                        normal: {
                            show: false
                        },
                        emphasis: {
                            formatter: '{b}',
                            position: 'right',
                            show: true
                        }
                    },
                    itemStyle: {
                        normal: {
                            color: '#C1232B',
                            shadowBlur: 10,
                            shadowColor: '#fe994e'
                        }
                    }
                }]
            };
            var regionPvAjax = new $ax(Feng.ctxPath + "/system/regionpv", function (data) {
                if (data.code == 200) {
                    var all = 0;
                    data = $.map(data.data, function (n) {
                        all += n.count;
                        return {name: n.name, value: n.count}
                    });
                    var result = convertData(data.sort(function (a, b) {
                        return b.value - a.value;
                    }));
                    option3.series[0].symbolSize = function (val) {
                        return val[2] / (all / 30);
                    },
                        option3.series[0].data = result;
                    myCharts3.setOption(option3);
                }
            }, function (data) {
                Feng.error("获取PV信息错误!" + data.message + "!");
            });
            regionPvAjax.type = "get";
            regionPvAjax.start();
        });
    };
    // 渲染网站注册量图表
    var myCharts4 = echarts.init(document.getElementById('fwl'), myEchartsTheme);
    var monthPvAjax = new $ax(Feng.ctxPath + "/system/monthpv", function (data) {
        if (data.code == 200) {
            var xAxis = $.map(data.data, function (n) {
                return n.name + "月";
            });
            var series = $.map(data.data, function (n) {
                return n.count;
            });
            var option4 = {
                grid: {
                    top: '20'
                },
                tooltip: {
                    trigger: "axis"
                },
                yAxis: [{
                    type: "value"
                }],
                xAxis: {
                    data: xAxis
                },
                series: [{
                    type: 'bar',
                    data: series
                }]
            };
            myCharts4.setOption(option4);
        }
    }, function (data) {
        Feng.error("获取PV信息错误!" + data.message + "!");
    });


    // 渲染用户访问排行
    var userPvAjax = new $ax(Feng.ctxPath + "/system/userpv", function (data) {
        if (data.code == 200) {
            var user_pv = $("#user_pv").empty();
            $.each(data.data.sort(function (a, b) {
                return b.count - a.count;
            }), function (i, n) {
                if (i < 5) {
                    if (i <= 2) {
                        user_pv.append($("<tr><td><span class='layui-badge layui-bg-cyan'>" + (i + 1) + "</span></td><td>" + n.name + "</td><td>" + n.count + "</td></tr>"));
                    } else {
                        user_pv.append($("<tr><td><span class='layui-badge layui-bg-gray'>" + (i + 1) + "</span></td><td>" + n.name + "</td><td>" + n.count + "</td></tr>"));
                    }
                }
            })
        }
    }, function (data) {
        Feng.error("获取PV信息错误!" + data.message + "!");
    });
    userPvAjax.type = "get";
    userPvAjax.start();
    // 1.直接打开
    if($("[lay-id='/system/welcome']", top.document).is(".layui-this")){
        // 渲染
    }
    // 2.选中打开的
    $("[lay-id='/system/welcome']", top.document).click(function(){
        myCharts2.setOption(option2);
    });
    try{
        allPvAjax.type = "get";
        allPvAjax.start();
        hourPvAjax.type = "get";
        hourPvAjax.start();
        myCharts2.setOption(option2);
        myCharts3Init();
        monthPvAjax.type = "get";
        monthPvAjax.start();
    }catch (e) {

    }
    // 窗口大小改变事件
    window.onresize = function () {
        myCharts1.resize();
        myCharts2.resize();
        myCharts3.resize();
        myCharts4.resize();
    };

});