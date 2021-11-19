package cn.gy.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 
 * @author JDChen
 *
 */
@ApiModel("客户信息")
@Data
public class WeixinVo {
    @ApiModelProperty("客户标识")
    private String code;
    private String department;
    private String telephone;
}
