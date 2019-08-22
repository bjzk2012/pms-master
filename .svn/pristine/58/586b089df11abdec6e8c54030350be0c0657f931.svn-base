package cn.kcyf.pms.modular.system.entity;

import cn.kcyf.pms.core.enumerate.AuthType;
import cn.kcyf.pms.core.enumerate.YesOrNo;
import cn.kcyf.orm.jpa.entity.TableDomain;
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
@Table(name = "sys_authcode")
@ApiModel("验证码")
public class AuthCode extends TableDomain {
    /**
     * 验证码或验证链接
     */
    @Column(name = "auth", nullable = false)
    private String auth;
    /**
     * 短信内容
     */
    @Column(name = "content", nullable = false)
    private String content;
    /**
     * 手机号
     */
    @Column(name = "mobile", nullable = false)
    private String mobile;
    /**
     * 短信类型
     */
    @Column(name = "type", nullable = false)
    @Enumerated
    private AuthType type;
    /**
     * 是否已使用
     */
    @Column(name = "uesd", nullable = false)
    @Enumerated
    private YesOrNo uesd;
}
