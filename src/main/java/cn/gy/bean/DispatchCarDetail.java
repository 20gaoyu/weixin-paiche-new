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
    protected   Long id;
    @Column(name = "applicant")
    protected  String applicant;
    @Column(name = "user")
    protected  String user;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "start_time")
    protected  Date startTime;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "end_time")
    protected  Date endTime;
    @Column(name = "if_comment")
    protected  String ifComment;
    @Column(name = "use_reason")
    protected  String useReason;
    @Column(name = "destination")
    protected  String destination;
    @Column(name = "one_audit")
    protected  String oneAudit;
    @Column(name = "two_audit")
    protected  String twoAudit;
    @Column(name = "status")
    protected  String status;
    @Column(name = "operation")
    protected  String operation;
    @Column(name = "cancel_reason")
    protected  String cancelReason;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time")
    protected  Date createTime;
}
