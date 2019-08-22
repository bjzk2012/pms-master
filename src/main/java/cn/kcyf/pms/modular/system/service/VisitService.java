package cn.kcyf.pms.modular.system.service;

import cn.kcyf.pms.modular.system.entity.Visit;
import cn.kcyf.orm.jpa.service.BasicService;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface VisitService extends BasicService<Visit, Long> {
    List<Map<String, String>> day(Date startTime, Date endTime);

    List<Map<String, String>> month(Date startTime, Date endTime);

    List<Map<String, String>> year(Date startTime, Date endTime);

    List<Map<String, String>> city();

    List<Map<String, String>> hour(Date startTime, Date endTime);

    List<Map<String, String>> month2year(Date startTime, Date endTime);

    List<Map<String, String>> uv();
}
