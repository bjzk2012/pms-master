package cn.kcyf.pms.modular.business.entity;

import cn.kcyf.pms.modular.system.entity.User;
import cn.kcyf.orm.jpa.entity.TableDomain;
import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@ToString
@NoArgsConstructor
@Entity
@Table(name = "k_key")
@ApiModel("口令")
public class Key extends TableDomain {
    /**
     * 项目
     */
    @ManyToOne
    @JoinColumn(name = "project_id")
    @JSONField(serialize = false, deserialize = false)
    private Project project;

    public Long getProjectId(){
        return project.getId();
    }
    public String getProjectName(){
        return project.getName();
    }
    /**
     * 口令名称
     */
    @NotBlank(message = "口令名称不能为空")
    @Column(name = "name", nullable = false)
    private String name;
    /**
     * 口令用途
     */
    @NotBlank(message = "口令用途不能为空")
    @Column(name = "useway", nullable = false)
    private String useway;
    /**
     * 口令账号
     */
    @NotBlank(message = "口令账号不能为空")
    @Column(name = "account", nullable = false)
    private String account;
    /**
     * 口令密码
     */
    @Column(name = "password", nullable = false)
    @JSONField(serialize = false, deserialize = false)
    private String password;
    /**
     * 管理员
     */
    @ManyToOne
    @JoinColumn(name = "manager_id")
    @JSONField(serialize = false, deserialize = false)
    private User manager;
    public Long getManagerId(){
        return manager.getId();
    }
    public String getManagerName(){
        return manager.getName();
    }
    /**
     * 备份管理员
     */
    @ManyToOne
    @JoinColumn(name = "backup_manager_id")
    @JSONField(serialize = false, deserialize = false)
    private User backupManager;
    public Long getBackupManagerId(){
        if (this.backupManager == null){
            return null;
        }
        return backupManager.getId();
    }
    public String getBackupManagerName(){
        if (this.backupManager == null){
            return "";
        }
        return backupManager.getName();
    }
    /**
     * 描述
     */
    @Column(name = "description")
    private String description;
}
