package cn.kcyf.pms.modular.system.controller;

import cn.kcyf.orm.jpa.criteria.Criteria;
import cn.kcyf.orm.jpa.criteria.Restrictions;
import cn.kcyf.pms.core.controller.BasicController;
import cn.kcyf.pms.core.enumerate.ReadType;
import cn.kcyf.pms.core.model.ResponseData;
import cn.kcyf.pms.modular.system.entity.NoticeRecord;
import cn.kcyf.pms.modular.system.service.NoticeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class MessageController extends BasicController {
    @Autowired
    private NoticeRecordService noticeRecordService;

    @GetMapping("/message")
    public String message() {
        return "/modular/frame/message.html";
    }

    @GetMapping("/messages")
    @ResponseBody
    public ResponseData messages() {
        Criteria<NoticeRecord> criteria = new Criteria<NoticeRecord>();
        criteria.add(Restrictions.eq("toId", getUser().getId()));
        List<NoticeRecord> records = noticeRecordService.findList(criteria, new Sort(Sort.Direction.DESC, "createTime"));
        return ResponseData.success(records);
    }

    @GetMapping("/messages/count")
    @ResponseBody
    public ResponseData messagesCount() {
        Criteria<NoticeRecord> criteria = new Criteria<NoticeRecord>();
        criteria.add(Restrictions.eq("toId", getUser().getId()));
        criteria.add(Restrictions.eq("isRead", ReadType.UNREAD));
        return ResponseData.success(noticeRecordService.countBy(criteria));
    }

    @PostMapping("/messages/read/{id}")
    @ResponseBody
    public ResponseData messagesRead(@PathVariable(value = "id") Long id) {
        NoticeRecord dbrecord = noticeRecordService.getOne(id);
        if (!dbrecord.getToId().equals(getUser().getId())) {
            return ResponseData.error("你无权阅读此消息");
        }
        dbrecord.setIsRead(ReadType.READ);
        noticeRecordService.update(dbrecord);
        return ResponseData.success(dbrecord);
    }

    @PostMapping("/messages/read/all")
    @ResponseBody
    public ResponseData messagesRead() {
        Criteria<NoticeRecord> criteria = new Criteria<NoticeRecord>();
        criteria.add(Restrictions.eq("toId", getUser().getId()));
        List<NoticeRecord> records = noticeRecordService.findList(criteria);
        if (records != null && !records.isEmpty()) {
            for (NoticeRecord record : records) {
                record.setIsRead(ReadType.READ);
                noticeRecordService.update(record);
            }
        }
        return SUCCESS_TIP;
    }
}
