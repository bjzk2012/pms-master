package cn.kcyf.pms.core.model;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.domain.Page;

import java.util.Collection;

@Data
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@ApiModel("响应信息")
public class ResponseData {
    public static final String DEFAULT_SUCCESS_MESSAGE = "请求成功";
    public static final String DEFAULT_ERROR_MESSAGE = "网络异常";
    public static final Integer DEFAULT_SUCCESS_CODE = 200;
    public static final Integer DEFAULT_ERROR_CODE = 500;
    private Boolean success;
    private Integer code;
    private String message;
    private Object data;
    private long count;

    public ResponseData() {
    }

    public static SuccessResponseData success() {
        return new SuccessResponseData();
    }

    public static SuccessResponseData success(Object object) {
        return new SuccessResponseData(object);
    }

    public static SuccessResponseData success(Integer code, String message, Object object) {
        return new SuccessResponseData(code, message, object);
    }

    public static ResponseData list(Page<?> page) {
        return new ResponseData(Boolean.TRUE, 0, DEFAULT_SUCCESS_MESSAGE, page.getContent(), page.getTotalElements());
    }

    public static ResponseData list(Collection<?> list) {
        return new ResponseData(Boolean.TRUE, 0, DEFAULT_SUCCESS_MESSAGE, list, list.size());
    }

    public static ErrorResponseData error(String message) {
        return new ErrorResponseData(message);
    }

    public static ErrorResponseData error(Integer code, String message) {
        return new ErrorResponseData(code, message);
    }

    public static ErrorResponseData error(Integer code, String message, Object object) {
        return new ErrorResponseData(code, message, object);
    }
}
