package cn.gy.bean;

import cn.gy.validation.Insert;
import cn.gy.validation.Update;
import io.swagger.annotations.ApiModel;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@ApiModel("客户信息")
@Table(name = "t_m_customer")
public class TMCustomer {
    /**
     * 客户标识
     */
    @Id
    @Column(name = "customer_id")
    @NotNull(groups = {Update.class}, message = "客户标识不能为空")
    private Long customerId;

    /**
     * 客户名称
     */
    @Column(name = "customer_name")
    @NotEmpty(groups = {Update.class, Insert.class}, message = "客户名称不能为空")
    private String customerName;

    /**
     * 客户来源
     */
    @Column(name = "customer_source")
    private Integer customerSource;

    /**
     * 主账号
     */
    private String account;

    /**
     * APP ID
     */
    @Column(name = "app_id")
    private Long appId;

    /**
     * API KEY
     */
    @Column(name = "api_key")
    private String apiKey;

    /**
     * SECRET KEY
     */
    @Column(name = "secret_key")
    private String secretKey;

    /**
     * EMAIL
     */
    private String email;

    /**
     * 联系电话
     */
    private String telephone;

    /**
     * 客户描述
     */
    @Column(name = "customer_describe")
    private String customerDescribe;

    /**
     * 状态：0：无效；1：有效
     */
    private Byte status;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新人
     */
    private String updator;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

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
     * 获取客户来源
     *
     * @return customer_source - 客户来源
     */
    public Integer getCustomerSource() {
        return customerSource;
    }

    /**
     * 设置客户来源
     *
     * @param customerSource 客户来源
     */
    public void setCustomerSource(Integer customerSource) {
        this.customerSource = customerSource;
    }

    /**
     * 获取主账号
     *
     * @return account - 主账号
     */
    public String getAccount() {
        return account;
    }

    /**
     * 设置主账号
     *
     * @param account 主账号
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * 获取APP ID
     *
     * @return app_id - APP ID
     */
    public Long getAppId() {
        return appId;
    }

    /**
     * 设置APP ID
     *
     * @param appId APP ID
     */
    public void setAppId(Long appId) {
        this.appId = appId;
    }

    /**
     * 获取API KEY
     *
     * @return api_key - API KEY
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * 设置API KEY
     *
     * @param apiKey API KEY
     */
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * 获取SECRET KEY
     *
     * @return secret_key - SECRET KEY
     */
    public String getSecretKey() {
        return secretKey;
    }

    /**
     * 设置SECRET KEY
     *
     * @param secretKey SECRET KEY
     */
    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    /**
     * 获取EMAIL
     *
     * @return email - EMAIL
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置EMAIL
     *
     * @param email EMAIL
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取联系电话
     *
     * @return telephone - 联系电话
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * 设置联系电话
     *
     * @param telephone 联系电话
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * 获取客户描述
     *
     * @return customer_describe - 客户描述
     */
    public String getCustomerDescribe() {
        return customerDescribe;
    }

    /**
     * 设置客户描述
     *
     * @param customerDescribe 客户描述
     */
    public void setCustomerDescribe(String customerDescribe) {
        this.customerDescribe = customerDescribe;
    }

    /**
     * 获取状态：0：无效；1：有效
     *
     * @return status - 状态：0：无效；1：有效
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置状态：0：无效；1：有效
     *
     * @param status 状态：0：无效；1：有效
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 获取创建人
     *
     * @return creator - 创建人
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 设置创建人
     *
     * @param creator 创建人
     */
    public void setCreator(String creator) {
        this.creator = creator;
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
     * 获取更新人
     *
     * @return updator - 更新人
     */
    public String getUpdator() {
        return updator;
    }

    /**
     * 设置更新人
     *
     * @param updator 更新人
     */
    public void setUpdator(String updator) {
        this.updator = updator;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}