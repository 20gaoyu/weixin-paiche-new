package cn.gy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : qinguoxing
 * @date : 2019/11/12
 */
@ControllerAdvice
public class RequestExceptionHandler {
    private static final int RESULT_SIZE = 3;
    private static final String RESULT_CODE_FIELD_NAME = "code";
    private static final String RESULT_TYPE_FIELD_NAME = "type";
    private static final String RESULT_MSG_FIELD_NAME = "msg";
    @ExceptionHandler(RequestParamException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String,Object> queryParamExceptionHandler(RequestParamException e){
        Map<String, Object> result = new HashMap<>(RESULT_SIZE);
        result.put(RESULT_CODE_FIELD_NAME,HttpStatus.BAD_REQUEST.value());
        result.put(RESULT_TYPE_FIELD_NAME,HttpStatus.BAD_REQUEST);
        result.put(RESULT_MSG_FIELD_NAME,e.getMessage());
        return result;
    }
}
