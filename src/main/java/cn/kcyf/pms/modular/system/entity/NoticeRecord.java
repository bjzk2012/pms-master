package cn.kcyf.pms.modular.system.entity;

import cn.kcyf.orm.jpa.entity.TableDomain;
import cn.kcyf.pms.core.enumerate.NoticeType;
import cn.kcyf.pms.core.enumerate.ReadType;
import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Data
@ToString
@NoArgsConstructor
@Entity
@Table(name = "sys_notice_record")
@ApiModel("通知明细")
public class NoticeRecord extends TableDomain {
    /**
     * 发送方
     */
    @Column(name = "from_name", nullable = false)
    private String fromName;
    /**
     * 接收人
     */
    @Column(name = "to_id", nullable = false)
    private Long toId;
    /**
     * 通知
     */
    @ManyToOne
    @JoinColumn(name = "notice_id")
    @JSONField(serialize = false, deserialize = false)
    private Notice notice;
    public String getTitle(){
        return notice.getTitle();
    }
    public NoticeType getType(){
        return notice.getType();
    }
    public String getTypeMessage(){
        return notice.getType().getMessage();
    }
    public String getContent(){
        return notice.getContent();
    }
    /**
     * 已读状态
     */
    @Column(name = "is_read", nullable = false)
    @Enumerated
    private ReadType isRead;

    /**
     * 阅读时间
     */
    @Column(name = "read_time")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date readTime;

}
