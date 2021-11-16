package cn.gy.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author gaoyu
 *
 */
@ApiModel("错误日志信息")
@Data
public class ErrorLogVo implements Serializable {
    @ApiModelProperty("id")
    private Long id;
    @ApiModelProperty("账号名称")
    private String accountName;
    @ApiModelProperty("客户名称")
    private String customerName;
    @ApiModelProperty("错误描述")
    private String errorDesc;
    @ApiModelProperty("异常时间")
    private Date errorTime;
}
