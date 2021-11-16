package cn.gy.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 
 * @author JDChen
 *
 */
@ApiModel("角色菜单")
public class RoleMenuVo {
    @ApiModelProperty(value = "角色ID", required = true)
    private String roleId;

    @ApiModelProperty(value = "已分配菜单", required = true)
    private List<MenuVo> assignMenuList;

    @ApiModelProperty(value = "未分配菜单")
    private List<MenuVo> unAssignMenuList;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public List<MenuVo> getAssignMenuList() {
        return assignMenuList;
    }

    public void setAssignMenuList(List<MenuVo> assignMenuList) {
        this.assignMenuList = assignMenuList;
    }

    public List<MenuVo> getUnAssignMenuList() {
        return unAssignMenuList;
    }

    public void setUnAssignMenuList(List<MenuVo> unAssignMenuList) {
        this.unAssignMenuList = unAssignMenuList;
    }

    @Override
    public String toString() {
        return "RoleMenuVo{" +
                "roleId='" + roleId + '\'' +
                ", assignMenuList=" + assignMenuList +
                ", unAssignMenuList=" + unAssignMenuList +
                '}';
    }
}
