package cn.kcyf.pms.modular.business.controller;

import cn.kcyf.orm.jpa.criteria.Criteria;
import cn.kcyf.orm.jpa.criteria.Restrictions;
import cn.kcyf.pms.core.constant.Constant;
import cn.kcyf.pms.core.controller.BasicController;
import cn.kcyf.pms.core.enumerate.Status;
import cn.kcyf.pms.core.log.BussinessLog;
import cn.kcyf.pms.core.model.ResponseData;
import cn.kcyf.pms.modular.business.entity.Mode;
import cn.kcyf.pms.modular.business.service.ModeService;
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

@Controller
@RequestMapping("/mode")
@Api(tags = "内容模型管理", description = "内容模型管理")
public class ModeController extends BasicController {
    private String PREFIX = "/modular/business/content/mode/";

    @Autowired
    private ModeService modeService;

    @GetMapping("")
    @RequiresPermissions(value = "mode")
    public String index() {
        return PREFIX + "mode.html";
    }

    @GetMapping("/mode_add")
    @RequiresPermissions(value = "mode_add")
    public String modeAdd() {
        return PREFIX + "mode_add.html";
    }

    @GetMapping("/mode_edit")
    @RequiresPermissions(value = "mode_edit")
    public String modeUpdate(Long modeId, Model model) {
        model.addAttribute("modeId", modeId);
        return PREFIX + "mode_edit.html";
    }

    @GetMapping(value = "/list")
    @ResponseBody
    @ApiOperation("查询内容模板树形结构")
    @RequiresPermissions(value = "mode_list")
    public ResponseData list(String condition) {
        Criteria<Mode> criteria = new Criteria<Mode>();
        if (!StringUtils.isEmpty(condition)) {
            criteria.add(Restrictions.or(Restrictions.like("name", condition), Restrictions.like("code", condition)));
        }
        return ResponseData.list(modeService.findList(criteria));
    }

    @PostMapping(value = "/add")
    @ResponseBody
    @BussinessLog("新增内容模板")
    @ApiOperation("新增内容模板")
    @RequiresPermissions(value = "mode_add")
    public ResponseData add(@Valid Mode mode, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseData.error(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        create(mode);
        mode.setStatus(Status.ENABLE);
        if (StringUtils.isEmpty(mode.getPicture())){
            mode.setPicture(Constant.DEFAULT_IMG);
        }
        modeService.create(mode);
        return SUCCESS_TIP;
    }

    @PostMapping(value = "/edit")
    @ResponseBody
    @BussinessLog("修改内容模板")
    @ApiOperation("修改内容模板")
    @RequiresPermissions(value = "mode_edit")
    public ResponseData edit(@Valid Mode mode, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseData.error(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        Mode dbmode = modeService.getOne(mode.getId());
        update(dbmode);
        dbmode.setCode(mode.getCode());
        dbmode.setName(mode.getName());
        dbmode.setTplCatalogue(mode.getTplCatalogue());
        dbmode.setTplContent(mode.getTplContent());
        dbmode.setName(mode.getName());
        dbmode.setPicture(mode.getPicture());
        dbmode.setDescription(mode.getDescription());
        dbmode.setSort(mode.getSort());
        dbmode.setCols(mode.getCols());
        modeService.update(dbmode);
        return SUCCESS_TIP;
    }

    @PostMapping("/freeze/{modeId}")
    @ResponseBody
    @BussinessLog("禁用内容模板")
    @ApiOperation("禁用内容模板")
    @RequiresPermissions(value = "mode_freeze")
    public ResponseData freeze(@PathVariable Long modeId) {
        Mode mode = modeService.getOne(modeId);
        mode.setStatus(Status.DISABLE);
        modeService.update(mode);
        return SUCCESS_TIP;
    }

    @PostMapping("/unfreeze/{modeId}")
    @ResponseBody
    @BussinessLog("启用内容模板")
    @ApiOperation("启用内容模板")
    @RequiresPermissions(value = "mode_freeze")
    public ResponseData unfreeze(@PathVariable Long modeId) {
        Mode mode = modeService.getOne(modeId);
        mode.setStatus(Status.ENABLE);
        modeService.update(mode);
        return SUCCESS_TIP;
    }

    @GetMapping(value = "/detail/{modeId}")
    @ResponseBody
    @ApiOperation("查看内容模板详情")
    @RequiresPermissions(value = {"mode_detail", "mode_edit"}, logical = Logical.OR)
    public ResponseData detail(@PathVariable("modeId") Long modeId) {
        return ResponseData.success(modeService.getOne(modeId));
    }
}
