package cn.kcyf.pms.modular.system.entity;

import cn.kcyf.orm.jpa.entity.TableDomain;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;


/**
 * 访问记录表
 *
 * @author Tom
 */
@Data
@ToString
@NoArgsConstructor
@Entity
@Table(name = "sys_visit")
@ApiModel("访问记录")
public class Visit extends TableDomain {
    /**
     * ip地址
     */
    @Column(name = "ip")
    private String ip;
    /**
     * 访问的资源
     */
    @Column(name = "uri")
    private String uri;
    /**
     * 国家
     */
    @Column(name = "country")
    private String country;
    /**
     * 省
     */
    @Column(name = "region")
    private String region;
    /**
     * 市
     */
    @Column(name = "city")
    private String city;
    /**
     * 运营商
     */
    @Column(name = "isp")
    private String isp;
    /**
     * 访问时间
     */
    @Column(name = "send_time")
    private Date sendTime;
    /**
     * 当前用户
     */
    @Column(name = "login_id")
    private Long loginId;
    /**
     * 当前用户名
     */
    @Column(name = "login_name")
    private String loginName;
}
