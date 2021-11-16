package cn.gy.bean;

import cn.gy.validation.Update;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Table(name = "t_m_member")
public class Member {

    @Id
    @NotNull(groups = {Update.class}, message = "id不能为空")
    @Column(name = "id")
    private  Long id;
    @Column(name = "account_name")
    private  String accountName;
    @Column(name = "job_number")
    private  Long jobNumber;
    @Column(name = "sex")
    private  String sex;
    @Column(name = "telephone")
    private  String telephone;
    @Column(name = "email")
    private  String email;
    @Column(name = "department_id")
    private  Long departmentId;
    @Column(name = "department_name")
    private  String departmentName;
    @Column(name = "position")
    private  String position;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time")
    private  Date createTime;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "last_login_time")
    private Date lastLoginTime;
    @Column(name = "open_id")
    private String openId;
}
