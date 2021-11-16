package cn.gy.bean;

import cn.gy.validation.Insert;
import cn.gy.validation.Update;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigInteger;
import java.util.Date;

@Table(name = "t_m_role")
public class TMRole {
    /**
     * 角色ID
     */
    @Id
    @Column(name = "role_id")
    @NotNull(groups = {Update.class}, message = "角色id不能为空")
    private BigInteger roleId;

    /**
     * 角色名称
     */
    @Column(name = "role_name")
    @NotEmpty(groups = {Update.class, Insert.class}, message = "角色名称不能为空")
    @Size(groups = {Update.class, Insert.class}, min = 1, max = 16,
            message = "角色名称必须在16个字符内")
    private String roleName;

    /**
     * 角色类别
     * 角色类别：0：无效，1：系统，2：普通，3：CDN，4：媒体存储
     */
    private Integer category;

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
     * 获取角色ID
     *
     * @return role_id - 角色ID
     */
    public BigInteger getRoleId() {
        return roleId;
    }

    /**
     * 设置角色ID
     *
     * @param roleId 角色ID
     */
    public void setRoleId(BigInteger roleId) {
        this.roleId = roleId;
    }

    /**
     * 获取角色名称
     *
     * @return role_name - 角色名称
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * 设置角色名称
     *
     * @param roleName 角色名称
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * 获取角色类别
     *
     * @return category - 角色类别
     */
    public Integer getCategory() {
        return category;
    }

    /**
     * 设置角色类别
     *
     * @param category 角色类别
     */
    public void setCategory(Integer category) {
        this.category = category;
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