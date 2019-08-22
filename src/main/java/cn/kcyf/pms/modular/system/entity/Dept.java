package cn.kcyf.pms.modular.system.entity;

import cn.kcyf.orm.jpa.entity.TableDomain;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 部门表
 *
 * @author Tom
 */
@Data
@ToString
@NoArgsConstructor
@Entity
@Table(name = "sys_dept")
@ApiModel("部门")
public class Dept extends TableDomain {
    /**
     * 父部门
     */
    @Column(name = "parent_id")
    private Long parentId;

    @JSONField(name = "pId")
    public Long getPId() {
        if (parentId == null) {
            return 0L;
        }
        return parentId;
    }

    /**
     * 简称
     */
    @Column(name = "simple_name", nullable = false)
    @NotBlank(message = "简称不能为空")
    @Size(min = 2, max = 20, message = "简称长度必须为2到20位")
    private String simpleName;

    @JSONField(name = "name")
    public String getName() {
        return simpleName;
    }

    @JSONField(name = "open")
    public Boolean getOpen() {
        return true;
    }

    /**
     * 全称
     */
    @Column(name = "full_name", nullable = false)
    @NotBlank(message = "全称不能为空")
    @Size(min = 2, max = 255, message = "全称长度必须为2到255位")
    private String fullName;
    /**
     * 描述
     */
    @Column(name = "description")
    private String description;
    /**
     * 排序
     */
    @Column(name = "sort")
    private Integer sort;
}
