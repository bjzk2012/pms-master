package cn.kcyf.pms.modular.business.entity;

import cn.kcyf.pms.core.enumerate.WorkStatus;
import cn.kcyf.commons.utils.DateUtils;
import cn.kcyf.orm.jpa.entity.TableDomain;
import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

import static cn.kcyf.pms.core.constant.Constant.workCompareMap;

@Data
@ToString
@NoArgsConstructor
@Entity
@Table(name = "w_work")
@ApiModel("日工作记录")
public class Work extends TableDomain {
    /**
     * 日志编码
     */
    @Column(name = "code")
    private String code;
    /**
     * 工作日期
     */
    @Column(name = "today")
    @Temporal(value = TemporalType.DATE)
    private Date today;
    public String getTodayRemark(){
        return DateUtils.format(today, "yyyy-MM-dd");
    }
    /**
     * 工作日志
     */
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_id")
    @JSONField(serialize = false, deserialize = false)
    private Set<WorkRecord> records;
    public WorkStatus getStatus(){
        if (records == null || records.isEmpty()){
            return WorkStatus.DRAFT;
        }
        List<WorkRecord> recordsList = records
                .stream()
                .sorted((a, b) -> workCompareMap.get(a.getStatus()) - workCompareMap.get(b.getStatus()))
                .collect(Collectors.toList());
        return recordsList.iterator().next().getStatus();
    }
    public String getStatusMessage(){
        return getStatus().getMessage();
    }
}
