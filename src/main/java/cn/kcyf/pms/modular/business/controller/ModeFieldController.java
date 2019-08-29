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
import org.springframework.data.domain.Sort;
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
    private ModeService modeService;
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
        criteria.add(Restrictions.isNull("parentId"));
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
        setTypes(model);
        setDicts(model);
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
        return ResponseData.list(modefieldService.findList(criteria, new Sort(Sort.Direction.ASC, "sort")));
    }

    @PostMapping(value = "/add")
    @ResponseBody
    @BussinessLog("新增内容模板属性")
    @ApiOperation("新增内容模板属性")
    @RequiresPermissions(value = "modefield_add")
    public ResponseData add(@Valid ModeField modefield, @NotBlank(message = "请选择内容模板") Long modeId, Long dictId, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseData.error(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        create(modefield);
        Criteria<ModeField> criteria = new Criteria<ModeField>();
        criteria.add(Restrictions.eq("field", modefield.getField()));
        if (modefieldService.countBy(criteria) > 0){
            return ResponseData.error("内容模板属性已存在，请更换属性名再试");
        }
        modefield.setStatus(Status.ENABLE);
        if (!modefield.getCustom() && dictId != null) {
            modefield.setDict(dictService.getOne(dictId));
        }
        modefield.setRequired(modefield.getRequired() == null ? false : modefield.getRequired());
        modefield.setSingle(modefield.getSingle() == null ? false : modefield.getSingle());
        modefield.setDisplay(modefield.getDisplay() == null ? false : modefield.getDisplay());
        modefield.setMode(modeService.getOne(modeId));
        modefieldService.create(modefield);
        return SUCCESS_TIP;
    }

    @PostMapping(value = "/edit")
    @ResponseBody
    @BussinessLog("修改内容模板属性")
    @ApiOperation("修改内容模板属性")
    @RequiresPermissions(value = "modefield_edit")
    public ResponseData edit(@Valid ModeField modefield, @NotBlank(message = "请选择内容模板") Long modeId, Long dictId, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseData.error(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        ModeField dbmodefield = modefieldService.getOne(modefield.getId());
        dbmodefield.setType(modefield.getType());
        dbmodefield.setLabel(modefield.getLabel());
        dbmodefield.setDefValue(modefield.getDefValue());
        dbmodefield.setHelp(modefield.getHelp());
        dbmodefield.setRequired(modefield.getRequired() == null ? false : modefield.getRequired());
        dbmodefield.setSingle(modefield.getSingle() == null ? false : modefield.getSingle());
        dbmodefield.setDisplay(modefield.getDisplay() == null ? false : modefield.getDisplay());
        dbmodefield.setSort(modefield.getSort());
        if (modeId != null && !modeId.equals(dbmodefield.getModeId())){
            dbmodefield.setMode(modeService.getOne(modeId));
        }
        if (modefield.getType().equals(FieldType.TEXT) || modefield.getType().equals(FieldType.PASSWORD) || modefield.getType().equals(FieldType.NUMBER) || modefield.getType().equals(FieldType.TEXTAREA)){
            dbmodefield.setMinLength(modefield.getMinLength());
            dbmodefield.setMaxLength(modefield.getMaxLength());
        }
        if (modefield.getType().equals(FieldType.NUMBER)){
            dbmodefield.setMin(modefield.getMin());
            dbmodefield.setMax(modefield.getMax());
        }
        if (modefield.getType().equals(FieldType.COMBOBOX) || modefield.getType().equals(FieldType.CHECKBOX) || modefield.getType().equals(FieldType.SWITCH) || modefield.getType().equals(FieldType.REDIO)){
            if (modefield.getCustom()){
                dbmodefield.setOptKeys(modefield.getOptKeys());
                dbmodefield.setOptValues(modefield.getOptValues());
            } else {
                dbmodefield.setDict(dictService.getOne(dictId));
            }
        }
        if (modefield.getType().equals(FieldType.TEXTAREA)){
            dbmodefield.setCols(modefield.getCols());
            dbmodefield.setRows(modefield.getRows());
        }
        if (modefield.getType().equals(FieldType.IMAGE)){
            dbmodefield.setWidth(modefield.getWidth());
            dbmodefield.setHeight(modefield.getHeight());
        }
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

    @PostMapping("/unsingle/{modefieldId}")
    @ResponseBody
    @BussinessLog("取消内容模板属性独占")
    public ResponseData unsingle(@PathVariable Long modefieldId) {
        ModeField modefield = modefieldService.getOne(modefieldId);
        modefield.setSingle(false);
        modefieldService.update(modefield);
        return SUCCESS_TIP;
    }

    @PostMapping("/single/{modefieldId}")
    @ResponseBody
    @BussinessLog("内容模板属性独占")
    public ResponseData single(@PathVariable Long modefieldId) {
        ModeField modefield = modefieldService.getOne(modefieldId);
        modefield.setSingle(true);
        modefieldService.update(modefield);
        return SUCCESS_TIP;
    }



    @PostMapping("/sort/{modefieldId}")
    @ResponseBody
    @BussinessLog("内容模板属性排序")
    public ResponseData sort(@PathVariable Long modefieldId, Integer sort) {
        ModeField modefield = modefieldService.getOne(modefieldId);
        modefield.setSort(sort);
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
