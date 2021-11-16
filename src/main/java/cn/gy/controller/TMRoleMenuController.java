package cn.gy.controller;

import cn.gy.bean.RoleMenuVo;
import cn.gy.bean.TMRole;
import cn.gy.constant.CategoryEnum;
import cn.gy.core.web.Result;
import cn.gy.core.web.ResultGenerator;
import cn.gy.service.TMRoleMenuService;
import cn.gy.service.TMRoleService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigInteger;

/**
* Created by JDChen on 2019/07/24.
*/
@Api(tags = "角色菜单管理")
@RestController
@RequestMapping("/aiverify/v1/tMRoleMenu")
public class TMRoleMenuController {
    @Resource
    private TMRoleMenuService tMRoleMenuService;
    
    @Resource
    private TMRoleService tMRoleService;

    @ApiOperation(value = "保存角色对应的菜单权限")
    @PostMapping
    public Result add(@RequestBody RoleMenuVo roleMenuVo) {
    	BigInteger roleId = BigInteger.valueOf(Long.parseLong(roleMenuVo.getRoleId()));
    	TMRole role = tMRoleService.findBy("roleId", roleId);
    	if(role == null || role.getCategory().equals(CategoryEnum.INVALID.getType())) {
    		return ResultGenerator.genFailResult("不存在该角色或该角色已失效，无法保存");
    	}
        tMRoleMenuService.saveOrUpdate(roleMenuVo);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "返回角色对应的菜单权限")
    @GetMapping("/{roleId}")
    public Result<RoleMenuVo> detail(@PathVariable BigInteger roleId) {
    	TMRole role = tMRoleService.findBy("roleId", roleId);
    	if(role == null || role.getCategory().equals(CategoryEnum.INVALID.getType())) {
    		return ResultGenerator.genFailResult("不存在该角色或该角色已失效");
    	}
        RoleMenuVo tMRoleMenu = tMRoleMenuService.getRoleMenu(roleId);
        return ResultGenerator.genSuccessResult(tMRoleMenu);
    }

}
