package cn.kcyf.pms.core.model.modular.system;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UserAddRequest extends UserEditRequest {
    @NotBlank(message = "确认密码不能为空")
    @Size(min = 6, max = 12, message = "确认密码必须6到12位")
    @Pattern(regexp = "[\\S]+", message = "确认密码不能出现空格")
    private String password;
    @NotBlank(message = "确认密码不能为空")
    private String rePassword;
}
