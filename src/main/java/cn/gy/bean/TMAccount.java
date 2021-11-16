package cn.gy.bean;



import cn.gy.validation.Update;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.Date;

//@Data
@Table(name = "t_m_account")
public class TMAccount {
    /**
     * 账号标识
     */
    @Id
    @NotNull(groups = {Update.class}, message = "客户标识不能为空")
    @Column(name = "account_id")
    private  Long accountId;
    /**
     * 账号名称
     */
    @Column(name = "account_name")
    private String accountName;
    /**
     * 账号分类1为系统，2为普通默认，3为无效
     */
    @Column(name = "account_type")
    private Byte accountType;
    /**
     * 密码
     */
    @Column(name = "password")
    private String password;
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
     * 盐
     */
    @Column(name = "salt")
    private String salt;
    /**
     * 角色标识
     */
    @Column(name = "role_id")
    private BigInteger roleId;
    /**
     * 角色名称
     */
    @Column(name = "role_name")
    private String roleName;
    /**
     * 创建
     */
    @Column(name = "create_time")
    private Date createTime;
    /**
     * 更新时间
     */
    @Column(name = "last_login_time")
    private Date lastLoginTime;
    /**
     * 是否重置密码
     */
    @Column(name = "if_reset_passwd")
    private Integer ifResetPasswd;

    /**
     * 已完成的用户指引功能清单(逗号分割)
     */
    @Column(name = "completed_guide")
    private String completedGuide;

    public @NotNull(groups = {Update.class}, message = "客户标识不能为空") Long getAccountId() {
        return accountId;
    }

    public void setAccountId(@NotNull(groups = {Update.class}, message = "客户标识不能为空") Long accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Byte getAccountType() {
        return accountType;
    }

    public void setAccountType(Byte accountType) {
        this.accountType = accountType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public BigInteger getRoleId() {
        return roleId;
    }

    public void setRoleId(BigInteger roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Integer getIfResetPasswd() {
        return ifResetPasswd;
    }

    public void setIfResetPasswd(Integer ifResetPasswd) {
        this.ifResetPasswd = ifResetPasswd;
    }

    public String getCompletedGuide() {
        return completedGuide;
    }

    public void setCompletedGuide(String completedGuide) {
        this.completedGuide = completedGuide;
    }
}

