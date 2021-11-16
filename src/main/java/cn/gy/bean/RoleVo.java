package cn.gy.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 
 * @author JDChen
 *
 */
@ApiModel("客户信息")
public class RoleVo {
    @ApiModelProperty("角色标识")
    private String roleId;

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("角色类别")
    private String category;

    @ApiModelProperty("创建时间，格式:yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "RoleVo{" +
                "roleId='" + roleId + '\'' +
                ", roleName='" + roleName + '\'' +
                ", category='" + category + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
