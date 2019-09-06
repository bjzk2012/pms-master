package cn.kcyf.pms.modular.business.controller;

import cn.kcyf.orm.jpa.criteria.Criteria;
import cn.kcyf.orm.jpa.criteria.Restrictions;
import cn.kcyf.pms.core.controller.BasicController;
import cn.kcyf.pms.core.enumerate.QuestionCategory;
import cn.kcyf.pms.core.enumerate.QuestionStatus;
import cn.kcyf.pms.core.model.ResponseData;
import cn.kcyf.pms.modular.business.entity.Question;
import cn.kcyf.pms.modular.business.service.QuestionService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/sightseer")
@Api(tags = "游客中心", description = "游客中心")
public class SightseerController extends BasicController {

    @Autowired
    private QuestionController questionController;
    @Autowired
    private QuestionService questionService;

    @GetMapping("/index")
    public String userInfo(Model model) {
        questionController.setModel(model);
        return "/sightseer/index.html";
    }

    /**
     * 我的反馈
     * @param condition
     * @param projectId
     * @param category
     * @param submitTimeLimit
     * @param phone
     * @param status
     * @param page
     * @param limit
     * @return
     */
    @GetMapping(value = "/myoriginate/list")
    @ResponseBody
    public ResponseData myoriginateList(String condition, Long projectId, QuestionCategory category, String submitTimeLimit, String phone, QuestionStatus status, int page, int limit) {
        Criteria<Question> criteria = questionController.createCriteria(condition, projectId, category, submitTimeLimit, phone, status);
        criteria.add(Restrictions.eq("sponsor", getUser().getAccount()));
        return ResponseData.list(questionService.findList(criteria, PageRequest.of(page - 1, limit, new Sort(Sort.Direction.DESC, "time"))));
    }
}
