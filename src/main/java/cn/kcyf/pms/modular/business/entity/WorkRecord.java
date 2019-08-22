package cn.kcyf.pms.modular.business.entity;

import cn.kcyf.pms.core.enumerate.WorkStatus;
import cn.kcyf.commons.utils.DateUtils;
import cn.kcyf.orm.jpa.entity.TableDomain;
import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@ToString
@NoArgsConstructor
@Entity
@Table(name = "w_work_record")
@ApiModel("日工作记录")
public class WorkRecord extends TableDomain {
    @Column(name = "work_id")
    private Long workId;
    /**
     * 项目
     */
    @ManyToOne(fetch = FetchType.LAZY)
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
     * 工作日期
     */
    @Column(name = "today")
    @Temporal(value = TemporalType.DATE)
    private Date today;
    public String getTodayRemark(){
        return DateUtils.format(today, "yyyy-MM-dd");
    }
    /**
     * 工作时间
     */
    @Column(name = "time")
    private Integer time;
    /**
     * 工作内容
     */
    @Column(name = "content")
    private String content;
    /**
     * 工作日志审核流程信息
     */
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_record_id")
    @JSONField(serialize = false, deserialize = false)
    private Set<WorkAudit> audits;
    /**
     * 状态
     */
    @Column(name = "status")
    @Enumerated
    private WorkStatus status;
    public String getStatusMessage(){
        return status.getMessage();
    }

    @Column(name = "submit_user_name")
    private String submitUserName;
    @Column(name = "submit_time")
    private Date submitTime;
    @Column(name = "last_audit_user_id")
    private Long lastAuditUserId;
    @Column(name = "last_audit_user_name")
    private String lastAuditUserName;
    @Column(name = "last_audit_time")
    private Date lastAuditTime;
}
