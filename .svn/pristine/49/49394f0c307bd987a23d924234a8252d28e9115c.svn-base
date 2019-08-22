package cn.kcyf.pms.modular.system.dao;

import cn.kcyf.pms.modular.system.entity.Dict;
import cn.kcyf.orm.jpa.dao.BasicDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface DictDao extends BasicDao<Dict, Long> {
    @Query(value = "select id from sys_dict where parent_id = :id", nativeQuery = true)
    List<BigInteger> getChildIdById(@Param(value = "id") Long id);

    Dict findFirstByParentIdIsNullAndCodeEquals(@Param(value = "code") String code);

    Boolean existsByCodeEqualsAndParentIdIsNull(@Param(value = "code") String code);

    Boolean existsByCodeEqualsAndParentIdEquals(@Param(value = "code") String code, @Param(value = "parentId") Long parentId);
}
