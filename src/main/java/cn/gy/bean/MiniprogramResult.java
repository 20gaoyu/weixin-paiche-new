package cn.gy.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class MiniprogramResult implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 获取到openid */
    public String openid;
    /** 获取到unionid */
    public String unionid;
    /** 获取到session_key */
    public String session_key;
    /** 获取到的凭证 */
    public String access_token;
    /** 凭证有效时间，单位：秒 */
    public Integer expires_in;
    /** 错误码 */
    public Integer errcode;
    /** 错误信息 */
    public String errmsg;

    // get set 省略
}
