package cn.gy.bean;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "t_m_error_log")
public class TMErrorLog {
    /**
     * 标识
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 日标识
     */
    @Column(name = "day_id")
    private Integer dayId;

    /**
     * 账号标识
     */
    @Column(name = "account_id")
    private Long accountId;

    /**
     * 账号名称
     */
    @Column(name = "account_name")
    private String accountName;

    /**
     * 客户标识
     */
    @Column(name = "customer_id")
    private Long customerId;

    /**
     * 客户名称
     */
    @Column(name = "customer_name")
    private String customerName;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 异常时间
     */
    @Column(name = "error_time")
    private Date errorTime;

    /**
     * 错误描述
     */
    @Column(name = "error_desc")
    private String errorDesc;

    /**
     * 错误码
     */
    @Column(name = "error_code")
    private Integer errorCode;

    /**
     * 错误内容
     */
    @Column(name = "error_content")
    private String errorContent;

    /**
     * 获取标识
     *
     * @return id - 标识
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置标识
     *
     * @param id 标识
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取日标识
     *
     * @return day_id - 日标识
     */
    public Integer getDayId() {
        return dayId;
    }

    /**
     * 设置日标识
     *
     * @param dayId 日标识
     */
    public void setDayId(Integer dayId) {
        this.dayId = dayId;
    }

    /**
     * 获取账号标识
     *
     * @return account_id - 账号标识
     */
    public Long getAccountId() {
        return accountId;
    }

    /**
     * 设置账号标识
     *
     * @param accountId 账号标识
     */
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    /**
     * 获取账号名称
     *
     * @return account_name - 账号名称
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * 设置账号名称
     *
     * @param accountName 账号名称
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    /**
     * 获取客户标识
     *
     * @return customer_id - 客户标识
     */
    public Long getCustomerId() {
        return customerId;
    }

    /**
     * 设置客户标识
     *
     * @param customerId 客户标识
     */
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    /**
     * 获取客户名称
     *
     * @return customer_name - 客户名称
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * 设置客户名称
     *
     * @param customerName 客户名称
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取异常时间
     *
     * @return error_time - 异常时间
     */
    public Date getErrorTime() {
        return errorTime;
    }

    /**
     * 设置异常时间
     *
     * @param errorTime 异常时间
     */
    public void setErrorTime(Date errorTime) {
        this.errorTime = errorTime;
    }

    /**
     * 获取错误描述
     *
     * @return error_desc - 错误描述
     */
    public String getErrorDesc() {
        return errorDesc;
    }

    /**
     * 设置错误描述
     *
     * @param errorDesc 错误描述
     */
    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }

    /**
     * 获取错误码
     *
     * @return error_code - 错误码
     */
    public Integer getErrorCode() {
        return errorCode;
    }

    /**
     * 设置错误码
     *
     * @param errorCode 错误码
     */
    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * 获取错误内容
     *
     * @return error_content - 错误内容
     */
    public String getErrorContent() {
        return errorContent;
    }

    /**
     * 设置错误内容
     *
     * @param errorContent 错误内容
     */
    public void setErrorContent(String errorContent) {
        this.errorContent = errorContent;
    }
}