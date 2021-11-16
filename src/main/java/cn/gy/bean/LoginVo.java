package cn.gy.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 
 * @author JDChen
 *
 */
@ApiModel("登录信息")
public class LoginVo implements Serializable {
    @ApiModelProperty("客户标识")
    private String customerId;

    @ApiModelProperty("客户名称")
    private String customerName;

    @ApiModelProperty("账号标识")
    private String accountId;

    @ApiModelProperty("账号名称")
    private String accountName;

    @ApiModelProperty("apiKey")
    private String apiKey;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "LoginVo{" +
                "customerId='" + customerId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", accountId='" + accountId + '\'' +
                ", accountName='" + accountName + '\'' +
                ", apiKey='" + apiKey + '\'' +
                '}';
    }
}
