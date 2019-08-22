package cn.kcyf.pms.modular.system.service.impl;

import cn.kcyf.pms.core.model.DeptNode;
import cn.kcyf.pms.modular.system.dao.DeptDao;
import cn.kcyf.pms.modular.system.entity.Dept;
import cn.kcyf.pms.modular.system.service.DeptService;
import cn.kcyf.orm.jpa.dao.BasicDao;
import cn.kcyf.orm.jpa.service.AbstractBasicService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class DeptServiceImpl extends AbstractBasicService<Dept, Long> implements DeptService {
    @Autowired
    private DeptDao deptDao;

    @Override
    public BasicDao<Dept, Long> getRepository() {
        return deptDao;
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
        List<BigInteger> temp = deptDao.getChildIdById(id);
        if (temp != null) {
            for (BigInteger childId : temp){
                getChildId(Long.valueOf(childId.toString()), childIds);
            }
        }
    }

    @Transactional(readOnly = true)
    public String getParentName(Long id) {
        return deptDao.getParentName(id);
    }

    @Transactional(readOnly = true)
    public List<DeptNode> tree() {
        return buildTree(deptDao.findAll());
    }

    List<DeptNode> buildTree(List<Dept> depts){
        List<DeptNode> result = new ArrayList<DeptNode>();
        if (depts != null && !depts.isEmpty()){
            for (Dept dept : depts){
                if (dept.getParentId() == null){
                    DeptNode node = new DeptNode();
                    BeanUtils.copyProperties(dept, node);
                    buildChild(node, depts);
                    result.add(node);
                }
            }
        }
        return result;
    }

    void buildChild(DeptNode node, List<Dept> depts){
        if (depts != null && !depts.isEmpty()){
            if (node.getChildren() == null){
                node.setChildren(new ArrayList<DeptNode>());
            }
            for(Dept dept : depts){
                if (node.getId().equals(dept.getParentId())) {
                    DeptNode child = new DeptNode();
                    BeanUtils.copyProperties(dept, child);
                    node.getChildren().add(child);
                    buildChild(child, depts);
                }
            }
            if (node.getChildren().isEmpty()){
                node.setChildren(null);
            }
        }
    }
}
