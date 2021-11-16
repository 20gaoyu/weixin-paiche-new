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
@Table(name = "t_m_dispatch_car_detail")
public class DispatchCarDetail {

    @Id
    @NotNull(groups = {Update.class}, message = "id不能为空")
    @Column(name = "id")
    private  Long id;
    @Column(name = "applicant")
    private  String applicant;
    @Column(name = "user")
    private  String user;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "start_time")
    private  Date startTime;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "end_time")
    private  Date endTime;
    @Column(name = "if_comment")
    private  String ifComment;
    @Column(name = "use_reason")
    private  String useReason;
    @Column(name = "destination")
    private  String destination;
    @Column(name = "one_audit")
    private  String oneAudit;
    @Column(name = "two_audit")
    private  String twoAudit;
    @Column(name = "status")
    private  String status;
    @Column(name = "operation")
    private  String operation;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time")
    private  Date createTime;
}
