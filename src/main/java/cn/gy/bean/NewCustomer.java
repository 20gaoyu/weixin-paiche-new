package cn.gy.bean;

import cn.gy.validation.Insert;
import cn.gy.validation.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@ApiModel("新增客户参数")
public class NewCustomer {
	@ApiModelProperty("客户名称")
	@NotEmpty(groups = {Update.class, Insert.class}, message = "客户名称不能为空")
    private String customerName;

    @ApiModelProperty("客户来源")
    private Integer customerSource;
    
    @ApiModelProperty("联系邮箱")
    private String email;
    
    @ApiModelProperty("联系电话")
    private String telephone;

    @ApiModelProperty("主账号")
    @NotEmpty(groups = {Insert.class}, message = "主账号不能为空")
    @Size(groups = {Insert.class}, min = 6, max = 15, message = "主账号长度应为6-15位")
    private String account;
    
    @ApiModelProperty("状态 0：无效；1：有效")
    @Range(groups = {Update.class, Insert.class}, min = 0, max = 1, message = "生效标记非法")
    private Byte status;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getCustomerSource() {
        return customerSource;
    }

    public void setCustomerSource(Integer customerSource) {
        this.customerSource = customerSource;
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

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "NewCustomer{" +
                "customerName='" + customerName + '\'' +
                ", customerSource=" + customerSource +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                ", account='" + account + '\'' +
                ", status=" + status +
                '}';
    }
}
