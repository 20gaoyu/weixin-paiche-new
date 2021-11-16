package cn.gy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author chenws
 * @version 1.0
 * @date 2021/3/9
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST,reason = "查询条件异常，请检查！")
public class RequestParamException extends Exception{
    public RequestParamException(String message) {
        super(message);
    }
}
