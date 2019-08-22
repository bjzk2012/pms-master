package cn.kcyf.pms.core.model;

import cn.kcyf.pms.modular.system.entity.Dict;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("字典树形节点")
public class DictNode {
    private Long id;
    /**
     * 父部门
     */
    private Long parentId;
    public Long getPId(){
        return parentId;
    }
    public Boolean getOpen(){
        return true;
    }
    /**
     * 名称
     */
    private String name;
    /**
     * 子节点
     */
    private List<Dict> children;
}
