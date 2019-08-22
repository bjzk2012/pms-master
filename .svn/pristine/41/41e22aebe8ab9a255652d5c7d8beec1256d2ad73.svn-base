package cn.kcyf.pms.modular.business.entity;

import cn.kcyf.pms.core.enumerate.Status;
import cn.kcyf.orm.jpa.entity.TableDomain;
import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
 * 项目表
 *
 * @author Tom
 */
@Data
@ToString
@NoArgsConstructor
@Entity
@Table(name = "p_project")
@ApiModel("项目")
public class Project extends TableDomain {
    /**
     * 项目编码
     */
    @Column(name = "code")
    private String code;
    /**
     * 项目名称
     */
    @Column(name = "name")
    @NotBlank(message = "项目名称不能为空")
    private String name;
    /**
     * 已使用工时
     */
    @Column(name = "used")
    private Integer used;
    /**
     * 总工时
     */
    @Column(name = "time")
    private Integer time;

    @JSONField(format = "#0.00")
    public BigDecimal getRate() {
        BigDecimal rate = new BigDecimal(used.toString()).divide(new BigDecimal(time.toString()), 4, BigDecimal.ROUND_HALF_DOWN);
        if (rate.compareTo(BigDecimal.ONE) > 0) {
            return BigDecimal.ONE;
        }
        return rate.multiply(new BigDecimal("100"));
    }

    /**
     * 项目描述
     */
    @Column(name = "description")
    private String description;
    /**
     * 项目状态
     */
    @Column(name = "status")
    private Status status;

    public String getStatusMessage() {
        if (this.status != null) {
            return status.getMessage();
        }
        return "";
    }
}
