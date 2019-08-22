layui.define(["jquery"], function (exports) {
    var $ = layui.jquery;
    var contextMenu = {
        bind: function (elem, items) {
            $(elem).bind('contextmenu', function (e) {
                contextMenu.show(items, e.clientX, e.clientY);
                return false
            })
        },
        getHtml: function (items, pid) {
            var htmlStr = '';
            for (var i = 0; i < items.length; i++) {
                var item = items[i];
                item.itemId = 'ctxMenu-' + pid + i;
                if (item.subs && item.subs.length > 0) {
                    htmlStr += '<div class="ctxMenu-item haveMore" lay-id="' + item.itemId + '">';
                    htmlStr += '<a>';
                    if (item.icon) {
                        htmlStr += '<i class="' + item.icon + ' ctx-icon"></i>'
                    }
                    htmlStr += item.name;
                    htmlStr += '<i class="layui-icon layui-icon-right icon-more"></i>';
                    htmlStr += '</a>';
                    htmlStr += '<div class="ctxMenu-sub" style="display: none;">';
                    htmlStr += contextMenu.getHtml(item.subs, pid + i);
                    htmlStr += '</div>'
                } else {
                    htmlStr += '<div class="ctxMenu-item" lay-id="' + item.itemId + '">';
                    htmlStr += '<a>';
                    if (item.icon) {
                        htmlStr += '<i class="' + item.icon + ' ctx-icon"></i>'
                    }
                    htmlStr += item.name;
                    htmlStr += '</a>'
                }
                htmlStr += '</div>';
                if (item.hr == true) {
                    htmlStr += '<hr/>'
                }
            }
            return htmlStr
        },
        setEvents: function (items) {
            for (var i = 0; i < items.length; i++) {
                var item = items[i];
                if (item.click) {
                    $('.ctxMenu').on('click', '[lay-id="' + item.itemId + '"]', item.click)
                }
                if (item.subs && item.subs.length > 0) {
                    contextMenu.setEvents(item.subs)
                }
            }
        },
        remove: function () {
            var ifs = top.window.frames;
            for (var i = 0; i < ifs.length; i++) {
                var tif = ifs[i];
                try {
                    tif.layui.jquery('.ctxMenu').remove()
                } catch (e) {}
            }
            try {
                top.layui.jquery('.ctxMenu').remove()
            } catch (e) {}
        },
        getPageHeight: function () {
            return document.documentElement.clientHeight || document.body.clientHeight
        },
        getPageWidth: function () {
            return document.documentElement.clientWidth || document.body.clientWidth
        },
        show: function (items, x, y) {
            var xy = 'left: ' + x + 'px; top: ' + y + 'px;';
            var htmlStr = '<div class="ctxMenu" style="' + xy + '">';
            htmlStr += contextMenu.getHtml(items, '');
            htmlStr += '</div>';
            contextMenu.remove();
            $('body').append(htmlStr);
            var $ctxMenu = $('.ctxMenu');
            if (x + $ctxMenu.outerWidth() > contextMenu.getPageWidth()) {
                x -= $ctxMenu.outerWidth()
            }
            if (y + $ctxMenu.outerHeight() > contextMenu.getPageHeight()) {
                y = y - $ctxMenu.outerHeight();
                if (y < 0) {
                    y = 0
                }
            }
            $ctxMenu.css({
                'top': y,
                'left': x
            });
            contextMenu.setEvents(items);
            $('.ctxMenu-item.haveMore').on('mouseenter', function () {
                var $item = $(this).find('>a');
                var $sub = $(this).find('>.ctxMenu-sub');
                var top = $item.offset().top;
                var left = $item.offset().left + $item.outerWidth();
                if (left + $sub.outerWidth() > contextMenu.getPageWidth()) {
                    left = $item.offset().left - $sub.outerWidth()
                }
                if (top + $sub.outerHeight() > contextMenu.getPageHeight()) {
                    top = top - $sub.outerHeight() + $item.outerHeight();
                    if (top < 0) {
                        top = 0
                    }
                }
                $(this).find('>.ctxMenu-sub').css({
                    'top': top,
                    'left': left,
                    'display': 'block'
                })
            }).on('mouseleave', function () {
                $(this).find('>.ctxMenu-sub').css('display', 'none')
            })
        }
    };
    $(document).off('click.ctxMenu').on('click.ctxMenu', function () {
        contextMenu.remove()
    });
    $('body').off('click.ctxMenuMore').on('click.ctxMenuMore', '.ctxMenu-item', function (e) {
        if ($(this).hasClass('haveMore')) {
            if (e !== void 0) {
                e.preventDefault();
                e.stopPropagation()
            }
        } else {
            contextMenu.remove()
        }
    });
    exports("contextMenu", contextMenu)
});