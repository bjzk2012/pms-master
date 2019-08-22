package cn.kcyf.pms.modular.system.entity;

import cn.kcyf.pms.core.enumerate.LogType;
import cn.kcyf.pms.core.enumerate.Succeed;
import cn.kcyf.orm.jpa.entity.IdDomain;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

/**
 * 操作日志
 *
 * @author Tom
 */
@Data
@ToString
@NoArgsConstructor
@Entity
@Table(name = "sys_operation_log")
@ApiModel("操作日志")
public class OperationLog extends IdDomain {
    /**
     * 日志类型(字典)
     */
    @Column(name = "log_type")
    @Enumerated
    private LogType logType;
    public String getLogTypeMessage(){
        if (logType != null){
            return logType.getMessage();
        }
        return "";
    }
    /**
     * 日志名称
     */
    @Column(name = "log_name")
    private String logName;
    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Long userId;
    /**
     * 用户id
     */
    @Column(name = "user_name")
    private String userName;
    /**
     * 类名称
     */
    @Column(name = "class_name")
    private String className;
    /**
     * 方法名称
     */
    @Column(name = "method")
    private String method;
    /**
     * 是否成功(字典)
     */
    @Column(name = "succeed")
    @Enumerated
    private Succeed succeed;
    public String getSucceedMessage(){
        if (succeed != null){
            return succeed.getMessage();
        }
        return "";
    }
    /**
     * 备注
     */
    @Column(name = "message")
    @Lob
    private String message;

    @Column(name = "create_time")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createTime;

}
