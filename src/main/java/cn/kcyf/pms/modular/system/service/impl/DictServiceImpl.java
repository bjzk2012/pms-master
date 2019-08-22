package cn.kcyf.pms.modular.system.service.impl;

import cn.kcyf.pms.modular.system.dao.DictDao;
import cn.kcyf.pms.modular.system.entity.Dict;
import cn.kcyf.pms.modular.system.service.DictService;
import cn.kcyf.orm.jpa.dao.BasicDao;
import cn.kcyf.orm.jpa.service.AbstractBasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class DictServiceImpl extends AbstractBasicService<Dict, Long> implements DictService {
    @Autowired
    private DictDao dictDao;
    public BasicDao<Dict, Long> getRepository() {
        return dictDao;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        List<Long> childIds = new ArrayList<Long>();
        getChildId(id, childIds);
        deletes(childIds);
    }

    private void getChildId(Long id, List<Long> childIds){
        childIds.add(id);
        List<BigInteger> temp = dictDao.getChildIdById(id);
        if (temp != null) {
            for (BigInteger childId : temp){
                getChildId(Long.valueOf(childId.toString()), childIds);
            }
        }
    }

    @Transactional(readOnly = true)
    public Boolean existsByCode(String code, Long parentId) {
        if (parentId == null || parentId.equals(0L)) {
            return dictDao.existsByCodeEqualsAndParentIdIsNull(code);
        }
        return dictDao.existsByCodeEqualsAndParentIdEquals(code, parentId);
    }
}
