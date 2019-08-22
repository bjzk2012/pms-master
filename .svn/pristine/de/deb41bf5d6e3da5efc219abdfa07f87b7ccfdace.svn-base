package cn.kcyf.pms.modular.system.dao;

import cn.kcyf.pms.modular.system.entity.Visit;
import cn.kcyf.orm.jpa.dao.BasicDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface VisitDao extends BasicDao<Visit, Long> {
    @Query(value = "select substring(send_time, 1, 10) as `name`, count(1) as `count` from sys_visit where send_time between :startTime and :endTime group by substring(send_time, 1, 10)", nativeQuery = true)
    List<Map<String, String>> day(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    @Query(value = "select substring(send_time, 1, 7) as `name`, count(1) as `count` from sys_visit where send_time between :startTime and :endTime group by substring(send_time, 1, 7)", nativeQuery = true)
    List<Map<String, String>> month(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    @Query(value = "select substring(send_time, 1, 4) as `name`, count(1) as `count` from sys_visit where send_time between :startTime and :endTime group by substring(send_time, 1, 4)", nativeQuery = true)
    List<Map<String, String>> year(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    @Query(value = "select city as `name`, count(1) as `count` from sys_visit group by city", nativeQuery = true)
    List<Map<String, String>> city();

    @Query(value = "select substring(send_time, 12, 2) as `name`, count(1) as `count` from sys_visit where send_time between :startTime and :endTime group by substring(send_time, 12, 2)", nativeQuery = true)
    List<Map<String, String>> hour(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    @Query(value = "select substring(send_time, 6, 2) as `name`, count(1) as `count` from sys_visit where send_time between :startTime and :endTime group by substring(send_time, 6, 2)", nativeQuery = true)
    List<Map<String, String>> month2year(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    @Query(value = "select login_name as `name`, count(1) as `count` from sys_visit group by login_name", nativeQuery = true)
    List<Map<String, String>> uv();
}
