package cn.kcyf.pms.modular.business.controller;

import cn.kcyf.orm.jpa.criteria.Criteria;
import cn.kcyf.orm.jpa.criteria.Restrictions;
import cn.kcyf.pms.core.controller.BasicController;
import cn.kcyf.pms.core.enumerate.Status;
import cn.kcyf.pms.core.model.ResponseData;
import cn.kcyf.pms.modular.business.entity.Catalogue;
import cn.kcyf.pms.modular.business.entity.Content;
import cn.kcyf.pms.modular.business.entity.Mode;
import cn.kcyf.pms.modular.business.entity.ModeField;
import cn.kcyf.pms.modular.business.service.ContentService;
import cn.kcyf.pms.modular.business.service.ModeFieldService;
import cn.kcyf.pms.modular.business.service.ModeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/content")
@Api(tags = "内容管理", description = "内容管理")
public class ContentController extends BasicController {
    @Autowired
    private ModeService modeService;
    @Autowired
    private ModeFieldService modeFieldService;
    @Autowired
    private ContentService contentService;

    private String PREFIX = "/modular/business/content/content/";

    private void setModes(Model model){
        Criteria<Mode> criteria = new Criteria<Mode>();
        criteria.add(Restrictions.eq("status", Status.ENABLE));
        model.addAttribute("modes", modeService.findList(criteria));
    }

    @GetMapping("")
    @RequiresPermissions(value = "content")
    public String index(Model model) {
        setModes(model);
        return PREFIX + "content.html";
    }

    @GetMapping("/content_add")
//    @RequiresPermissions(value = "content_add")
    public String contentAdd(Long modeId, Model model) {
        Criteria<ModeField> criteria = new Criteria<ModeField>();
        criteria.add(Restrictions.eq("mode.id", modeId));
        model.addAttribute("fields", modeFieldService.findList(criteria));
        return PREFIX + "content_add.html";
    }

    @GetMapping("/content_edit")
//    @RequiresPermissions(value = "content_edit")
    public String contentUpdate(Long contentId, Model model) {
        return PREFIX + "content_edit.html";
    }

    @GetMapping(value = "/list")
    @ResponseBody
    @ApiOperation("查询内容目录树形结构")
//    @RequiresPermissions(value = "content_list")
    public ResponseData list(String condition) {
        Criteria<Content> criteria = new Criteria<Content>();
        if (!StringUtils.isEmpty(condition)) {
            criteria.add(Restrictions.or(Restrictions.like("subject", condition), Restrictions.like("text", condition)));
        }
        return ResponseData.list(contentService.findList(criteria));
    }
}
