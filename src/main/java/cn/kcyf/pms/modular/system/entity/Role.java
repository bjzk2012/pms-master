package cn.kcyf.pms.modular.system.entity;

import cn.kcyf.pms.core.enumerate.Status;
import cn.kcyf.orm.jpa.entity.TableDomain;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * 角色表
 *
 * @author Tom
 */
@Data
@ToString
@NoArgsConstructor
@Entity
@Table(name = "sys_role")
@ApiModel("角色")
public class Role extends TableDomain {
    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    @Column(name = "name", nullable = false)
    private String name;
    /**
     * 标识
     */
    @NotBlank(message = "角色标识不能为空")
    @Column(name = "code", nullable = false)
    private String code;
    /**
     * 描述
     */
    @Column(name = "description")
    private String description;
    /**
     * 序号
     */
    @Column(name = "sort", nullable = false)
    private Integer sort;
    /**
     * 状态
     */
    @Column(name = "status", nullable = false)
    private Status status;
    public String getStatusMessage(){
        if (this.status != null){
            return this.status.getMessage();
        }
        return "";
    }
    /**
     * 角色权限关联表
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JSONField(serialize = false, deserialize = false)
    private Set<Menu> menus;
    public String getMenuId(){
        if (menus != null && !menus.isEmpty()){
            List<Long> menuIds = new ArrayList<Long>();
            for (Menu menu : this.menus){
                menuIds.add(menu.getId());
            }
            return StringUtils.join(menuIds, ",");
        }
        return "";
    }

}
