package cn.gy.service;


import cn.gy.bean.RoleVo;
import cn.gy.bean.TMRole;
import cn.gy.core.service.AbstractService;
import cn.gy.dao.TMRoleMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;


/**
 * Created by JDChen on 2019/07/18.
 */
@Service
@Transactional
public class TMRoleService extends AbstractService<TMRole> {
    @Resource
    private TMRoleMapper tMRoleMapper;

	@Resource
	private TMRoleMenuService tMRoleMenuService;

	@Resource
	private TMAccountService tMAccountService;

    

	public void updateStatusById(BigInteger id) {
		tMRoleMapper.updateStatusById(id);
	}

	public List<RoleVo> getRoleList(String qName, String[] qCategory) {
		return tMRoleMapper.getRoleList(qName, qCategory);
	}

    public void doDelete(BigInteger id) {
		this.updateStatusById(id);
		tMRoleMenuService.deletedByRoleId(id);
    }

	public void doUpdate(TMRole tMRole) {
		this.update(tMRole);
		tMAccountService.updateRoleNameByRoleId(tMRole.getRoleName(),tMRole.getRoleId());
	}
}
