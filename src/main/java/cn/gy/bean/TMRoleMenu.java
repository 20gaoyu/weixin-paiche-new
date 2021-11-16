package cn.gy.bean;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;

@Table(name = "t_m_role_menu")
public class TMRoleMenu {
    @Id
    @Column(name = "role_menu_id")
    private BigInteger roleMenuId;

    @Column(name = "menu_id")
    private Integer menuId;

    /**
     * 角色ID
     */
    @Column(name = "role_id")
    private BigInteger roleId;

    /**
     * 创建人ID
     */
    private String creator;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * @return role_menu_id
     */
    public BigInteger getRoleMenuId() {
        return roleMenuId;
    }

    /**
     * @param roleMenuId
     */
    public void setRoleMenuId(BigInteger roleMenuId) {
        this.roleMenuId = roleMenuId;
    }

    /**
     * @return menu_id
     */
    public Integer getMenuId() {
        return menuId;
    }

    /**
     * @param menuId
     */
    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

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
     * 获取创建人ID
     *
     * @return creator - 创建人ID
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 设置创建人ID
     *
     * @param creator 创建人ID
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
}