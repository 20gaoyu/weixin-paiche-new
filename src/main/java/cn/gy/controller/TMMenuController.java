package cn.gy.controller;


import cn.gy.bean.MenuTree;
import cn.gy.bean.Node;
import cn.gy.bean.TMMenu;
import cn.gy.constant.OperationEnum;
import cn.gy.core.web.Result;
import cn.gy.core.web.ResultGenerator;
import cn.gy.service.TMMenuService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by JDChen on 2019/7/30/0030
 */
@Api(tags = "菜单管理")
@RestController
@RequestMapping("/aiverify/v1/tMMenu")
@Slf4j
public class TMMenuController {
    @Resource
    private TMMenuService tMMenuService;

    @ApiOperation(value = "新增菜单")
    @PostMapping
    public Result add(@RequestBody TMMenu tMMenu) {
    	Integer id = tMMenu.getMenuId();//作为新增parent_id
    	TMMenu newTMMenu = tMMenuService.findById(id);
    	if(newTMMenu == null) {
    		return ResultGenerator.genFailResult(
                    String.format("不存在该条菜单,无法增加"));
    	}else if (tMMenu.getOperation().equals(OperationEnum.LEVELONE.getType())
    			|| tMMenu.getOperation().equals(OperationEnum.LEVELThREE.getType())) {
    		return ResultGenerator.genFailResult("一级菜单和三级菜单不能增加");
		}
        tMMenuService.addMenu(id,tMMenu);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "删除菜单")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
    	TMMenu deleteTMMenu = tMMenuService.findById(id);
    	if(deleteTMMenu == null) {
    		return ResultGenerator.genFailResult(
                    String.format("id为%d的菜单不存在，无法删除", id));
    	}else if (OperationEnum.LEVELThREE.getType().equals(deleteTMMenu.getOperation())) {
    		Condition condition = new Condition(TMMenu.class);
        	Condition.Criteria tMMenuCriteria = condition.createCriteria();
        	tMMenuCriteria.andEqualTo("parentId", deleteTMMenu.getParentId());
        	tMMenuCriteria.andGreaterThan("menuOrder", deleteTMMenu.getMenuOrder());
        	List<TMMenu> list = tMMenuService.findByCondition(condition);
        	if(list != null && !list.isEmpty()) {
        		list.forEach(t -> {t.setMenuOrder(t.getMenuOrder()-1);tMMenuService.update(t);});
        	}

            tMMenuService.deleteMenu(id);
            return ResultGenerator.genSuccessResult();
		}else {
    		return ResultGenerator.genFailResult("一级菜单和二级菜单不能删除");
    	}
    	
    }

    /**
     * @return Node<TMMenu>
     * @description 返回菜单(if_show = 1, 即可用菜单)递归对象
     */
    @GetMapping("/getDisplayMenuNodes")
    @ApiOperation(value = "getDisplayMenuNodes", notes = "获取有效菜单递归对象集")
    public Result<List<Node<TMMenu>>> getAllDisplayMenuNode() {
        Node<TMMenu> node = tMMenuService.getAllDisplayMenuNode();
        return ResultGenerator.genSuccessResult(node.getChildren());
    }

    /**
     * @return Node<TMMenu>
     * @description 返回菜单(if_show = 1, 即可用菜单)递归对象
     */
    @GetMapping("/getDisplayMenus")
    @ApiOperation(value = "getDisplayMenus", notes = "获取有效菜单递归对象集")
    @ApiImplicitParam(name = "ifshow", value = "是否显示", paramType = "query", dataType = "String")
    public Result<List<MenuTree>> getAllDisplayMenu(@RequestParam(required = false) String ifshow) {
        List<MenuTree> menus = tMMenuService.getAllDisplayMenu(ifshow);
        return ResultGenerator.genSuccessResult(menus);
    }

    /**
     * 根据登录用户获取有效菜单
     * @return 返回登录用户的菜单
     */
    @GetMapping("/getDisplayMenusByRole")
    @ApiOperation(value = "getDisplayMenusByRole", notes = "根据登录用户获取有效菜单")
    public Result<List<MenuTree>> getDisplayMenuByRole() {
        List<MenuTree> menus = tMMenuService.getDisplayMenuByRole();
        return ResultGenerator.genSuccessResult(menus);
    }
}
