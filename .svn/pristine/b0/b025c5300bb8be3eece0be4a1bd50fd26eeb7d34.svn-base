package cn.kcyf.pms.modular.business.service;

import cn.kcyf.pms.core.model.CatalogueNode;
import cn.kcyf.pms.modular.business.entity.Catalogue;
import cn.kcyf.orm.jpa.service.BasicService;

import java.util.List;

public interface CatalogueService extends BasicService<Catalogue, Long> {
    String getParentName(Long id);
    List<CatalogueNode> tree();
}
