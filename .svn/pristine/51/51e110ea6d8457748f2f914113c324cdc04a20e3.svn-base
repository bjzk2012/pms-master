layui.define(['layer'], function (exports) {
    var $ = layui.jquery;
    var layer = layui.layer;
    var bodyDOM = '.layui-layout-admin>.layui-body';
    var tabDOM = bodyDOM + '>.layui-tab';
    var sideDOM = '.layui-layout-admin>.layui-side>.layui-side-scroll';
    var headerDOM = '.layui-layout-admin>.layui-header';
    var tabFilter = 'admin-pagetabs';
    var navFilter = 'admin-side-nav';
    var admin = {
        defaultTheme: 'admin',
        tableName: 'easyweb',
        flexible: function (expand) {
            var isExapnd = $('.layui-layout-admin').hasClass('admin-nav-mini');
            if (isExapnd == !expand) {
                return
            }
            if (expand) {
                $('.layui-layout-admin').removeClass('admin-nav-mini')
            } else {
                $('.layui-layout-admin').addClass('admin-nav-mini')
            }
            admin.removeNavHover()
        },
        activeNav: function (url) {
            if (!url) {
                url = window.location.pathname;
                var us = url.split('/');
                url = us[us.length - 1]
            }
            if (url && url != '') {
                var $a = $(sideDOM + '>.layui-nav a[lay-href="' + url + '"]');
                if ($a && $a.length > 0) {
                    $(sideDOM + '>.layui-nav .layui-nav-item .layui-nav-child dd').removeClass('layui-this');
                    $(sideDOM + '>.layui-nav .layui-nav-item').removeClass('layui-this');
                    $(sideDOM + '>.layui-nav .layui-nav-item').removeClass('layui-nav-itemed');
                    $a.parent().addClass('layui-this');
                    $a.parent('dd').parents('.layui-nav-child').parent().addClass('layui-nav-itemed');
                    $('ul[lay-filter="' + navFilter + '"]').addClass('layui-hide');
                    var $aUl = $a.parents('.layui-nav');
                    $aUl.removeClass('layui-hide');
                    $(headerDOM + '>.layui-nav>.layui-nav-item').removeClass('layui-this');
                    $(headerDOM + '>.layui-nav>.layui-nav-item>a[nav-bind="' + $aUl.attr('nav-id') + '"]').parent().addClass('layui-this')
                } else {
                    console.warn(url + ' is not in left side')
                }
            } else {
                console.warn('active url not be null')
            }
        },
        popupRight: function (param) {
            var eCallBack = param.end;
            if (param.title == undefined) {
                param.title = false;
                param.closeBtn = false
            }
            if (param.anim == undefined) {
                param.anim = 2
            }
            if (param.fixed == undefined) {
                param.fixed = true
            }
            param.isOutAnim = false;
            param.offset = 'r';
            param.shadeClose = true;
            param.area = '336px';
            param.skin = 'layui-layer-adminRight';
            param.move = false;
            param.end = function () {
                layer.closeAll('tips');
                eCallBack ? eCallBack : ''
            };
            return admin.open(param)
        },
        open: function (param) {
            if (!param.area) {
                param.area = (param.type == 2) ? ['360px', '300px'] : '360px'
            }
            if (!param.skin) {
                param.skin = 'layui-layer-admin'
            }
            if (!param.offset) {
                param.offset = '100px'
            }
            if (param.fixed == undefined) {
                param.fixed = false
            }
            param.resize = param.resize != undefined ? param.resize : false;
            param.shade = param.shade != undefined ? param.shade : .1;
            param.maxmin = param.maxmin != undefined ? param.maxmin : false;
            return layer.open(param)
        },
        req: function (url, data, success, method) {
            admin.ajax({
                url: url,
                data: data,
                type: method,
                dataType: 'json',
                success: success
            })
        },
        ajax: function (param) {
            var successCallback = param.success;
            param.success = function (result, status, xhr) {
                var jsonRs;
                if ('json' == param.dataType.toLowerCase()) {
                    jsonRs = result
                } else {
                    jsonRs = admin.parseJSON(result)
                }
                if (jsonRs && admin.ajaxSuccessBefore(jsonRs) == false) {
                    return
                }
                successCallback(result, status, xhr)
            };
            param.error = function (xhr) {
                param.success({
                    code: xhr.status,
                    msg: xhr.statusText
                })
            };
            param.beforeSend = function (xhr) {
                var headers = admin.getAjaxHeaders();
                for (var i = 0; i < headers.length; i++) {
                    xhr.setRequestHeader(headers[i].name, headers[i].value)
                }
            };
            $.ajax(param)
        },
        ajaxSuccessBefore: function (res) {
            if (res.code == 401) {
                layer.msg(res.msg, {
                    icon: 2,
                    time: 1500
                }, function () {}, 1000);
                return false
            }
        },
        getAjaxHeaders: function () {
            var headers = new Array();
            return headers
        },
        parseJSON: function (str) {
            if (typeof str == 'string') {
                try {
                    var obj = JSON.parse(str);
                    if (typeof obj == 'object' && obj) {
                        return obj
                    }
                } catch (e) {
                    console.warn(e)
                }
            }
        },
        showLoading: function (elem) {
            if (!elem) {
                elem = 'body'
            }
            $(elem).addClass('page-no-scroll');
            $(elem).append('<div class="page-loading"><div class="rubik-loader"></div></div>')
        },
        removeLoading: function (elem) {
            if (!elem) {
                elem = 'body'
            }
            $(elem).children('.page-loading').remove();
            $(elem).removeClass('page-no-scroll')
        },
        putTempData: function (key, value) {
            if (value != undefined && value != null) {
                layui.sessionData('tempData', {
                    key: key,
                    value: value
                })
            } else {
                layui.sessionData('tempData', {
                    key: key,
                    remove: true
                })
            }
        },
        getTempData: function (key) {
            return layui.sessionData('tempData')[key]
        },
        rollPage: function (d) {
            var $tabTitle = $(tabDOM + '>.layui-tab-title');
            var left = $tabTitle.scrollLeft();
            if ('left' === d) {
                $tabTitle.animate({
                    'scrollLeft': left - 120
                }, 100)
            } else if ('auto' === d) {
                var autoLeft = 0;
                $tabTitle.children("li").each(function () {
                    if ($(this).hasClass('layui-this')) {
                        return false
                    } else {
                        autoLeft += $(this).outerWidth()
                    }
                });
                $tabTitle.animate({
                    'scrollLeft': autoLeft - 120
                }, 100)
            } else {
                $tabTitle.animate({
                    'scrollLeft': left + 120
                }, 100)
            }
        },
        refresh: function (url) {
            var $iframe;
            if (!url) {
                $iframe = $(tabDOM + '>.layui-tab-content>.layui-tab-item.layui-show>.admin-iframe');
                if (!$iframe || $iframe.length <= 0) {
                    $iframe = $(bodyDOM + '>.admin-iframe')
                }
            } else {
                $iframe = $(tabDOM + '>.layui-tab-content>.layui-tab-item>.admin-iframe[lay-id="' + url + '"]');
                if (!$iframe || $iframe.length <= 0) {
                    $iframe = $(bodyDOM + '>.admin-iframe')
                }
            }
            if ($iframe && $iframe[0]) {
                $iframe[0].contentWindow.location.reload(true)
            } else {
                console.warn(url + ' is not found')
            }
        },
        closeThisTabs: function (url) {
            admin.closeTabOperNav();
            var $title = $(tabDOM + '>.layui-tab-title');
            if (!url) {
                if ($title.find('li').first().hasClass('layui-this')) {
                    layer.msg('主页不能关闭', {
                        icon: 2
                    });
                    return
                }
                $title.find('li.layui-this').find(".layui-tab-close").trigger("click")
            } else {
                if (url == $title.find('li').first().attr('lay-id')) {
                    layer.msg('主页不能关闭', {
                        icon: 2
                    });
                    return
                }
                $title.find('li[lay-id="' + url + '"]').find(".layui-tab-close").trigger("click")
            }
        },
        closeOtherTabs: function (url) {
            if (!url) {
                $(tabDOM + '>.layui-tab-title li:gt(0):not(.layui-this)').find('.layui-tab-close').trigger('click')
            } else {
                $(tabDOM + '>.layui-tab-title li:gt(0)').each(function () {
                    if (url != $(this).attr('lay-id')) {
                        $(this).find('.layui-tab-close').trigger('click')
                    }
                })
            }
            admin.closeTabOperNav()
        },
        closeAllTabs: function () {
            $(tabDOM + '>.layui-tab-title li:gt(0)').find('.layui-tab-close').trigger('click');
            $(tabDOM + '>.layui-tab-title li:eq(0)').trigger('click');
            admin.closeTabOperNav()
        },
        closeTabOperNav: function () {
            $('.layui-icon-down .layui-nav .layui-nav-child').removeClass('layui-show')
        },
        changeTheme: function (theme) {
            if (theme) {
                layui.data(admin.tableName, {
                    key: 'theme',
                    value: theme
                });
                if ('admin' == theme) {
                    theme = undefined
                }
            } else {
                layui.data(admin.tableName, {
                    key: 'theme',
                    remove: true
                })
            }
            admin.removeTheme(top);
            !theme || top.layui.link(admin.getThemeDir() + theme + '.css', theme);
            var ifs = top.window.frames;
            for (var i = 0; i < ifs.length; i++) {
                var tif = ifs[i];
                try {
                    admin.removeTheme(tif)
                } catch (e) {}
                if (theme && tif.layui) {
                    tif.layui.link(admin.getThemeDir() + theme + '.css', theme)
                }
            }
        },
        removeTheme: function(w) {
            if (!w) {
                w = window
            }
            if (w.layui) {
                var themeId = 'layuicss-theme';
                w.layui.jquery('link[id^="' + themeId + '"]').remove()
            }
        },
        getThemeDir: function () {
            return layui.cache.base + 'theme/'
        },
        closeThisDialog: function () {
            parent.layer.close(parent.layer.getFrameIndex(window.name))
        },
        iframeAuto: function () {
            parent.layer.iframeAuto(parent.layer.getFrameIndex(window.name))
        },
        getPageHeight: function () {
            return document.documentElement.clientHeight || document.body.clientHeight
        },
        getPageWidth: function () {
            return document.documentElement.clientWidth || document.body.clientWidth
        },
        removeNavHover: function () {
            $('.admin-nav-hover>.layui-nav-child').css({
                'top': 'auto',
                'max-height': 'none',
                'overflow': 'auto'
            });
            $('.admin-nav-hover').removeClass('admin-nav-hover')
        },
        setNavHoverCss: function ($that) {
            var $nav = $('.admin-nav-hover>.layui-nav-child');
            if ($that && $nav.length > 0) {
                var isOver = ($that.offset().top + $nav.outerHeight()) > window.innerHeight;
                if (isOver) {
                    var newTop = $that.offset().top - $nav.outerHeight() + $that.outerHeight();
                    if (newTop < 50) {
                        var pageHeight = admin.getPageHeight();
                        if ($that.offset().top < pageHeight / 2) {
                            $nav.css({
                                'top': '50px',
                                'max-height': pageHeight - 50 + 'px',
                                'overflow': 'auto'
                            })
                        } else {
                            $nav.css({
                                'top': $that.offset().top,
                                'max-height': pageHeight - $that.offset().top,
                                'overflow': 'auto'
                            })
                        }
                    } else {
                        $nav.css('top', newTop)
                    }
                } else {
                    $nav.css('top', $that.offset().top)
                }
                isHover = true
            }
        }
    };
    admin.events = {
        flexible: function (e) {
            var expand = $('.layui-layout-admin').hasClass('admin-nav-mini');
            admin.flexible(expand)
        },
        refresh: function () {
            admin.refresh()
        },
        back: function () {
            history.back()
        },
        theme: function () {
            var url = $(this).attr('data-url');
            admin.popupRight({
                type: 2,
                content: url ? url : Feng.ctxPath + '/system/theme'
            })
        },
        note: function () {
            var url = $(this).attr('data-url');
            admin.popupRight({
                id: 'layer-note',
                title: '便签',
                type: 2,
                closeBtn: false,
                content: url ? url : 'page/tpl/tpl-note.html'
            })
        },
        message: function () {
            var url = $(this).attr('data-url');
            admin.popupRight({
                type: 2,
                content: url ? url : Feng.ctxPath + '/system/message'
            })
        },
        fullScreen: function (e) {
            var ac = 'layui-icon-screen-full',
                ic = 'layui-icon-screen-restore';
            var ti = $(this).find('i');
            var isFullscreen = document.fullscreenElement || document.msFullscreenElement || document.mozFullScreenElement || document.webkitFullscreenElement || false;
            if (isFullscreen) {
                var efs = document.exitFullscreen || document.webkitExitFullscreen || document.mozCancelFullScreen || document.msExitFullscreen;
                if (efs) {
                    efs.call(document)
                } else if (window.ActiveXObject) {
                    var ws = new ActiveXObject('WScript.Shell');
                    ws && ws.SendKeys('{F11}')
                }
                ti.addClass(ac).removeClass(ic)
            } else {
                var el = document.documentElement;
                var rfs = el.requestFullscreen || el.webkitRequestFullscreen || el.mozRequestFullScreen || el.msRequestFullscreen;
                if (rfs) {
                    rfs.call(el)
                } else if (window.ActiveXObject) {
                    var ws = new ActiveXObject('WScript.Shell');
                    ws && ws.SendKeys('{F11}')
                }
                ti.addClass(ic).removeClass(ac)
            }
        },
        leftPage: function () {
            admin.rollPage("left")
        },
        rightPage: function () {
            admin.rollPage()
        },
        closeThisTabs: function () {
            admin.closeThisTabs()
        },
        closeOtherTabs: function () {
            admin.closeOtherTabs()
        },
        closeAllTabs: function () {
            admin.closeAllTabs()
        },
        closeDialog: function () {
            admin.closeThisDialog()
        }
    };
    $('body').on('click', '*[ew-event]', function () {
        var event = $(this).attr('ew-event');
        var te = admin.events[event];
        te && te.call(this, $(this))
    });
    $('.site-mobile-shade').click(function () {
        admin.flexible(true)
    });
    var isHover = false;
    $('body').on('mouseenter', '.layui-layout-admin.admin-nav-mini .layui-side .layui-nav .layui-nav-item>a', function () {
        if (admin.getPageWidth() > 750) {
            var $that = $(this);
            $('.admin-nav-hover>.layui-nav-child').css('top', 'auto');
            $('.admin-nav-hover').removeClass('admin-nav-hover');
            $that.parent().addClass('admin-nav-hover');
            var $nav = $('.admin-nav-hover>.layui-nav-child');
            if ($nav.length > 0) {
                admin.setNavHoverCss($that)
            } else {
                var tipText = $that.find('cite').text();
                var bgColor = $('.layui-layout-admin .layui-side').css('background-color');
                bgColor = (bgColor == 'rgb(255, 255, 255)' ? '#009688' : bgColor);
                layer.tips(tipText, $that, {
                    tips: [2, bgColor],
                    time: -1
                })
            }
        }
    }).on('mouseleave', '.layui-layout-admin.admin-nav-mini .layui-side .layui-nav .layui-nav-item>a', function () {
        layer.closeAll('tips')
    });
    $('body').on('mouseleave', '.layui-layout-admin.admin-nav-mini .layui-side', function () {
        isHover = false;
        setTimeout(function () {
            if (!isHover) {
                admin.removeNavHover()
            }
        }, 500)
    });
    $('body').on('mouseenter', '.layui-layout-admin.admin-nav-mini .layui-side .layui-nav .layui-nav-item.admin-nav-hover .layui-nav-child', function () {
        isHover = true
    });
    $('body').on('mouseenter', '*[lay-tips]', function () {
        var tipText = $(this).attr('lay-tips');
        var dt = $(this).attr('lay-direction');
        var bgColor = $(this).attr('lay-bg');
        layer.tips(tipText, this, {
            tips: [dt || 3, bgColor || '#333333'],
            time: -1
        })
    }).on('mouseleave', '*[lay-tips]', function () {
        layer.closeAll('tips')
    });
    $('body').on('click', '*[ew-href]', function () {
        var url = $(this).attr('ew-href');
        var title = $(this).text();
        top.layui.index.openTab({
            title: title,
            url: url
        })
    });
    if (!layui.contextMenu) {
        $(document).off('click.ctxMenu').on('click.ctxMenu', function () {
            var ifs = top.window.frames;
            for (var i = 0; i < ifs.length; i++) {
                var tif = ifs[i];
                try {
                    tif.layui.jquery('.ctxMenu').remove()
                } catch (e) {}
            }
            top.layui.jquery('.ctxMenu').remove()
        })
    }
    var theme = layui.data(admin.tableName).theme;
    if (theme) {
        (theme == 'admin') || layui.link(admin.getThemeDir() + theme + '.css', theme)
    } else if ('admin' != admin.defaultTheme) {
        layui.link(admin.getThemeDir() + admin.defaultTheme + '.css', admin.defaultTheme)
    }
    if (top.layui && top.layui.index && top.layui.index.pageTabs) {
        $('body').addClass('tab-open')
    }
    exports('admin', admin)
});