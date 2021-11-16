package cn.gy.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by JDChen on 2019/09/10
 */
@ApiModel("账号信息")
public class AccountNameVo {

    @ApiModelProperty("账号标识")
    private String accountId;

    @ApiModelProperty("账号名称")
    private String accountName;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    @Override
    public String toString() {
        return "AccountNameVo{" +
                "accountId='" + accountId + '\'' +
                ", accountName='" + accountName + '\'' +
                '}';
    }

}
