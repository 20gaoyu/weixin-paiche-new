package cn.gy.bean;

import cn.gy.validation.Update;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class DispatchCarDetailWebVo {

    protected   Long id;
    protected  String applicant;
    protected  String user;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    protected  Date startTime;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    protected  Date endTime;
    protected  String ifComment;
    protected  String useReason;
    protected  String destination;
    protected  String oneAudit;
    protected  String twoAudit;
    protected  String status;
    protected  String operation;
    protected  String cancelReason;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    protected  Date createTime;
    protected  String departmentName;
    protected  String useNumber;
}
