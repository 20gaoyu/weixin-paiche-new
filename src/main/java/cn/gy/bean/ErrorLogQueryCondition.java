package cn.gy.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@ApiModel("错误日志参数")
@Data
public class ErrorLogQueryCondition {

    @ApiModelProperty("账号ID")
    private Long accountId;
    @ApiModelProperty(value="客户ID")
    private Long customerId;
    @ApiModelProperty("开始时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @ApiModelProperty("结束时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    @ApiModelProperty(value="当前页",required=true)
    private Integer page;
    @ApiModelProperty(value="单页记录条数",required=true)
    private Integer size;

}
