package cn.kcyf.pms.modular.system.service;

import cn.kcyf.pms.core.enumerate.AuthType;
import cn.kcyf.pms.modular.system.entity.AuthCode;
import cn.kcyf.orm.jpa.service.BasicService;

public interface AuthCodeService extends BasicService<AuthCode, Long> {
    AuthCode send(String mobile, AuthType type, String memberName, String content);
    Boolean check(String mobile, AuthType type, String content);
}
