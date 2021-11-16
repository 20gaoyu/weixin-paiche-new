package cn.gy.bean;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "t_m_visit_log")
public class TMVisitLog {
    /**
     * 标识
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "day_id")
    private Integer dayId;

    public Integer getDayId() {
        return dayId;
    }

    public void setDayId(Integer dayId) {
        this.dayId = dayId;
    }

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
     * 访问时间
     */
    @Column(name = "visit_time")
    private Date visitTime;

    /**
     * 访问者的IP
     */
    @Column(name = "visitor_ip")
    private String visitorIp;

    /**
     * 访问的url地址
     */
    @Column(name = "visit_url")
    private String visitUrl;

    /**
     * 请求内容
     */
    @Column(name = "request_content")
    private String requestContent;

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
     * 获取访问时间
     *
     * @return visit_time - 访问时间
     */
    public Date getVisitTime() {
        return visitTime;
    }

    /**
     * 设置访问时间
     *
     * @param visitTime 访问时间
     */
    public void setVisitTime(Date visitTime) {
        this.visitTime = visitTime;
    }

    /**
     * 获取访问者的IP
     *
     * @return visitor_ip - 访问者的IP
     */
    public String getVisitorIp() {
        return visitorIp;
    }

    /**
     * 设置访问者的IP
     *
     * @param visitorIp 访问者的IP
     */
    public void setVisitorIp(String visitorIp) {
        this.visitorIp = visitorIp;
    }

    /**
     * 获取访问的url地址
     *
     * @return visit_url - 访问的url地址
     */
    public String getVisitUrl() {
        return visitUrl;
    }

    /**
     * 设置访问的url地址
     *
     * @param visitUrl 访问的url地址
     */
    public void setVisitUrl(String visitUrl) {
        this.visitUrl = visitUrl;
    }

    /**
     * 获取请求内容
     *
     * @return request_content - 请求内容
     */
    public String getRequestContent() {
        return requestContent;
    }

    /**
     * 设置请求内容
     *
     * @param requestContent 请求内容
     */
    public void setRequestContent(String requestContent) {
        this.requestContent = requestContent;
    }
}