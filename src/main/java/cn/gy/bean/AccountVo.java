package cn.gy.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 
 * @author gaoyu
 *
 */
@ApiModel("账号信息")
@Data
public class AccountVo {

    @ApiModelProperty("账号标识")
    private String accountId;
    @ApiModelProperty("账号名称")
    private String accountName;
    @ApiModelProperty("账号类型")
    private Byte accountType;
    @ApiModelProperty("客户标识")
    private String customerId;
    @ApiModelProperty("客户名称")
    private String customerName;
    @ApiModelProperty("角色标识")
    private String roleId;
    @ApiModelProperty("角色名称")
    private String roleName;
    @ApiModelProperty("最后登录时间")
    private Date lastLoginTime;
    @ApiModelProperty("创建时间")
    private Date createTime;

}
