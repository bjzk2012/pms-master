package cn.kcyf.pms.modular.business.service.impl;

import cn.kcyf.pms.core.model.CatalogueNode;
import cn.kcyf.pms.modular.business.dao.CatalogueDao;
import cn.kcyf.pms.modular.business.entity.Catalogue;
import cn.kcyf.pms.modular.business.service.CatalogueService;
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
public class CatalogueServiceImpl extends AbstractBasicService<Catalogue, Long> implements CatalogueService {
    @Autowired
    private CatalogueDao catalogueDao;

    @Override
    public BasicDao<Catalogue, Long> getRepository() {
        return catalogueDao;
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
        List<BigInteger> temp = catalogueDao.getChildIdById(id);
        if (temp != null) {
            for (BigInteger childId : temp){
                getChildId(Long.valueOf(childId.toString()), childIds);
            }
        }
    }

    @Transactional(readOnly = true)
    public String getParentName(Long id) {
        return catalogueDao.getParentName(id);
    }

    @Transactional(readOnly = true)
    public List<CatalogueNode> tree() {
        return buildTree(catalogueDao.findAll());
    }

    List<CatalogueNode> buildTree(List<Catalogue> catalogues){
        List<CatalogueNode> result = new ArrayList<CatalogueNode>();
        if (catalogues != null && !catalogues.isEmpty()){
            for (Catalogue catalogue : catalogues){
                if (catalogue.getParentId() == null){
                    CatalogueNode node = new CatalogueNode();
                    BeanUtils.copyProperties(catalogue, node);
                    buildChild(node, catalogues);
                    result.add(node);
                }
            }
        }
        return result;
    }

    void buildChild(CatalogueNode node, List<Catalogue> catalogues){
        if (catalogues != null && !catalogues.isEmpty()){
            if (node.getChildren() == null){
                node.setChildren(new ArrayList<CatalogueNode>());
            }
            for(Catalogue catalogue : catalogues){
                if (node.getId().equals(catalogue.getParentId())) {
                    CatalogueNode child = new CatalogueNode();
                    BeanUtils.copyProperties(catalogue, child);
                    node.getChildren().add(child);
                    buildChild(child, catalogues);
                }
            }
            if (node.getChildren().isEmpty()){
                node.setChildren(null);
            }
        }
    }
}
