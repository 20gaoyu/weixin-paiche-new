package cn.gy.bean;

import cn.gy.validation.Update;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class MemberWebVo {

    @Id
    @NotNull(groups = {Update.class}, message = "id不能为空")
    @Column(name = "id")
    private  Long id;
    @JsonProperty("姓名")
    private  String accountName;
    @JsonProperty("编号")
    private  Long jobNumber;
    @JsonProperty( "企业微信名")
    private  String sex;
    @JsonProperty( "电话")
    private  String telephone;
    @JsonProperty( "邮箱")
    private  String email;
    @JsonProperty( "部门ID")
    private  Long departmentId;
    @JsonProperty( "部门")
    private  String departmentName;
    @JsonProperty( "职位")
    private  String position;
    @JsonProperty( "创建时间")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private  Date createTime;
    @JsonProperty( "上次登录时间")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastLoginTime;
    @JsonProperty( "操作")
    private String openId;
}
