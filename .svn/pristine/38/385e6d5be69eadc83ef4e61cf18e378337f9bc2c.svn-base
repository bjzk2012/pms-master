package cn.kcyf.pms.core.log;

import cn.kcyf.pms.core.enumerate.LogType;

import java.lang.annotation.*;

/**
 * 标记需要做业务日志的方法
 *
 * @author Tom
 *
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface BussinessLog {

    /**
     * 业务的名称,例如:"修改菜单"
     */
    String value() default "";

    /**
     * 被修改的实体的唯一标识,例如:菜单实体的唯一标识为"id"
     */
    String key() default "id";

    /**
     * 日志类型，默认为业务日志
     */
    LogType type() default LogType.BUSSINESS;
}
