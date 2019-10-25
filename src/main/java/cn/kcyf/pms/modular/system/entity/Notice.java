package cn.kcyf.pms.modular.system.entity;

import cn.kcyf.orm.jpa.entity.TableDomain;
import cn.kcyf.pms.core.enumerate.NoticeType;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Data
@ToString
@NoArgsConstructor
@Entity
@Table(name = "sys_notice")
@ApiModel("通知")
public class Notice extends TableDomain {
    /**
     * 通知标题
     */
    @Column(name = "title", nullable = false)
    private String title;
    /**
     * 通知内容
     */
    @Column(name = "content", nullable = false, length = 1000)
    private String content;
    /**
     * 通知类型
     */
    @Column(name = "type", nullable = false)
    @Enumerated
    private NoticeType type;
    public String getTypeMessage(){
        if (this.type != null){
            return this.type.getMessage();
        }
        return "";
    }
}
