package cn.gy.core.web;

/**
 * 响应结果生成工具
 */
public class ResultGenerator {

    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";

    public static Result genSuccessResult() {
        return new Result().setCode(ResultCode.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE);
    }

    public static <T> Result<T> genSuccessResult(T data) {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.SUCCESS);
        result.setMessage(DEFAULT_SUCCESS_MESSAGE);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> genFailResult(String message,T data) {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.FAIL);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    public static Result genFailResult(String message) {
        return new Result().setCode(ResultCode.FAIL).setMessage(message);
    }

    public static Result genFailResult(ResultCode resultCode, String message) {
        return new Result().setCode(resultCode).setMessage(message);
    }
}
