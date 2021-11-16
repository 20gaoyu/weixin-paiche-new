package cn.gy.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @author gaoyu
 *
 */
@ApiModel("客户信息")
@Data
public class LoginUrlVo {
    @ApiModelProperty("是否修改过密码1修改，0未修改")
    private boolean if_reset_passwd;

    @ApiModelProperty("第一个url")
    private String url;

}
