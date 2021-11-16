package cn.gy.service;


import cn.gy.bean.HasMenuVo;
import cn.gy.bean.RoleMenuVo;
import cn.gy.bean.TMAccount;
import cn.gy.bean.TMRoleMenu;
import cn.gy.core.service.AbstractService;
import cn.gy.dao.TMRoleMenuMapper;
import cn.gy.util.EnvUtil;
import cn.gy.util.Sequence;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by JDChen on 2019/07/24.
 */
@Service
@Transactional
@Slf4j
public class TMRoleMenuService extends AbstractService<TMRoleMenu> {
    @Resource
    private TMRoleMenuMapper tMRoleMenuMapper;

    @Resource
    private TMAccountService tMAccountService;


    public RoleMenuVo getRoleMenu(BigInteger roleId) {
        RoleMenuVo roleMenuVo = new RoleMenuVo();
        roleMenuVo.setRoleId(String.valueOf(roleId));
        roleMenuVo.setAssignMenuList(tMRoleMenuMapper.getAssignMenuList(roleId));
        roleMenuVo.setUnAssignMenuList(tMRoleMenuMapper.getUnAssignMenuList(roleId));
        return roleMenuVo;
    }

    public void saveOrUpdate(RoleMenuVo roleMenuVo) {
        BigInteger roleId = BigInteger.valueOf(Long.parseLong(roleMenuVo.getRoleId()));
        Condition condition = new Condition(TMRoleMenu.class);
        condition.createCriteria().andEqualTo("roleId", roleMenuVo.getRoleId());
        tMRoleMenuMapper.deleteByCondition(condition);
        List<BigInteger> idList = roleMenuVo.getAssignMenuList().stream().map(menu -> BigInteger.valueOf(Long.parseLong(Sequence.nextVal()))).collect(Collectors.toList());
        tMRoleMenuMapper.insertBatch(idList, roleMenuVo.getAssignMenuList(), roleId, EnvUtil.getEnv().getAccount());
        //删除redis里角色对应的权限
//		List<String> keys = redisHelper.keys(SysConstant.REDIS_ROLE_MENU + roleMenuVo.getRoleId());
//		if(keys != null && !keys.isEmpty()){
//			stringRedisTemplate.delete(keys);
//		}
    }

    public boolean hasMenu(String account, String method, String url) {
        TMAccount tMAccount = tMAccountService.getAccount(account);
        if (tMAccount == null) {
            return false;
        }
        BigInteger roleId = tMAccount.getRoleId();
        return ifHasMenu(roleId, method, url);
    }

    private boolean ifHasMenu(BigInteger roleId, String method, String url) {
        List<HasMenuVo> menuList = getMenuByBase(roleId);
        if (menuList != null && !menuList.isEmpty()) {
            List<HasMenuVo> list = menuList.stream().filter(t -> t.getUrl().equals(url) && t.getMethod().equals(method)).
                    collect(Collectors.toList());
            if (list != null && !list.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private List<HasMenuVo> getMenuByBase(BigInteger roleId) {
        List<HasMenuVo> menuList = tMRoleMenuMapper.getThirdLevelMenu(roleId);//查找数据库
        String menuJson = JSON.toJSONString(menuList);
        return menuList;
    }

    public void deletedByRoleId(BigInteger id) {
        tMRoleMenuMapper.deletedByRoleId(id);
    }

    public void deletedByMenuId(Integer id) {
        tMRoleMenuMapper.deletedByMenuId(id);

    }

}
