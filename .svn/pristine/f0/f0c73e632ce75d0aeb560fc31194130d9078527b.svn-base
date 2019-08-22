package cn.kcyf.pms.modular.business.entity;

import cn.kcyf.pms.core.enumerate.QuestionRecordType;
import cn.kcyf.pms.core.enumerate.QuestionStatus;
import cn.kcyf.orm.jpa.entity.TableDomain;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@ToString
@NoArgsConstructor
@Entity
@Table(name = "q_question_record")
@ApiModel("问题处理记录")
public class QuestionRecord extends TableDomain {
    /**
     * 记录类型
     */
    @Column(name = "type", nullable = false)
    private QuestionRecordType type;
    /**
     * 问题编号
     */
    @Column(name = "question_id", nullable = false)
    private Long questionId;
    /**
     * 操作人
     */
    @Column(name = "operator", nullable = false)
    private String operator;
    /**
     * 操作内容
     */
    @Column(name = "description")
    @Lob
    private String description;
    /**
     * 处理后问题的状态
     */
    @Column(name = "status")
    @Enumerated
    private QuestionStatus status;
    public String getStatusMessage(){
        return status.getMessage();
    }

}
