package cn.gy.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Created by JDChen on 2019/09/09
 */
@ApiModel("用户访问日志")
public class VisitLogVo {
    @ApiModelProperty("客户名称")
    private String customerName;

    @ApiModelProperty("账号名称")
    private String accountName;

    @ApiModelProperty("访问ip")
    private String visitIp;

    @ApiModelProperty("访问时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date visitDate;

    @ApiModelProperty("访问地址")
    private String visitUrl;

    @ApiModelProperty("请求内容")
    private String content;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getVisitIp() {
        return visitIp;
    }

    public void setVisitIp(String visitIp) {
        this.visitIp = visitIp;
    }

    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    public String getVisitUrl() {
        return visitUrl;
    }

    public void setVisitUrl(String visitUrl) {
        this.visitUrl = visitUrl;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "VisitLogVo{" +
                "customerName='" + customerName + '\'' +
                ", accountName='" + accountName + '\'' +
                ", visitIp='" + visitIp + '\'' +
                ", visitDate=" + visitDate +
                ", visitUrl='" + visitUrl + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
