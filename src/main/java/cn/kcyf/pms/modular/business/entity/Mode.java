package cn.kcyf.pms.modular.business.entity;

import cn.kcyf.pms.core.enumerate.Status;
import cn.kcyf.orm.jpa.entity.TableDomain;
import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
@ToString
@NoArgsConstructor
@Entity
@Table(name = "c_mode")
@ApiModel("内容模型")
public class Mode extends TableDomain {
    /**
     * 模型名称
     */
    @Column(name = "name", nullable = false)
    @NotBlank(message = "模型名称不能为空")
    private String name;
    /**
     * 标识
     */
    @Column(name = "code", nullable = false)
    @NotBlank(message = "标识不能为空")
    private String code;
    /**
     * 目录模板（列表）
     */
    @Column(name = "tpl_catalogue")
    private String tplCatalogue;
    /**
     * 内容模板（详情）
     */
    @Column(name = "tpl_content")
    private String tplContent;
    /**
     * 图片
     */
    @Column(name = "picture")
    private String picture;
    /**
     * 栏目简介
     */
    @Column(name = "description", length = 1000)
    private String description;
    /**
     * 状态
     */
    @Column(name = "status")
    @Enumerated
    private Status status;

    public String getStatusMessage() {
        if (this.status != null) {
            return status.getMessage();
        }
        return "";
    }
    /**
     * 单行属性数
     */
    @Column(name = "cols")
    private Integer cols;
    /**
     * 排序号
     */
    @Column(name = "sort")
    private Integer sort;
}
