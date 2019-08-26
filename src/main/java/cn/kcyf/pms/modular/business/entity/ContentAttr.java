package cn.kcyf.pms.modular.business.entity;

import cn.kcyf.orm.jpa.entity.IdDomain;
import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@ToString
@NoArgsConstructor
@Entity
@Table(name = "c_content_attr")
@ApiModel("内容属性")
public class ContentAttr extends IdDomain {

    @ManyToOne
    @JoinColumn(name = "field_id")
    @JSONField(serialize = false, deserialize = false)
    private ModeField field;

    @Column(name = "value")
    @Lob
    private String value;

}
