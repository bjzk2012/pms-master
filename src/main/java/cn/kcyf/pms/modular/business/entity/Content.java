package cn.kcyf.pms.modular.business.entity;

import cn.kcyf.orm.jpa.entity.TableDomain;
import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Data
@ToString
@NoArgsConstructor
@Entity
@Table(name = "c_content")
@ApiModel("内容")
public class Content extends TableDomain {
    /**
     * 标题
     */
    @Column(name = "subject", nullable = false)
    private String subject;
    /**
     * 编辑
     */
    @Column(name = "author", nullable = false)
    private String author;
    /**
     * 发布时间
     */
    @Column(name = "publish_time")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date publishTime;
    /**
     * 来源
     */
    @Column(name = "origin")
    private String origin;
    /**
     * 简介
     */
    @Column(name = "description", length = 1000)
    private String description;
    /**
     * 标题图
     */
    @Column(name = "title_img")
    private String titleImg;
    /**
     * 内容图
     */
    @Column(name = "content_img")
    private String contentImg;
    /**
     * 正文
     */
    @Column(name = "text", nullable = false)
    @Lob
    private String text;
    /**
     * 模型
     */
    @ManyToOne
    @JoinColumn(name = "mode_id")
    @JSONField(serialize = false, deserialize = false)
    private Mode mode;
    /**
     * 属性
     */
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id")
    @JSONField(serialize = false, deserialize = false)
    private Set<ContentAttr> attrs;

    public Map<String, String> getAttrsMap(){
        Map<String, String> attrsMap = new HashMap<String, String>();
        if (attrs != null){
            for (ContentAttr attr : attrs){
                attrsMap.put(attr.getField().getField(), attr.getValue());
            }
        }
        return attrsMap;
    }

}
