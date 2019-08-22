package cn.kcyf.pms.core.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("文章目录树形节点")
public class CatalogueNode {
    private Long id;
    /**
     * 父目录
     */
    private Long parentId;

    public Long getPId() {
        return parentId;
    }

    /**
     * 简称
     */
    private String simpleName;

    public String getName() {
        return simpleName;
    }

    public Boolean getOpen() {
        return true;
    }

    /**
     * 全称
     */
    private String fullName;
    /**
     * 描述
     */
    private String description;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 子节点
     */
    private List<CatalogueNode> children;
}
