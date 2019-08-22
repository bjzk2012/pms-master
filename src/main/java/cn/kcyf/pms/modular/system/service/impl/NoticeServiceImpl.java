package cn.kcyf.pms.modular.system.service.impl;

import cn.kcyf.commons.utils.ArrayUtils;
import cn.kcyf.orm.jpa.criteria.Criteria;
import cn.kcyf.orm.jpa.criteria.Restrictions;
import cn.kcyf.orm.jpa.dao.BasicDao;
import cn.kcyf.orm.jpa.service.AbstractBasicService;
import cn.kcyf.pms.core.enumerate.ReadType;
import cn.kcyf.pms.modular.system.dao.NoticeDao;
import cn.kcyf.pms.modular.system.dao.NoticeRecordDao;
import cn.kcyf.pms.modular.system.entity.Notice;
import cn.kcyf.pms.modular.system.entity.NoticeRecord;
import cn.kcyf.pms.modular.system.entity.User;
import cn.kcyf.pms.modular.system.service.NoticeService;
import cn.kcyf.pms.modular.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoticeServiceImpl extends AbstractBasicService<Notice, Long> implements NoticeService {
    @Autowired
    private NoticeDao noticeDao;
    @Autowired
    private NoticeRecordDao noticeRecordDao;
    @Autowired
    private UserService userService;
    public BasicDao<Notice, Long> getRepository() {
        return noticeDao;
    }

    @Transactional
    public void create(Notice notice, int totype, String userId, String roleId) {
        List<Long> userIds = new ArrayList<Long>();
        Criteria<User> criteria = new Criteria<User>();
        criteria.add(Restrictions.ne("roles.code", "administrator"));
        switch (totype){
            case 2:
                if (StringUtils.isEmpty(roleId)){
                    throw new RuntimeException("角色未选择");
                }
                criteria.add(Restrictions.in("roles.id", ArrayUtils.convertStrArrayToLong(roleId.split(","))));
                break;
            case 3:
                if (StringUtils.isEmpty(userId)){
                    throw new RuntimeException("用户未选择");
                }
                userIds = ArrayUtils.convertStrArrayToLong(userId.split(","));
                break;
        }
        if (totype != 3) {
            List<User> users = userService.findList(criteria);
            if (users != null && !users.isEmpty()) {
                for (User user: users){
                    userIds.add(user.getId());
                }
            }
        }
        if (userIds.isEmpty()){
            throw new RuntimeException("接收方不能为空");
        }
        noticeDao.save(notice);
        for (Long id : userIds){
            NoticeRecord record = new NoticeRecord();
            record.setCreateUserId(notice.getCreateUserId());
            record.setCreateUserName(notice.getCreateUserName());
            record.setFromName(notice.getType().getMessage());
            record.setToId(id);
            record.setNotice(notice);
            record.setIsRead(ReadType.UNREAD);
            noticeRecordDao.save(record);
        }
    }
}
