package cn.kcyf.pms.modular.system.entity;

import cn.kcyf.orm.jpa.entity.IdDomain;
import cn.kcyf.pms.core.enumerate.DayType;
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
@Table(name = "sys_holiday")
@ApiModel("节假日")
public class Holiday extends IdDomain {
    /**
     * 日期
     */
    @Column(name = "day")
    private String day;
    /**
     * 中文名
     */
    @Column(name = "name")
    private String name;
    /**
     * 节假日类型
     */
    @Column(name = "type")
    @Enumerated
    private DayType type;
    /**
     * 周几
     */
    @Column(name = "week")
    private Integer week;
}
