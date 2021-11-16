package cn.gy.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigInteger;

@ApiModel("新增账号参数")
@Data
public class NewAccount {
    @ApiModelProperty("账号名称")
    private String accountName;
    @ApiModelProperty("角色标识")
    private BigInteger roleId;
    @ApiModelProperty("角色名称")
    private String roleName;
    @ApiModelProperty("客户标识")
    private Long customerId;
    @ApiModelProperty("客户名称")
    private String customerName;

}
