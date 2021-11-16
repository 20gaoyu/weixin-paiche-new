package cn.gy.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 
 * @author JDChen
 *
 */
@ApiModel("客户信息")
public class CustomerVo {
    @ApiModelProperty("客户标识")
    private String customerId;

    @ApiModelProperty("客户名称")
    private String customerName;

    @ApiModelProperty("客户来源")
    private String customerSource;

    @ApiModelProperty("APP ID")
    private Long appId;
    
    @ApiModelProperty("API KEY")
    private String apiKey;

    @ApiModelProperty("SECRET KEY")
    private String secretKey;

    @ApiModelProperty("状态：0：无效；1：有效")
    private Byte status;

    @ApiModelProperty("创建时间")
    private Date createTime;
    
    @ApiModelProperty("联系邮箱")
    private String email;
    
    @ApiModelProperty("联系电话")
    private String telephone;
    
    @ApiModelProperty("主账号")
    private String account;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerSource() {
        return customerSource;
    }

    public void setCustomerSource(String customerSource) {
        this.customerSource = customerSource;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "CustomerVo{" +
                "customerId='" + customerId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", customerSource='" + customerSource + '\'' +
                ", appId=" + appId +
                ", apiKey='" + apiKey + '\'' +
                ", secretKey='" + secretKey + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                ", account='" + account + '\'' +
                '}';
    }
}
