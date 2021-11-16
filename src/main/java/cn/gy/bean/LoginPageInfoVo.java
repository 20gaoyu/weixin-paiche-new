package cn.gy.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/***
 * Created by JDChen on 2019/09/03
 */
@ApiModel("判别当前登录客户为管理员还是普通客户，分页展示客户信息")
public class LoginPageInfoVo<T> extends PageInfoVo<T>{

    @ApiModelProperty("登录客户类别：0：普通客户，1:管理员")
    private String customerStatus;

    public LoginPageInfoVo(List<T> list, long total, String customerStatus) {
        super(list, total);
        this.customerStatus = customerStatus;
    }

    public LoginPageInfoVo(String customerStatus) {
        this.customerStatus = customerStatus;
    }

    public String getCustomerStatus() {
        return customerStatus;
    }

    public void setCustomerStatus(String customerStatus) {
        this.customerStatus = customerStatus;
    }
}
