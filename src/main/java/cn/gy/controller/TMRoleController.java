package cn.gy.controller;


import cn.gy.bean.PageInfoVo;
import cn.gy.bean.RoleVo;
import cn.gy.bean.TMAccount;
import cn.gy.bean.TMRole;
import cn.gy.constant.CategoryEnum;
import cn.gy.core.web.Result;
import cn.gy.core.web.ResultGenerator;
import cn.gy.service.TMAccountService;
import cn.gy.service.TMRoleService;
import cn.gy.util.Sequence;
import cn.gy.validation.Insert;
import cn.gy.validation.Update;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
* Created by JDChen on 2019/07/18.
*/
@Api(tags = "角色管理")
@RestController
@RequestMapping("/aiverify/v1/tMRole")
@Slf4j
public class TMRoleController {
    @Resource
    private TMRoleService tMRoleService;
    
    @Resource
    private TMAccountService tMAccountService;
    
    @ApiOperation(value = "新增角色")
    @PostMapping
    public Result add(@Validated({Insert.class})
    				  @RequestBody TMRole tMRole) {
        BigInteger id = BigInteger.valueOf(Long.parseLong(Sequence.nextVal()));
        Condition condition = new Condition(TMRole.class);
        condition.createCriteria().andEqualTo("roleName", tMRole.getRoleName());
        if (tMRoleService.countByCondition(condition) > 0) {
        	return ResultGenerator.genFailResult("角色名称已存在");
        }
            
        tMRole.setRoleId(id);
        tMRole.setCategory(CategoryEnum.ORDINARY.getType());
        tMRoleService.save(tMRole);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "删除角色")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable BigInteger id) {
    	TMRole tMRoleDelete = tMRoleService.findBy("roleId", id);
    	if( tMRoleDelete== null) {
    		return ResultGenerator.genFailResult(
                    String.format("id为%d的角色不存在", id));
    	}
    	if(!tMRoleDelete.getCategory().equals(CategoryEnum.ORDINARY.getType())) {
    		return ResultGenerator.genFailResult(
                    String.format("id为%d的角色不是普通角色，不能删除", id));
    	}
    	Condition condition = new Condition(TMAccount.class);
    	Condition.Criteria deleteCriteria = condition.createCriteria();
    	deleteCriteria.andEqualTo("roleId", id);
    	List<TMAccount> list = tMAccountService.findByCondition(condition);
    	List<TMAccount> ordinaryList = list.stream().filter(t -> !t.getAccountType().equals(new Byte("3")))
    			.collect(Collectors.toList());
    	if(ordinaryList != null && !ordinaryList.isEmpty()) {
    		return ResultGenerator.genFailResult(
                    String.format("该角色对应生效账号，不能删除", id));
    	}

        tMRoleService.doDelete(id);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "编辑角色")
    @PutMapping
    public Result update(@Validated({Update.class})
    					 @RequestBody TMRole tMRole) {
    	TMRole eEditTMRole = tMRoleService.findBy("roleId", tMRole.getRoleId());
    	if(!eEditTMRole.getCategory().equals(CategoryEnum.ORDINARY.getType())) {
    		return ResultGenerator.genFailResult(
                    String.format("id为%d的角色不是普通角色，不能编辑", eEditTMRole.getRoleId()));
    	} else if(!eEditTMRole.getRoleName().equals(tMRole.getRoleName())) {
    		Condition condition = new Condition(TMRole.class);
        	Condition.Criteria tMRoleCriteria = condition.createCriteria();
        	tMRoleCriteria.andEqualTo("roleName", tMRole.getRoleName());
        	List<TMRole> list = tMRoleService.findByCondition(condition);
        	if(list != null && !list.isEmpty()) {
        		return ResultGenerator.genFailResult("角色名称已存在");
            }
            tMRoleService.doUpdate(tMRole);
		}
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "分页展示角色信息")
    @ApiImplicitParams({
                @ApiImplicitParam(name = "page", value = "当前页", required = true, paramType = "query", dataType = "int", example = "1"),
                @ApiImplicitParam(name = "size", value = "单页记录条数", required = true, paramType = "query", dataType = "int", example = "10"),
                @ApiImplicitParam(name = "qName", value = "角色名称", paramType = "query", dataType = "string"),
                @ApiImplicitParam(name = "qCategory", value = "角色类别", paramType = "query", dataType = "String[]")
        })
    @GetMapping
    public Result<PageInfoVo<RoleVo>> list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size,
                                           @RequestParam(required = false) String qName, @RequestParam(required = false) String[] qCategory) {
        PageHelper.startPage(page, size);
        List<RoleVo> list = tMRoleService.getRoleList(qName, qCategory);
        PageInfo<RoleVo> pageInfo = new PageInfo<>(list);
        List<RoleVo> roleVoList = pageInfo.getList();
        roleVoList.forEach(t -> t.setCategory(CategoryEnum.getEnumName(Integer.parseInt(t.getCategory()))));
        return ResultGenerator.genSuccessResult(
                new PageInfoVo<>(roleVoList,pageInfo.getTotal()));
    }
}
