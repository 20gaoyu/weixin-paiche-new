package cn.gy.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @author JDChen
 *
 */
@ApiModel("菜单")
public class MenuVo {

    @ApiModelProperty("菜单ID")
    private Integer menuId;
    @ApiModelProperty("菜单名称")
    private String menuName;
    @ApiModelProperty("菜单父ID")
    private Integer parentId;


    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return "MenuVo{" +
                "menuId=" + menuId +
                ", menuName='" + menuName + '\'' +
                ", parentId=" + parentId +
                '}';
    }
}
