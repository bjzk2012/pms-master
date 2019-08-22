package cn.kcyf.pms.core.scheduled;

import cn.kcyf.pms.core.enumerate.DayType;
import cn.kcyf.pms.core.util.WorkDayUtil;
import cn.kcyf.pms.modular.business.entity.Work;
import cn.kcyf.pms.modular.business.service.WorkService;
import cn.kcyf.pms.modular.system.entity.User;
import cn.kcyf.pms.modular.system.service.UserService;
import cn.kcyf.commons.utils.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class WorkTask {
    @Autowired
    private WorkService workService;
    @Autowired
    private UserService userService;

    @Scheduled(cron = "0 0 0 * * ? ")
//    @Scheduled(cron = "0/60 * * * * *")
    private void init(){
        Date today = new Date();
        if (DayType.WORKDAY.equals(WorkDayUtil.request(today))){
            List<User> users = userService.findAll();
            for (User user : users){
                Work work = new Work();
                work.setCode("WT" + System.currentTimeMillis() + RandomUtils.generateNumString(4));
                work.setToday(today);
                work.setCreateTime(today);
                work.setCreateUserId(user.getId());
                work.setCreateUserName(user.getName());
                workService.create(work);
            }
        }
    }
}
