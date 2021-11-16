package cn.gy.service;



import cn.gy.bean.MenuTree;
import cn.gy.bean.Node;
import cn.gy.bean.TMAccount;
import cn.gy.bean.TMMenu;
import cn.gy.constant.OperationEnum;
import cn.gy.core.service.AbstractService;
import cn.gy.dao.TMMenuMapper;
import cn.gy.util.EnvUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by JDChen on 2019/07/23.
 */
@Service
@Transactional
public class TMMenuService extends AbstractService<TMMenu> {
    @Resource
    private TMMenuMapper tMMenuMapper;

    @Resource
    private TMAccountService tMAccountService;

    @Resource
    private TMRoleMenuService tMRoleMenuService;


    /**
     * @return Node<TMMenu>
     * @description 查找菜单(if_show = 1, 即可用菜单)递归对象
     */
    public Node<TMMenu> getAllDisplayMenuNode() {
        Condition condition = new Condition(TMMenu.class);
        Condition.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("ifShow", new Byte("1"));

        List<TMMenu> displayMenus = findByCondition(condition);

        TMMenu mRoot = new TMMenu();
        mRoot.setMenuId(-1);
        Node<TMMenu> nodeRoot = new Node<>(mRoot);

        this.sortNodeTree(nodeRoot, displayMenus);
        return nodeRoot;
    }
    
    private void sortNodeTree(Node<TMMenu> node, List<TMMenu> menus) {
        for (TMMenu menu : menus) {
            if (menu.getParentId().equals(node.getContent().getMenuId())) {
                node.addChild(new Node<>(menu));
            }
        }

        if (node.getChildren().size() < 1) {
            return;
        }

        node.getChildren().sort(Comparator.comparingInt(o -> o.getContent().getMenuOrder()));

        for (Node<TMMenu> childNode : node.getChildren()) {
            this.sortNodeTree(childNode, menus);
        }
    }
    
    /**
     * @return List<MenuTree>
     * @description 返回菜单树，区别与Node节点菜单
     */
    public List<MenuTree> getAllDisplayMenu(String ifshow) {
        Condition condition = new Condition(TMMenu.class);
        Condition.Criteria criteria = condition.createCriteria();
        if(StringUtils.isNotBlank(ifshow)){
            criteria.andEqualTo("ifShow", new Byte("1"));
        }
        List<TMMenu> displayMenus = findByCondition(condition);
        return getSelectMenu(displayMenus);
    }
    
    /**
     * 根据登录用户获取有效菜单
     * @return 返回菜单树
     */
    public List<MenuTree> getDisplayMenuByRole() {
    	String account = EnvUtil.getEnv().getAccount();
    	if(account == null) {
    		return null;
    	}else {
    		List<TMMenu> displayMenus = findMenuByRole(account);
    		if(displayMenus != null && !displayMenus.isEmpty()){
                displayMenus = displayMenus.stream().filter(t -> t.getIfShow().equals(new Byte("1"))).collect(Collectors.toList());
            }
    		return (CollectionUtils.isNotEmpty(displayMenus))?getSelectMenu(displayMenus):null;
		}
	}

    private List<TMMenu> findMenuByRole(String account) {
        TMAccount tMAccount = tMAccountService.getAccount(account);
        if(tMAccount == null) {
            return null;
        }else {
            BigInteger roleId = tMAccount.getRoleId();
            return tMMenuMapper.findMenuByRole(roleId);
        }
    }

    public List<MenuTree> getSelectMenu(List<TMMenu> displayMenus) {
        List<MenuTree> menus = new ArrayList<>();
        for (TMMenu menu : displayMenus) {
            menus.add(new MenuTree(menu.getMenuId(), menu.getMenuName(), menu.getMethod(),
                    menu.getParentId(), menu.getMenuOrder(), menu.getUrl(), menu.getOperation()));
        }

        MenuTree rootMenu = new MenuTree();
        rootMenu.setMenuId(-1);

        this.sortMenuTree(rootMenu, menus);
        return rootMenu.getChildren();
    }
    
    private void sortMenuTree(MenuTree pTree, List<MenuTree> menus) {
        for (MenuTree menu : menus) {
            if (menu.getParentId().equals(pTree.getMenuId())) {
                pTree.addChild(menu);
            }
        }

        if (pTree.getChildren().size() < 1) {
            return;
        }

        pTree.getChildren().sort(Comparator.comparingInt(MenuTree::getMenuOrder));

        for (MenuTree childMenu : pTree.getChildren()) {
            this.sortMenuTree(childMenu, menus);
        }
    }

	public void addMenu(Integer id, TMMenu tMMenu) {
		Condition condition = new Condition(TMMenu.class);
    	Condition.Criteria tMMenuCriteria = condition.createCriteria();
    	tMMenuCriteria.andEqualTo("parentId", id);
    	List<TMMenu> list = this.findByCondition(condition);
    	if(list == null || list.isEmpty()) {
    		tMMenu.setMenuOrder(1);
    	}else {
    		tMMenu.setMenuOrder(list.size()+1);
    	}
    	tMMenu.setMenuId(0);
    	tMMenu.setParentId(id);
    	tMMenu.setMenuType(new Byte("1"));
    	tMMenu.setOperation(OperationEnum.LEVELThREE.getType());
        this.save(tMMenu);
	}

    public void deleteMenu(Integer id) {
        this.deleteById(id);
        tMRoleMenuService.deletedByMenuId(id);
    }
}
