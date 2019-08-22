package cn.kcyf.pms.modular.business.controller;

import cn.kcyf.pms.core.model.ChartTotalTimeComparator;
import cn.kcyf.pms.modular.business.entity.Work;
import cn.kcyf.pms.modular.business.entity.WorkAudit;
import cn.kcyf.pms.modular.business.service.WorkAuditService;
import cn.kcyf.pms.modular.business.service.WorkService;
import cn.kcyf.pms.core.controller.BasicController;
import cn.kcyf.pms.modular.system.entity.User;
import cn.kcyf.pms.modular.system.service.UserService;
import cn.kcyf.commons.utils.DateUtils;
import cn.kcyf.orm.jpa.criteria.Criteria;
import cn.kcyf.orm.jpa.criteria.Restrictions;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
@RequestMapping("/chart/total")
@Api(tags = "统计管理", description = "统计管理")
public class ChartTotalController extends BasicController {
    private static String PREFIX = "/modular/business/chart";
    @Autowired
    private UserService userService;
    @Autowired
    private WorkService workService;
    @Autowired
    private WorkAuditService workAuditService;

    @GetMapping("")
    public String total(String timeLimit, Model model) {
        // 获取当前用户角色列表
        Set<String> roles = getUser().getRoles();
        // 判断当前用户是否为超级管理员
        boolean isAdmin = roles.contains("administrator");
        // 获取当前用户对象
        User user = userService.getOne(getUser().getId());
        // 是否超级管理员
        // 是：查询除超级管理员以外的所有用户
        // 否：查询当前部门所有用户
        Criteria<User> criteria = new Criteria<User>();
        if (!isAdmin) {
            criteria.add(Restrictions.eq("dept.id", user.getDept().getId()));
        }
        criteria.add(Restrictions.ne("roles.code",  "administrator"));
        List<User> users = userService.findList(criteria);
        model.addAttribute("users", users);
        // 初始化时间
        Date today = new Date();
        Date startTime, endTime;
        if (!StringUtils.isEmpty(timeLimit)) {
            String[] split = timeLimit.split(" - ");
            startTime = DateUtils.parse(split[0], "yyyy-MM-dd");
            endTime = DateUtils.parse(split[1] + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
            model.addAttribute("timeLimit", timeLimit);
        } else {
            startTime = DateUtils.addTime(today, Calendar.DAY_OF_YEAR, -6);
            endTime = DateUtils.getEndTime(today);
            model.addAttribute("timeLimit", DateUtils.format(startTime, "yyyy-MM-dd") + " - " + DateUtils.format(endTime, "yyyy-MM-dd"));
        }
        // 初始化出一个以时间为key以工单列表为value的Map【为方便后续重组数据】
        Map<String, List<Work>> timelist = new HashMap<String, List<Work>>();
        int count = DateUtils.differentDays(startTime, endTime) + 1;
        // 初始化工单列表
        for (int i = 0; i < count; i++) {
            Date time = DateUtils.addTime(endTime, Calendar.DAY_OF_YEAR, 0 - i);
            timelist.put(DateUtils.format(time, "yyyy-MM-dd"), new ArrayList<Work>());
        }
        // 查询除所有的工单
        List<Work> list = workService.findAll();
        // 根据工单给Map中添加内容
        JSONArray result = new JSONArray();
        if (list != null && !list.isEmpty()) {
            for (Work work : list) {
                String time = DateUtils.format(work.getToday(), "yyyy-MM-dd");
                if(timelist.get(time) != null) {
                    timelist.get(time).add(work);
                }
            }
        }
        // 获取涉及的用户Id
        List<Long> userIds = new ArrayList<Long>();
        if (users != null && !users.isEmpty()) {
            for (User u : users) {
                userIds.add(u.getId());
            }
        }
        // 充组数据得出按日期分组后的工单列表
        for (String key : timelist.keySet()) {
            JSONObject object = new JSONObject();
            object.put("time", key);
            for (Work work : timelist.get(key)) {
                if (userIds.contains(work.getCreateUserId())) {
                    object.put("" + work.getCreateUserId(), work.getStatus());
                }
            }
            result.add(object);
        }
        List<JSONObject> objects = result.toJavaList(JSONObject.class);
        // 对列表进行排序【按时间倒叙】
        Collections.sort(objects, new ChartTotalTimeComparator());
        model.addAttribute("objects", objects);
        model.addAttribute("limit", objects.size());
        return PREFIX + "/total/total.html";
    }

    @GetMapping("/detail")
    public String total_detail(Long userId, Date time, Model model) {
        Criteria<Work> criteria = new Criteria<Work>();
        criteria.add(Restrictions.eq("createUserId", userId));
        criteria.add(Restrictions.eq("today", time));
        Work work = workService.getOne(criteria);
        model.addAttribute("work", work);
        return PREFIX + "/total/detail.html";
    }

    @GetMapping("/audits")
    public String total_detail_audits(Long workRecordId, Model model) {
        Criteria<WorkAudit> criteria = new Criteria<WorkAudit>();
        criteria.add(Restrictions.eq("workRecordId", workRecordId));
        model.addAttribute("audits", workAuditService.findList(criteria));
        return PREFIX + "/total/audits.html";
    }
}
