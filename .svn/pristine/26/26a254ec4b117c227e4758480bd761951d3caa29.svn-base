package cn.kcyf.pms.modular.system.service.impl;

import cn.kcyf.pms.core.model.MenuNode;
import cn.kcyf.pms.modular.system.dao.MenuDao;
import cn.kcyf.pms.modular.system.dao.UserDao;
import cn.kcyf.pms.modular.system.entity.Role;
import cn.kcyf.pms.modular.system.entity.User;
import cn.kcyf.pms.modular.system.service.UserService;
import cn.kcyf.orm.jpa.dao.BasicDao;
import cn.kcyf.orm.jpa.service.AbstractBasicService;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl extends AbstractBasicService<User, Long> implements UserService {
    /**
     * 加密方式
     */
    @Value("${shiro.password.algorithmName}")
    public String algorithmName;

    /**
     * 循环次数
     */
    @Value("${shiro.password.hashIterations}")
    public int hashIterations;
    @Autowired
    private UserDao userDao;
    @Autowired
    private MenuDao menuDao;

    @Override
    public BasicDao<User, Long> getRepository() {
        return userDao;
    }

    @Transactional(readOnly = true)
    public List<MenuNode> getUserMenus(Long userId) {
        User user = userDao.getOne(userId);
        Set<Role> roles = user.getRoles();
        if (roles == null || roles.isEmpty()){
            return new ArrayList<MenuNode>();
        }
        List<Long> roleIds = new ArrayList<Long>();
        for (Role role : roles){
            roleIds.add(role.getId());
        }
        return MenuServiceImpl.buildTree(menuDao.findByRoleIds(Arrays.asList(new Integer[]{0}), roleIds));
    }

    public String md5(String credentials, String saltSource) {
        return new SimpleHash(algorithmName, credentials, ByteSource.Util.bytes(saltSource), hashIterations).toHex();
    }

    @Transactional(readOnly = true)
    public List<User> findAllByDeptId(Long deptId) {
        return userDao.findAllByDeptId(deptId);
    }

    @Override
    @Transactional
    public void delete(Long userId) {
        User user = userDao.getOne(userId);
        super.update(user);
    }
}
