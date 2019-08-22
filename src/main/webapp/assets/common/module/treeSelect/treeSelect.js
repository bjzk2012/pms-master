
layui.define(['form', 'jquery'], function (exports) { //提示：模块也可以依赖其它模块，如：layui.define('layer', callback);
  var jQuery = layui.jquery,
      $ = jQuery,
      form = layui.form,
      _MOD = 'treeSelect',
      trss = {},
      TreeSelect = function () {
        this.v = '1.0.0';
      };

  TreeSelect.prototype.render = function (options) {
    var elem = options.elem,
        // 请求地址
        data = options.data,
        // 是否可多选
        checkbox = options.checkbox,
        // 请求方式
        type = options.type === undefined ? 'GET' : options.type,
        // 节点点击回调
        click = options.click,
        // 选择框选择或取消选择后的回调函数
        check = options.check,
        // 渲染成功后的回调函数
        success = options.success,
        // 占位符（提示信息）
        placeholder = options.placeholder === undefined ? '请选择' : options.placeholder,
        // 是否开启搜索
        search = options.search === undefined ? false : options.search,
        // 唯一id
        tmp = new Date().getTime(),
        DATA = {},
        selected = 'layui-form-selected',
        TREE_OBJ = undefined,
        TREE_INPUT_ID = 'treeSelect-input-' + tmp,
        TREE_INPUT_CLASS = 'layui-treeselect',
        TREE_SELECT_ID = 'layui-treeSelect-' + tmp,
        TREE_SELECT_CLASS = 'layui-treeSelect',
        TREE_SELECT_TITLE_ID = 'layui-select-title-' + tmp,
        TREE_SELECT_TITLE_CLASS = 'layui-select-title',
        TREE_SELECT_BODY_ID = 'layui-treeSelect-body-' + tmp,
        TREE_SELECT_BODY_CLASS = 'layui-treeSelect-body',
        TREE_SELECT_SEARCHED_CLASS = 'layui-treeSelect-search-ed';


    var a = {
      init: function(){
        $.ajax({
          url: data,
          type: type,
          dataType: 'json',
          success: function (d) {
            DATA = d;
            a.hideElem().input().toggleSelect().loadCss().preventEvent();
            $.fn.zTree.init($('#' + TREE_SELECT_BODY_ID), a.setting(), d);
            if (search) {
              a.searchParam();
            }
            TREE_OBJ = $.fn.zTree.getZTreeObj(TREE_SELECT_BODY_ID);
            if (success){
              var obj = {
                treeId: TREE_SELECT_ID,
                data: d
              };
              success(obj);
            }
          }
        });
        return a;
      },
      setting: function () {
        var setting = {
          callback: {
            onClick: a.onClick,
            onExpand: a.onExpand,
            onCollapse: a.onCollapse,
            onCheck: a.onCheck
          }
        };
        if(checkbox){
            setting.check = {
              enable: true
            };
        }
        return setting;
      },
      onCollapse: function () {
        a.focusInput();
      },
      onExpand: function () {
        a.focusInput();
      },
      focusInput: function () {
        $('#' + TREE_INPUT_ID).focus();
      },
      onClick: function(event, treeId, treeNode){
          event.preventDefault();
        var name = treeNode.name,
            id = treeNode.id,
            $input = $('#' + TREE_SELECT_TITLE_ID + ' input');
        $input.val(name);
        $('#' + TREE_SELECT_ID).removeClass(selected);

        if (click){
          var obj = {
            data: DATA,
            current: treeNode,
            treeId: TREE_SELECT_ID
          };
          click(obj);
        }
        return a;
      },
      onCheck:function(event, treeId, treeNode){
          event.preventDefault();
          var zZTreeObj = $.fn.zTree.getZTreeObj(TREE_SELECT_BODY_ID)
          var changeCheckedNodes = zZTreeObj.getChangeCheckedNodes();
          var checkedNodes = zZTreeObj.getCheckedNodes(true);
          var name = $.map(changeCheckedNodes, function (n) {
              return n.name;
          }).join(",");
          $('#' + TREE_SELECT_TITLE_ID + ' input').val(name);
          if (check){
              check(changeCheckedNodes, checkedNodes);
          }
      },
      hideElem: function () {
        $(elem).hide();
        return a;
      },
      input: function(){
        var readonly = '';
        if (!search) {
          readonly = 'readonly';
        }
        var selectHtml = '<div class="'+ TREE_SELECT_CLASS +' layui-unselect layui-form-select" id="'+ TREE_SELECT_ID +'">' +
            '<div class="'+ TREE_SELECT_TITLE_CLASS +'" id="'+ TREE_SELECT_TITLE_ID +'">' +
            ' <input type="text" id="'+ TREE_INPUT_ID +'" placeholder="'+ placeholder +'" value="" '+ readonly +' class="layui-input layui-unselect">' +
            '<i class="layui-edge"></i>' +
            '</div>' +
            '<div class="layui-anim layui-anim-upbit" style="">' +
            '<div class="'+ TREE_SELECT_BODY_CLASS +' ztree" id="'+ TREE_SELECT_BODY_ID +'"></div>' +
            '</div>' +
            '</div>';
        $(elem).parent().append(selectHtml);
        return a;
      },
      /**
       * 展开/折叠下拉框
       */
      toggleSelect: function () {
        var item = '#' + TREE_SELECT_TITLE_ID;
        a.event('click', item, function (e) {
          var $select = $('#' + TREE_SELECT_ID);
          if ($select.hasClass(selected)) {
            $select.removeClass(selected);
            $('#' + TREE_INPUT_ID).blur();
          } else {
            // 隐藏其他picker
            $('.layui-form-select').removeClass(selected);
            // 显示当前picker
            $select.addClass(selected);
          }
          e.stopPropagation();
        });
        $(document).click(function () {
          var $select = $('#' + TREE_SELECT_ID);
          if ($select.hasClass(selected)) {
            $select.removeClass(selected);
            $('#' + TREE_INPUT_ID).blur();
          }
        });
        return a;
      },
      // 模糊查询
      searchParam: function () {
        if (!search) {
          return;
        }
        var item = '#' + TREE_INPUT_ID;
        a.event('input propertychange', item, function (e) {
          var elem = e.target,
              t = $(elem).val();
          $('#' + TREE_SELECT_ID + ' li.' + TREE_SELECT_SEARCHED_CLASS).removeClass(TREE_SELECT_SEARCHED_CLASS);
          var nodes = TREE_OBJ.getNodesByParamFuzzy("name", t, null);
          if (t !== '') {
            a.checkNodes(nodes);
            // 隐藏非结果项
            var lis = $('#' + TREE_SELECT_ID + ' li[treenode]');
            for (var i = 0; i < lis.length; i++) {
              var oLi = lis.eq(i);
              if (!oLi.hasClass(TREE_SELECT_SEARCHED_CLASS)){
                oLi.hide();
              } else {
                oLi.show();
              }
            }
            // 无结果提示

          } else {
            $('#' + TREE_SELECT_ID + ' li[treenode]').show();
          }
        });
      },
      checkNodes: function (nodes) {
        for (var i = 0; i < nodes.length; i++) {
          var o = nodes[i],
              pid = o.parentTId,
              tid = o.tId;
          if (pid !== null){
            // 获取父节点
            $('#' + pid).addClass(TREE_SELECT_SEARCHED_CLASS);
            var pNode = TREE_OBJ.getNodesByParam("tId", pid, null);
            TREE_OBJ.expandNode(pNode[0], true, false, true);
          }
          $('#' + tid).addClass(TREE_SELECT_SEARCHED_CLASS);
        }
      },
      // 阻止Layui的一些默认事件
      preventEvent: function() {
        var item = '#' + TREE_SELECT_ID + ' .layui-anim';
        a.event('click', item, function (e) {
          e.stopPropagation();
        });
        return a;
      },
      loadCss: function () {
        var ztree_ex = '.layui-treeSelect .layui-anim::-webkit-scrollbar{width:6px;height:6px;background-color:#F5F5F5;}.layui-treeSelect .layui-anim::-webkit-scrollbar-track{box-shadow:inset 0 0 6px rgba(107,98,98,0.3);border-radius:10px;background-color:#F5F5F5;}.layui-treeSelect .layui-anim::-webkit-scrollbar-thumb{border-radius:10px;box-shadow:inset 0 0 6px rgba(107,98,98,0.3);background-color:#555;}.layui-treeSelect.layui-form-select .layui-anim{display:none;position:absolute;left:0;top:37px;z-index:9999;min-width:100%;border:1px solid #d2d2d2;max-height:300px;overflow-y:auto;background-color:#fff;border-radius:2px;box-shadow:0 2px 4px rgba(0,0,0,.12);box-sizing:border-box;}.layui-treeSelect.layui-form-selected .layui-anim{display:block;}',
            $head = $('head'),
            ztreeStyle = $head.find('style[ztree]');
        if (ztreeStyle.length === 0) {
          $head.append($('<style ztree>').append(ztree_ex))
        }
        return a;
      },
      event: function (evt, el, fn) {
        $('body').on(evt, el, fn);
      }
    };
    a.init();
    return new TreeSelect();
  };

  /**
   * 重新加载trerSelect
   * @param filter
   */
  TreeSelect.prototype.refresh = function (filter) {
      var treeObj = obj.treeObj(filter);
      treeObj.reAsyncChildNodes(null, "refresh");
  };

  /**
   * 选中节点，因为tree是异步加载，所以必须在success回调中调用checkNode函数，否则无法获取生成的DOM元素
   * @param filter lay-filter属性
   * @param id 选中的id
   */
  TreeSelect.prototype.checkNode = function(filter, id){
      var o = obj.filter(filter),
          treeInput = o.find('.layui-select-title input'),
          treeObj = obj.treeObj(filter),
          node = treeObj.getNodeByParam("id", id, null),
          name = node.name;
      treeInput.val(name);
      o.find('a[treenode_a]').removeClass('curSelectedNode');
      treeObj.selectNode(node);
  };
  TreeSelect.prototype.checkNodes = function(filter, ids){
      var o = obj.filter(filter),
          treeInput = o.find('.layui-select-title input'),
          treeObj = obj.treeObj(filter);
      if(ids){
          var names = [];
          $.each(ids, function(i, n){
              var node = treeObj.getNodeByParam("id", n, null),
                  name = node.name;
              names.push(name);
              treeObj.checkNode(node);
          });
          treeInput.val(names.join(","));
      }
  };

  /**
   * 获取zTree对象，可调用所有zTree函数
   * @param filter
   */
  TreeSelect.prototype.zTree = function (filter) {
    return obj.treeObj(filter);
  };

  var obj = {
    filter: function(filter){
      if (!filter) {
        layui.hint().error('filter 不能为空');
      }
      var tf = $('*[lay-filter='+ filter +']'),
          o = tf.next();
      return o;
    },
    treeObj: function (filter) {
      var o = obj.filter(filter),
          treeId = o.find('.layui-treeSelect-body').attr('id'),
          tree = $.fn.zTree.getZTreeObj(treeId);
      return tree;
    }
  };

  //输出接口
  var mod = new TreeSelect();
  exports(_MOD, mod);
});    