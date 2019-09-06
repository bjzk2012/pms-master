package cn.kcyf.pms.core.model.modular.business;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class KeyEditRequest {
    @NotBlank(message = "请选择项目")
    private Long projectId;
    @NotBlank(message = "请选择管理人")
    private Long managerId;
}
