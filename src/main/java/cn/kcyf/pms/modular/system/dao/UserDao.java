package cn.kcyf.pms.modular.system.dao;

import cn.kcyf.pms.modular.system.entity.User;
import cn.kcyf.orm.jpa.dao.BasicDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends BasicDao<User, Long> {
    User findFirstByAccount(@Param("account") String account);

    @Query(value = "from User u where u.dept.id = :deptId")
    List<User> findAllByDeptId(@Param("deptId") Long deptId);
}
