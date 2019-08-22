package cn.kcyf.pms.modular.system.entity;

import cn.kcyf.pms.core.enumerate.LockStatus;
import cn.kcyf.pms.core.enumerate.Sex;
import cn.kcyf.orm.jpa.entity.TableDomain;
import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;


/**
 * 管理员表
 *
 * @author Tom
 */
@Data
@ToString
@NoArgsConstructor
@Entity
@Table(name = "sys_user")
@ApiModel("管理员")
public class User extends TableDomain {
    /**
     * 头像
     */
    @Column(name = "avatar")
    @ApiModelProperty(name = "avatar", value = "头像")
    private String avatar;
    /**
     * 账号
     */
    @Column(name = "account", unique = true, nullable = false)
    @NotBlank(message = "用户账号不能为空")
    @Size(min = 5, max = 36, message = "用户账号必须5到36位")
    @Pattern(regexp = "\\w+", message = "用户账号只能是单词字符（字母，数字，下划线，中横线）")
    @ApiModelProperty(name = "account", value = "账号")
    private String account;
    /**
     * 密码
     */
    @Column(name = "password")
    @JSONField(serialize = false)
    @ApiModelProperty(name = "password", value = "密码")
    private String password;
    /**
     * md5密码盐
     */
    @Column(name = "salt")
    @JSONField(serialize = false, deserialize = false)
    private String salt;
    /**
     * 名字
     */
    @Column(name = "name")
    @ApiModelProperty(name = "password", value = "名字")
    private String name;
    /**
     * 生日
     */
    @Column(name = "birthday")
    @Temporal(value = TemporalType.TIMESTAMP)
    @ApiModelProperty(name = "birthday", value = "生日")
    private Date birthday;
    /**
     * 性别
     */
    @Column(name = "sex")
    @Enumerated
    @ApiModelProperty(name = "sex", value = "性别")
    private Sex sex;

    public String getSexMessage() {
        if (this.sex != null) {
            return this.sex.getMessage();
        }
        return "--";
    }

    /**
     * 电子邮件
     */
    @Column(name = "email")
    @Email(message = "电子邮件格式不正确")
    @ApiModelProperty(name = "email", value = "电子邮件")
    private String email;
    /**
     * 电话
     */
    @Column(name = "phone")
    @Pattern(regexp = "(^\\.{0}$)|(^(0[0-9]{2,3}\\-)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?$)|(^0?[1][358][0-9]{9}$)", message = "电话必须是固定电话或手机号码")
    @ApiModelProperty(name = "phone", value = "电话")
    private String phone;
    /**
     * QQ号码
     */
    @Column(name = "qq")
    @ApiModelProperty(name = "qq", value = "QQ号码")
    private String qq;
    /**
     * 微信号
     */
    @Column(name = "wechat")
    @ApiModelProperty(name = "wechat", value = "微信号")
    private String wechat;
    /**
     * 微博账号
     */
    @Column(name = "weibo")
    @ApiModelProperty(name = "weibo", value = "微博账号")
    private String weibo;
    /**
     * 地址
     */
    @ApiModelProperty(name = "address", value = "地址")
    @Column(name = "address")
    private String address;
    /**
     * 角色
     */
    @ManyToMany(targetEntity = Role.class)
    @JoinTable(name = "sys_user_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "roles_id", referencedColumnName = "id")}
    )
    @JoinColumn(name = "roles_id")
    @JSONField(serialize = false, deserialize = false)
    private Set<Role> roles;

    public String getRoleId() {
        if (this.roles != null) {
            List<Long> roleIds = new ArrayList<Long>();
            for (Role role : this.roles) {
                roleIds.add(role.getId());
            }
            return StringUtils.join(roleIds, ",");
        }
        return "";
    }

    public String getRoleName() {
        if (this.roles != null) {
            List<String> roleNames = new ArrayList<String>();
            for (Role role : this.roles) {
                roleNames.add(role.getName());
            }
            return StringUtils.join(roleNames, ",");
        }
        return "";
    }

    /**
     * 部门
     */
    @ManyToOne
    @JoinColumn(name = "dept_id")
    @JSONField(serialize = false, deserialize = false)
    private Dept dept;

    public Long getDeptId() {
        if (this.dept != null) {
            return this.dept.getId();
        }
        return null;
    }

    public String getDeptName() {
        if (this.dept != null) {
            return this.dept.getFullName();
        }
        return "";
    }

    /**
     * 状态
     */
    @Column(name = "status")
    @ApiModelProperty(name = "status", value = "状态")
    private LockStatus status;

    public String getStatusMessage() {
        if (this.status != null) {
            return this.status.getMessage();
        }
        return "";
    }

}
