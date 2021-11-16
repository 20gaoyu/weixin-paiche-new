package cn.gy.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * 
 * @author JDChen
 *
 */
@ApiModel("客户菜单信息")
public class HasMenuVo {
    @ApiModelProperty("菜单ID")
    private Integer menuId;

    @ApiModelProperty("菜单名称")
    private String menuName;

    @ApiModelProperty("菜单URL")
    private String url;

    @ApiModelProperty("请求方法")
    private String method;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return "HasMenuVo{" +
                "menuId=" + menuId +
                ", menuName='" + menuName + '\'' +
                ", url='" + url + '\'' +
                ", method='" + method + '\'' +
                '}';
    }
}
