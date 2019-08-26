package cn.kcyf.pms.modular.business.controller;

import cn.kcyf.pms.core.controller.BasicController;
import cn.kcyf.pms.core.log.BussinessLog;
import cn.kcyf.pms.core.model.CatalogueNode;
import cn.kcyf.pms.core.model.ResponseData;
import cn.kcyf.pms.modular.business.entity.Catalogue;
import cn.kcyf.pms.modular.business.service.CatalogueService;
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
import java.util.List;

@Controller
@RequestMapping("/catalogue")
@Api(tags = "内容目录管理", description = "内容目录管理")
@RequiresRoles(value = "administrator")
public class CatalogueController extends BasicController {
    @Autowired
    private CatalogueService catalogueService;

    private String PREFIX = "/modular/business/content/catalogue/";


    @GetMapping("")
    @RequiresPermissions(value = "catalogue")
    public String index() {
        return PREFIX + "catalogue.html";
    }

    @GetMapping("/catalogue_add")
    @RequiresPermissions(value = "catalogue_add")
    public String catalogueAdd(Long parentId, Model model) {
        if (parentId != null && parentId.equals(0L)){
            parentId = null;
        }
        model.addAttribute("parentId", parentId);
        return PREFIX + "catalogue_add.html";
    }

    @GetMapping("/catalogue_edit")
    @RequiresPermissions(value = "catalogue_edit")
    public String catalogueUpdate(Long catalogueId, Model model) {
        model.addAttribute("catalogueId", catalogueId);
        String parentName = catalogueService.getParentName(catalogueId);
        model.addAttribute("parentName", parentName);
        return PREFIX + "catalogue_edit.html";
    }

    @GetMapping(value = "/treeSelect")
    @ResponseBody
    @ApiOperation("查询内容目录树形下拉列表")
    @RequiresPermissions(value = {"catalogue_add", "catalogue_edit"}, logical = Logical.OR)
    public List<CatalogueNode> treeSelect() {
        return catalogueService.tree();
    }

    @GetMapping(value = "/list")
    @ResponseBody
    @ApiOperation("查询内容目录树形结构")
    @RequiresPermissions(value = "catalogue_list")
    public ResponseData list(String name) {
        Criteria<Catalogue> criteria = new Criteria<Catalogue>();
        if (!StringUtils.isEmpty(name)) {
            criteria.add(Restrictions.like("fullName", name));
        }
        return ResponseData.list(catalogueService.findList(criteria));
    }

    @PostMapping(value = "/add")
    @ResponseBody
    @BussinessLog("新增内容目录")
    @ApiOperation("新增内容目录")
    @RequiresPermissions(value = "catalogue_add")
    public ResponseData add(@Valid Catalogue catalogue, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseData.error(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        create(catalogue);
        catalogueService.create(catalogue);
        return SUCCESS_TIP;
    }

    @PostMapping(value = "/edit")
    @ResponseBody
    @BussinessLog("修改内容目录")
    @ApiOperation("修改内容目录")
    @RequiresPermissions(value = "catalogue_edit")
    public ResponseData edit(@Valid Catalogue catalogue, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseData.error(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        Catalogue dbcatalogue = catalogueService.getOne(catalogue.getId());
        update(dbcatalogue);
        dbcatalogue.setFullName(catalogue.getFullName());
        dbcatalogue.setSimpleName(catalogue.getSimpleName());
        dbcatalogue.setDescription(catalogue.getDescription());
        dbcatalogue.setSort(catalogue.getSort());
        dbcatalogue.setParentId(catalogue.getParentId());
        catalogueService.update(dbcatalogue);
        return SUCCESS_TIP;
    }

    @GetMapping(value = "/detail/{catalogueId}")
    @ResponseBody
    @ApiOperation("查看内容目录详情")
    @RequiresPermissions(value = {"catalogue_detail", "catalogue_edit"}, logical = Logical.OR)
    public Catalogue detail(@PathVariable("catalogueId") Long catalogueId) {
        return catalogueService.getOne(catalogueId);
    }

}
