
package cn.kcyf.pms.modular.system.controller;

import cn.kcyf.pms.core.controller.BasicController;
import cn.kcyf.pms.core.log.BussinessLog;
import cn.kcyf.pms.core.model.DictNode;
import cn.kcyf.pms.core.model.ResponseData;
import cn.kcyf.pms.modular.system.entity.Dict;
import cn.kcyf.pms.modular.system.service.DictService;
import cn.kcyf.orm.jpa.criteria.Criteria;
import cn.kcyf.orm.jpa.criteria.Restrictions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 字典控制器
 *
 * @author Tom
 */
@Controller
@RequestMapping("/dict")
@Api(tags = "字典管理", description = "字典管理")
@RequiresRoles(value = "administrator")
public class DictController extends BasicController {

    private String PREFIX = "/modular/system/dict/";

    @Autowired
    private DictService dictService;

    @GetMapping("")
    @RequiresPermissions(value = "dict")
    public String index() {
        return PREFIX + "dict.html";
    }

    @GetMapping(value = "/dict_add")
    @RequiresPermissions(value = "dict_add")
    public String dictAdd() {
        return PREFIX + "dict_add.html";
    }

    @GetMapping(value = "/dict_edit")
    @RequiresPermissions(value = "dict_edit")
    public String dictEdit(Long dictId, Model model) {
        model.addAttribute("dictId", dictId);
        return PREFIX + "dict_edit.html";
    }

    @GetMapping(value = "/treeSelect")
    @ResponseBody
    @ApiOperation("查询字典树形下拉列表")
    @RequiresPermissions(value = {"dict_add", "dict_edit"}, logical = Logical.OR)
    public List<DictNode> treeSelect() {
        DictNode root = new DictNode();
        root.setId(0L);
        root.setName("顶级菜单");
        Criteria<Dict> criteria = new Criteria<Dict>();
        criteria.add(Restrictions.isNull("parentId"));
        List<Dict> list = dictService.findList(criteria);
        root.setChildren(list);
        List<DictNode> result = new ArrayList<DictNode>();
        result.add(root);
        return result;
    }

    @GetMapping(value = "/list")
    @ResponseBody
    @ApiOperation("查询字典树形列表")
    @RequiresPermissions(value = "dict_list")
    public ResponseData list(String condition) {
        Criteria<Dict> criteria = new Criteria<Dict>();
        if (!StringUtils.isEmpty(condition)) {
            criteria.add(Restrictions.or(Restrictions.like("name", condition), Restrictions.like("code", condition), Restrictions.like("description", condition)));
        }
        return ResponseData.list(dictService.findList(criteria));
    }

    @PostMapping(value = "/add")
    @ResponseBody
    @BussinessLog("新增字典")
    @ApiOperation("新增字典")
    @RequiresPermissions(value = "dict_add")
    public ResponseData add(@Valid Dict dict, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseData.error(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        create(dict);
        if (dict.getParentId() != null && dict.getParentId().equals(0L)) {
            dict.setParentId(null);
            dict.setParentName(null);
        }
        if (dictService.existsByCode(dict.getCode(), dict.getParentId())){
            return ResponseData.error("字典编码已存在");
        }
        dictService.create(dict);
        return SUCCESS_TIP;
    }

    @PostMapping(value = "/edit")
    @ResponseBody
    @BussinessLog("修改字典")
    @ApiOperation("修改字典")
    @RequiresPermissions(value = "dict_edit")
    public ResponseData edit(@Valid Dict dict, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseData.error(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        Dict dbdict = dictService.getOne(dict.getId());
        update(dbdict);
        if (dict.getParentId() != null && dict.getParentId().equals(0L)) {
            dbdict.setParentId(null);
            dbdict.setParentName(null);
        } else {
            dbdict.setParentId(dict.getParentId());
            dbdict.setParentName(dict.getParentName());
        }
        if (dictService.existsByCode(dict.getCode(), dict.getParentId())){
            return ResponseData.error("字典编码已存在");
        }
        dbdict.setCode(dict.getCode());
        dbdict.setName(dict.getName());
        dbdict.setDescription(dict.getDescription());
        dbdict.setSort(dict.getSort());
        dictService.update(dbdict);
        return SUCCESS_TIP;
    }

    @PostMapping(value = "/delete/{dictId}")
    @ResponseBody
    @BussinessLog("删除字典")
    @ApiOperation("删除字典")
    @RequiresPermissions(value = "dict_delete")
    public ResponseData delete(@PathVariable Long dictId) {
        dictService.delete(dictId);
        return SUCCESS_TIP;
    }

    @GetMapping("/detail/{dictId}")
    @ResponseBody
    @ApiOperation("查看字典详情")
    @RequiresPermissions(value = "dict_detail")
    public ResponseData detail(@PathVariable Long dictId) {
        Dict dict = dictService.getOne(dictId);
        return ResponseData.success(dict);
    }

}
