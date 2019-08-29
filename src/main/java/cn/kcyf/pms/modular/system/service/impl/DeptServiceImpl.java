package cn.kcyf.pms.modular.system.service.impl;

import cn.kcyf.pms.core.enumerate.Status;
import cn.kcyf.pms.core.model.DeptNode;
import cn.kcyf.pms.core.model.ResponseData;
import cn.kcyf.pms.core.model.SuccessResponseData;
import cn.kcyf.pms.modular.system.dao.DeptDao;
import cn.kcyf.pms.modular.system.dao.UserDao;
import cn.kcyf.pms.modular.system.entity.Dept;
import cn.kcyf.pms.modular.system.entity.User;
import cn.kcyf.pms.modular.system.service.DeptService;
import cn.kcyf.orm.jpa.dao.BasicDao;
import cn.kcyf.orm.jpa.service.AbstractBasicService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class DeptServiceImpl extends AbstractBasicService<Dept, Long> implements DeptService {
    @Autowired
    private DeptDao deptDao;
    @Autowired
    private UserDao userDao;

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
    @Transactional
    public ResponseData freeze(Long deptId,Long userId) {
        final User user = userDao.getOne(userId);
        long CurrentDeptId = 0l;
        if (null != user.getDept() && null != user.getDept().getId() &&
                user.getDept().getId().longValue() == deptId.longValue()){
            CurrentDeptId = user.getDept().getId();
            return ResponseData.error("自己当前部门不允许被禁用!");
        }
        Dept dept = getOne(deptId);
        boolean flag = false;
        final List<String> deptName = new ArrayList<String>();
        List<Dept> childs = new ArrayList<Dept>();//
        getChilds(dept,childs);
        if (!CollectionUtils.isEmpty(childs)){
            for (Dept d : childs) {
                if (userDao.existsByDept(d)){
                    deptName.add(d.getFullName());
                }
                if (0l != CurrentDeptId && d.getId() == CurrentDeptId){
                    flag = true;
                }
            }
        }
        if(!flag && CollectionUtils.isEmpty(deptName)){
            for (Dept d : childs) {
                d.setStatus(Status.DISABLE);
                update(d);
            }
            return new SuccessResponseData();
        }else{
            StringBuilder sb = new StringBuilder();
            sb.append(flag?"有当前部门":"");
            if (!CollectionUtils.isEmpty(deptName)){
                sb.append(StringUtils.join(deptName,","));
            }
            return ResponseData.error(sb.toString()+"不允许被禁用!");
        }
    }

    private void getChilds(Dept dept, List<Dept> childs){
        childs.add(dept);
        List<Dept> temp = deptDao.findAllByParentId(dept.getId());
        if (temp != null) {
            for (Dept one : temp){
                getChilds(one, childs);
            }
        }
    }

    @Transactional
    public ResponseData unfreeze(Long deptId) {
        Dept dept = getOne(deptId);
        dept.setStatus(Status.ENABLE);
        update(dept);
        if (null != dept.getParentId()){
            updateParentEnable(dept.getParentId());
        }
        return new SuccessResponseData();
    }

    public void updateParentEnable(Long deptId){
        if (null != deptId) {
            Dept parent = getOne(deptId);
            if (parent.getStatus().equals(Status.ENABLE)){
                return;
            }
            parent.setStatus(Status.ENABLE);
            update(parent);
            if (null != parent.getParentId()){
                updateParentEnable(parent.getParentId());
            }
        }
    }



}
