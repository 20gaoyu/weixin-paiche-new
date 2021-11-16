package cn.gy.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @author gaoyu
 *
 */
@ApiModel("账号信息")
@Data
public class CustomNameVo {


    @ApiModelProperty("客户标识")
    private String customerId;
    @ApiModelProperty("客户名称")
    private String customerName;

}
