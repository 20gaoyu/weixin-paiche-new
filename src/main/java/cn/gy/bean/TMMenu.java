package cn.gy.bean;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "t_m_menu")
public class TMMenu {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Integer menuId;

    /**
     * 菜单名称
     */
    @Column(name = "menu_name")
    private String menuName;

    /**
     * 菜单类型
     */
    @Column(name = "menu_type")
    private Byte menuType;

    /**
     * 父级菜单ID,-1:一级目录；1：二级目录
     */
    @Column(name = "parent_id")
    private Integer parentId;

    /**
     * 排序
     */
    @Column(name = "menu_order")
    private Integer menuOrder;

    /**
     * 是否可用，0：不可用，1：可用
     */
    @Column(name = "if_show")
    private Byte ifShow;

    /**
     * URL
     */
    private String url;

    /**
     * 方法
     */
    private String method;

    /**
     * 操作
     */
    private Integer operation;

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
     * 获取菜单名称
     *
     * @return menu_name - 菜单名称
     */
    public String getMenuName() {
        return menuName;
    }

    /**
     * 设置菜单名称
     *
     * @param menuName 菜单名称
     */
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    /**
     * 获取菜单类型
     *
     * @return menu_type - 菜单类型
     */
    public Byte getMenuType() {
        return menuType;
    }

    /**
     * 设置菜单类型
     *
     * @param menuType 菜单类型
     */
    public void setMenuType(Byte menuType) {
        this.menuType = menuType;
    }

    /**
     * 获取父级菜单ID,-1:一级目录；1：二级目录
     *
     * @return parent_id - 父级菜单ID,-1:一级目录；1：二级目录
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * 设置父级菜单ID,-1:一级目录；1：二级目录
     *
     * @param parentId 父级菜单ID,-1:一级目录；1：二级目录
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取排序
     *
     * @return menu_order - 排序
     */
    public Integer getMenuOrder() {
        return menuOrder;
    }

    /**
     * 设置排序
     *
     * @param menuOrder 排序
     */
    public void setMenuOrder(Integer menuOrder) {
        this.menuOrder = menuOrder;
    }

    /**
     * 获取是否可用，0：不可用，1：可用
     *
     * @return if_show - 是否可用，0：不可用，1：可用
     */
    public Byte getIfShow() {
        return ifShow;
    }

    /**
     * 设置是否可用，0：不可用，1：可用
     *
     * @param ifShow 是否可用，0：不可用，1：可用
     */
    public void setIfShow(Byte ifShow) {
        this.ifShow = ifShow;
    }

    /**
     * 获取URL
     *
     * @return url - URL
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置URL
     *
     * @param url URL
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取方法
     *
     * @return method - 方法
     */
    public String getMethod() {
        return method;
    }

    /**
     * 设置方法
     *
     * @param method 方法
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * 获取操作
     *
     * @return operation - 操作
     */
    public Integer getOperation() {
        return operation;
    }

    /**
     * 设置操作
     *
     * @param operation 操作
     */
    public void setOperation(Integer operation) {
        this.operation = operation;
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
}