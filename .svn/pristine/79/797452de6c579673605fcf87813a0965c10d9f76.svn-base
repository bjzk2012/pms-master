package cn.kcyf.pms.core.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
public class ErrorResponseData extends ResponseData {
    private String exceptionClazz;

    public ErrorResponseData(String message) {
        super(false, ResponseData.DEFAULT_ERROR_CODE, message, (Object)null, 0l);
    }

    public ErrorResponseData(Integer code, String message) {
        super(false, code, message, (Object)null, 0l);
    }

    public ErrorResponseData(Integer code, String message, Object object) {
        super(false, code, message, object, 0l);
    }

    public String getExceptionClazz() {
        return this.exceptionClazz;
    }

    public void setExceptionClazz(final String exceptionClazz) {
        this.exceptionClazz = exceptionClazz;
    }
}
