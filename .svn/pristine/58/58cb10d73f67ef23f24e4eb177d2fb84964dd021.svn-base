package cn.kcyf.pms.modular.system.dao;

import cn.kcyf.pms.modular.system.entity.Menu;
import cn.kcyf.orm.jpa.dao.BasicDao;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;

@Repository
public interface MenuDao extends BasicDao<Menu, Long> {
    @Query(value = "select m.* from sys_menu m left join sys_role_menus rm on rm.menus_id = m.id where m.menu_flag in (:menuFlag) and rm.role_id in (:roleIds)", nativeQuery = true)
    List<Menu> findByRoleIds(@Param("menuFlag") List<Integer> menuFlag, @Param("roleIds") List<Long> roleIds);

    @Query(value = "select id from sys_menu where parent_id = :id", nativeQuery = true)
    List<BigInteger> getChildIdById(@Param(value = "id") Long id);

    @Query(value = "update sys_menu set status = :status where id in (:ids)", nativeQuery = true)
    @Modifying
    void setStatusByIdIn(@Param(value = "status") int status, @Param(value = "ids") List<Long> ids);

    Set<Menu> findAllByIdIn(List<Long> ids);
}
