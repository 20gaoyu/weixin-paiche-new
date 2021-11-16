package cn.gy.constant;

public enum ErrorLogType {
    DATA_ERROR(1, "数据库异常"),
    FAIL(400,"失败"), // 失败
    UNAUTHORIZED(401,"未认证（签名错误）"), // 未认证（签名错误）
    ACCESS_DENIED(403,"拒绝访问"),//拒绝访问
    NOT_FOUND(404,"接口不存在"), // 接口不存在
    INTERNAL_SERVER_ERROR(500,"服务器内部错误"),// 服务器内部错误
    OTHER_ERROR(999, "其他异常");//该异常归为写入异常一种

    private int code;
    private String name;

    ErrorLogType(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
