package cn.kcyf.pms.modular.system.dao;

import cn.kcyf.pms.modular.system.entity.Dept;
import cn.kcyf.orm.jpa.dao.BasicDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface DeptDao extends BasicDao<Dept, Long> {

    @Query(value = "select id from sys_dept where parent_id = :id", nativeQuery = true)
    List<BigInteger> getChildIdById(@Param(value = "id") Long id);

    List<Dept> findAllByParentId(Long id);

    @Query(value = "select p.simple_name from sys_dept p left join sys_dept d on d.parent_id = p.id where d.id = :id", nativeQuery = true)
    String getParentName(@Param(value = "id") Long id);
}
