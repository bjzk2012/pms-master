package cn.kcyf.pms.core.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
public class SuccessResponseData extends ResponseData {
    public SuccessResponseData() {
        super(true, DEFAULT_SUCCESS_CODE, "请求成功", (Object)null, 0l);
    }

    public SuccessResponseData(Object object) {
        super(true, DEFAULT_SUCCESS_CODE, "请求成功", object, 0l);
    }

    public SuccessResponseData(Integer code, String message, Object object) {
        super(true, code, message, object, 0l);
    }
}
