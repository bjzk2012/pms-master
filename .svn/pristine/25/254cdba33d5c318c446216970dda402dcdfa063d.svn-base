package cn.kcyf.pms.modular.business.dao;

import cn.kcyf.pms.modular.business.entity.Catalogue;
import cn.kcyf.orm.jpa.dao.BasicDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface CatalogueDao extends BasicDao<Catalogue, Long> {

    @Query(value = "select id from sys_catalogue where parent_id = :id", nativeQuery = true)
    List<BigInteger> getChildIdById(@Param(value = "id") Long id);

    @Query(value = "select p.simple_name from sys_catalogue p left join sys_catalogue d on d.parent_id = p.id where d.id = :id", nativeQuery = true)
    String getParentName(@Param(value = "id") Long id);
}
