package cn.gy.bean;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kuyun
 * @date 2018-08-01
 * @description 菜单树，区别于Node.java的描述。去掉content父级包含
 */
@Slf4j
public class MenuTree {
    private Integer menuId;
    private String menuName;
    private String method;
    private Integer parentId;
    private Integer menuOrder;
    private String url;
    private Integer operation;

    private List<MenuTree> children = new ArrayList<>();

    public MenuTree() {

    }

    public MenuTree(Integer menuId, String menuName, String method, Integer parentId, 
    		Integer menuOrder, String url, Integer operation) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.method = method;
        this.parentId = parentId;
        this.menuOrder = menuOrder;
        this.url = url;
        this.operation = operation;
    }

    public void addChild(MenuTree menu) {
        this.getChildren().add(menu);
    }

    public void cleanChild() {
        this.getChildren().clear();
    }

    public boolean removeChild(int idx) {
        try {
            this.getChildren().remove(idx);
        } catch (Exception e) {
            log.info("context",e);
            return false;
        }
        return true;
    }

    /**
     * @return the menuId
     */
    public Integer getMenuId() {
        return menuId;
    }

    /**
     * @param menuId the id to set
     */
    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    /**
     * @return the menuName
     */
    public String getMenuName() {
        return menuName;
    }

    /**
     * @param menuName the menuName to set
     */
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    /**
     * @return the menuLink
     */
    public String getMethod() {
        return method;
    }

    /**
     * @param method the menuLink to set
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * @return the parentId
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * @param parentId the parentId to set
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * @return the menuOrder
     */
    public Integer getMenuOrder() {
        return menuOrder;
    }

    /**
     * @param menuOrder the menuOrder to set
     */
    public void setMenuOrder(Integer menuOrder) {
        this.menuOrder = menuOrder;
    }
    
    public Integer getOperation() {
		return operation;
	}

	public void setOperation(Integer operation) {
		this.operation = operation;
	}

	/**
     * @return the children
     */
    public List<MenuTree> getChildren() {
        return children;
    }

    /**
     * @param children the children to set
     */
    public void setChildren(List<MenuTree> children) {
        this.children = children;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
