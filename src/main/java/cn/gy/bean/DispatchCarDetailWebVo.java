package cn.gy.bean;

import cn.gy.validation.Update;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("用车开始时间")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    protected  Date startTime;
    @JsonProperty("用车结束时间")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    protected  Date endTime;
    @JsonProperty("申请人")
    protected  String applicant;
    @JsonProperty("使用人")
    protected  String user;

    @JsonProperty("评价")
    protected  String ifComment;
    @JsonProperty("用车原因")
    protected  String useReason;
    @JsonProperty("目的地")
    protected  String destination;
    @JsonProperty("主任审核")
    protected  String oneAudit;
    @JsonProperty("调度审核")
    protected  String twoAudit;
    @JsonProperty("状态")
    protected  String status;
    @JsonProperty("取消原因")
    protected  String cancelReason;
    @JsonProperty("部门")
    protected  String departmentName;
    @JsonProperty("使用人数")
    protected  String useNumber;
    @JsonProperty("操作")
    protected  String operation;
}
