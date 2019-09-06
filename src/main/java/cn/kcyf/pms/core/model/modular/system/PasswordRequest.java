package cn.kcyf.pms.core.model.modular.system;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@ApiModel("密码修改请求对象")
public class PasswordRequest {
    @NotBlank(message = "旧密码不能为空")
    @Size(min = 6, max = 12, message = "旧密码必须6到12位")
    @Pattern(regexp = "[\\S]+", message = "旧密码不能出现空格")
    @ApiModelProperty("旧密码")
    private String oldPassword;
    @NotBlank(message = "新密码不能为空")
    @Size(min = 6, max = 12, message = "新密码必须6到12位")
    @Pattern(regexp = "[\\S]+", message = "新密码不能出现空格")
    @ApiModelProperty("新密码")
    private String newPassword;
    @NotBlank(message = "确认密码不能为空")
    @Size(min = 6, max = 12, message = "确认密码必须6到12位")
    @Pattern(regexp = "[\\S]+", message = "确认密码不能出现空格")
    @ApiModelProperty("确认密码")
    private String rePassword;
}
