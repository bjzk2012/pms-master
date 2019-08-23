package cn.kcyf.pms.modular.business.controller;

import cn.kcyf.orm.jpa.criteria.Criteria;
import cn.kcyf.orm.jpa.criteria.Restrictions;
import cn.kcyf.pms.core.constant.Constant;
import cn.kcyf.pms.core.controller.BasicController;
import cn.kcyf.pms.core.enumerate.FieldType;
import cn.kcyf.pms.core.enumerate.QuestionCategory;
import cn.kcyf.pms.core.enumerate.Status;
import cn.kcyf.pms.core.log.BussinessLog;
import cn.kcyf.pms.core.model.ResponseData;
import cn.kcyf.pms.modular.business.entity.Mode;
import cn.kcyf.pms.modular.business.entity.ModeField;
import cn.kcyf.pms.modular.business.service.ModeFieldService;
import cn.kcyf.pms.modular.business.service.ModeService;
import cn.kcyf.pms.modular.system.entity.Dict;
import cn.kcyf.pms.modular.system.service.DictService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/modefield")
@Api(tags = "内容模型管理", description = "内容模型管理")
public class ModeFieldController extends BasicController {
    private String PREFIX = "/modular/business/content/modefield/";

    @Autowired
    private ModeFieldService modefieldService;
    @Autowired
    private DictService dictService;

    private void setTypes(Model model){
        Map<String, String> types = new HashMap<String, String>();
        for (FieldType type : FieldType.values()) {
            types.put(type.name(), type.getMessage());
        }
        model.addAttribute("types", types);
    }

    private void setDicts(Model model){
        Criteria<Dict> criteria = new Criteria<Dict>();
        List<Dict> dicts = dictService.findList(criteria);
        model.addAttribute("dicts", dicts);
    }

    @GetMapping("/modefield_add")
    @RequiresPermissions(value = "modefield_add")
    public String modefieldAdd(Long modeId, Model model) {
        model.addAttribute("modeId", modeId);
        setTypes(model);
        setDicts(model);
        return PREFIX + "modefield_add.html";
    }

    @GetMapping("/modefield_edit")
    @RequiresPermissions(value = "modefield_edit")
    public String modefieldUpdate(Long modefieldId, Model model) {
        model.addAttribute("modefieldId", modefieldId);
        return PREFIX + "modefield_edit.html";
    }

    @GetMapping(value = "/list")
    @ResponseBody
    @ApiOperation("查询内容模板属性树形结构")
    @RequiresPermissions(value = "modefield_list")
    public ResponseData list(Long modeId, String condition) {
        Criteria<ModeField> criteria = new Criteria<ModeField>();
        if (!StringUtils.isEmpty(condition)) {
            criteria.add(Restrictions.or(Restrictions.like("field", condition), Restrictions.like("label", condition)));
        }
        if (modeId == null || modeId.equals(0L)) {
            criteria.add(Restrictions.isNull("mode.id"));
        } else {
            criteria.add(Restrictions.eq("mode.id", modeId));
        }
        return ResponseData.list(modefieldService.findList(criteria));
    }

    @PostMapping(value = "/add")
    @ResponseBody
    @BussinessLog("新增内容模板属性")
    @ApiOperation("新增内容模板属性")
    @RequiresPermissions(value = "modefield_add")
    public ResponseData add(@Valid ModeField modefield, @NotBlank(message = "请选择内容模板") Long modeId, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseData.error(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        create(modefield);
        modefield.setStatus(Status.ENABLE);
        modefieldService.create(modefield);
        return SUCCESS_TIP;
    }

    @PostMapping(value = "/edit")
    @ResponseBody
    @BussinessLog("修改内容模板属性")
    @ApiOperation("修改内容模板属性")
    @RequiresPermissions(value = "modefield_edit")
    public ResponseData edit(@Valid ModeField modefield, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseData.error(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        ModeField dbmodefield = modefieldService.getOne(modefield.getId());
        update(dbmodefield);
        dbmodefield.setSort(modefield.getSort());
        modefieldService.update(dbmodefield);
        return SUCCESS_TIP;
    }

    @PostMapping("/freeze/{modefieldId}")
    @ResponseBody
    @BussinessLog("禁用内容模板属性")
    public ResponseData freeze(@PathVariable Long modefieldId) {
        ModeField modefield = modefieldService.getOne(modefieldId);
        modefield.setStatus(Status.DISABLE);
        modefieldService.update(modefield);
        return SUCCESS_TIP;
    }

    @PostMapping("/unfreeze/{modefieldId}")
    @ResponseBody
    @BussinessLog("启用内容模板属性")
    public ResponseData unfreeze(@PathVariable Long modefieldId) {
        ModeField modefield = modefieldService.getOne(modefieldId);
        modefield.setStatus(Status.ENABLE);
        modefieldService.update(modefield);
        return SUCCESS_TIP;
    }

    @GetMapping(value = "/detail/{modefieldId}")
    @ResponseBody
    @ApiOperation("查看内容模板属性详情")
    @RequiresPermissions(value = {"modefield_detail", "modefield_edit"}, logical = Logical.OR)
    public ResponseData detail(@PathVariable("modefieldId") Long modefieldId) {
        return ResponseData.success(modefieldService.getOne(modefieldId));
    }
}
