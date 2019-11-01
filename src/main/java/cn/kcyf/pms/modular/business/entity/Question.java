package cn.kcyf.pms.modular.business.entity;

import cn.kcyf.pms.core.enumerate.QuestionCategory;
import cn.kcyf.pms.core.enumerate.QuestionCause;
import cn.kcyf.pms.core.enumerate.QuestionQuomodo;
import cn.kcyf.pms.core.enumerate.QuestionStatus;
import cn.kcyf.pms.modular.system.entity.User;
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
@Table(name = "q_question")
@ApiModel("问题")
public class Question extends TableDomain {
    /**
     * 编码
     */
    @Column(name = "code", nullable = false)
    private String code;
    /**
     * 问题标题
     */
    @Column(name = "title", nullable = false)
    private String title;
    /**
     * 项目
     */
    @ManyToOne
    @JoinColumn(name = "project_id")
    @JSONField(serialize = false, deserialize = false)
    private Project project;

    public Long getProjectId() {
        if (project == null) {
            return null;
        }
        return project.getId();
    }

    public String getProjectName() {
        if (project == null) {
            return null;
        }
        return project.getName();
    }

    /**
     * 问题描述
     */
    @Column(name = "description")
    @Lob
    private String description;
    /**
     * 问题备注
     */
    @Column(name = "remark")
    @Lob
    private String remark;
    /**
     * 发生时间
     */
    @Column(name = "time", nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date time;
    /**
     * 联系手机号
     */
    @Column(name = "phone", nullable = false)
    private String phone;
    /**
     * 负责人
     */
    @ManyToOne
    @JoinColumn(name = "liable_id")
    @JSONField(serialize = false, deserialize = false)
    private User liable;

    public Long getLiableId() {
        if (this.liable == null) {
            return null;
        }
        return liable.getId();
    }

    public String getLiableName() {
        if (this.liable == null) {
            return "";
        }
        return liable.getName();
    }

    /**
     * 问题状态
     */
    @Column(name = "status", nullable = false)
    @Enumerated
    private QuestionStatus status;

    public String getStatusMessage() {
        return status.getMessage();
    }

    /**
     * 问题类型
     */
    @Column(name = "category")
    @Enumerated
    private QuestionCategory category;

    public String getCategoryMessage() {
        if (this.category == null) {
            return "";
        }
        return category.getMessage();
    }

    /**
     * 问题原因
     */
    @Column(name = "cause")
    @Enumerated
    private QuestionCause cause;

    public String getCauseMessage() {
        if (cause == null) {
            return null;
        }
        return cause.getMessage();
    }

    /**
     * 处理方式
     */
    @Column(name = "quomodo")
    @Enumerated
    private QuestionQuomodo quomodo;

    public String getQuomodoMessage() {
        if (quomodo == null) {
            return null;
        }
        return quomodo.getMessage();
    }

    /**
     * 发起人
     */
    @Column(name = "sponsor")
    private String sponsor;
    /**
     * IP地址
     */
    @Column(name = "ip")
    private String ip;
    /**
     * 浏览器版本
     */
    @Column(name = "browse")
    private String browse;
    /**
     * 问题处理记录
     */
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    @JSONField(serialize = false, deserialize = false)
    private Set<QuestionRecord> records;
}
