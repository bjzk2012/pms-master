
package cn.kcyf.pms.modular.system.controller;

import cn.kcyf.pms.core.controller.BasicController;
import cn.kcyf.pms.core.enumerate.LogType;
import cn.kcyf.pms.core.log.BussinessLog;
import cn.kcyf.pms.core.model.ResponseData;
import cn.kcyf.pms.modular.system.entity.OperationLog;
import cn.kcyf.pms.modular.system.service.OperationLogService;
import cn.kcyf.commons.utils.DateUtils;
import cn.kcyf.orm.jpa.criteria.Criteria;
import cn.kcyf.orm.jpa.criteria.Restrictions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 日志管理的控制器
 *
 * @author Tom
 */
@Controller
@RequestMapping("/log")
@Api(tags = "日志管理", description = "日志管理")
public class LogController extends BasicController {

    private static String PREFIX = "/modular/system/log/";

    @Autowired
    private OperationLogService operationLogService;

    @GetMapping("")
    @RequiresPermissions(value = "log")
    public String index(Model model) {
        Map<String, String> types = new HashMap<String, String>();
        for (LogType type : LogType.values()){
            types.put(type.name(), type.getMessage());
        }
        model.addAttribute("types", types);
        return PREFIX + "log.html";
    }

    @GetMapping("/list")
    @ResponseBody
    @RequiresPermissions(value = "log_list")
    public ResponseData list(String timeLimit, String logName, LogType logType, int page, int limit) {
        Criteria<OperationLog> criteria = new Criteria<OperationLog>();
        if (!StringUtils.isEmpty(timeLimit)) {
            String[] split = timeLimit.split(" - ");
            criteria.add(Restrictions.gte("createTime", DateUtils.parse(split[0] + " 00:00:00", "yyyy-MM-dd HH:mm:ss")));
            criteria.add(Restrictions.lte("createTime", DateUtils.parse(split[1] + " 23:59:59", "yyyy-MM-dd HH:mm:ss")));
        }
        if (!StringUtils.isEmpty(logName)) {
            criteria.add(Restrictions.like("logName", logName));
        }
        if (logType != null) {
            criteria.add(Restrictions.eq("logType", logType));
        }
        return ResponseData.list(operationLogService.findList(criteria, PageRequest.of(page - 1, limit, new Sort(Sort.Direction.DESC, "createTime"))));
    }

    @PostMapping("/delete")
    @ResponseBody
    @BussinessLog("清空业务日志")
    @ApiOperation("清空业务日志")
    @RequiresPermissions(value = "log_delete")
    public ResponseData delete() {
        List<OperationLog> list = operationLogService.findAll();
        if (list != null && !list.isEmpty()){
            List<Long> ids = new ArrayList<Long>();
            for (OperationLog log : list){
                ids.add(log.getId());
            }
            operationLogService.deletes(ids);
        }
        return SUCCESS_TIP;
    }

    @GetMapping("/detail/{id}")
    @ResponseBody
    @ApiOperation("查看日志详情")
    @RequiresPermissions(value = "log_detail")
    public ResponseData detail(@PathVariable Long id) {
        return ResponseData.success(operationLogService.getOne(id));
    }
}
