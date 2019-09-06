package cn.kcyf.pms.core.model.modular.system;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserEditRequest {
    @NotBlank(message = "部门未选择")
    private Long deptId;
    @NotBlank(message = "角色未选择")
    private String roleId;
}
