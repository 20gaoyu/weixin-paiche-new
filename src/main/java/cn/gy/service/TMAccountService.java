package cn.gy.service;

import cn.gy.bean.AccountPasswordVo;
import cn.gy.bean.AccountVo;
import cn.gy.bean.NewAccount;
import cn.gy.bean.TMAccount;
import cn.gy.core.service.AbstractService;
import cn.gy.core.web.Result;
import cn.gy.core.web.ResultGenerator;
import cn.gy.dao.TMAccountMapper;
import cn.gy.util.EnvUtil;
import cn.gy.util.Sequence;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;


/**
 * Created by gaoyu on 2019/07/18.
 */
@Slf4j
@Service
@Transactional
public class TMAccountService extends AbstractService<TMAccount> {
	@Resource
	private TMAccountMapper tMAccountMapper;

	public List<AccountVo> getAccountList(Long accountId, Long customerId, String roleName, String accountType) {
		return tMAccountMapper.getAccountList(accountId, customerId, roleName, accountType);
	}

	public TMAccount judgeAccount(String accountName, String passwd) {

		Condition condition = new Condition(TMAccount.class);
		condition.createCriteria().andEqualTo("accountName", accountName).andNotEqualTo("accountType",3);
		List<TMAccount> instList = tMAccountMapper.selectByCondition(condition);

		if (instList.size() < 1) {
			log.info("没有对应账号:" + accountName);
			return null;
		} else if (instList.size() > 1) {
			log.info("存在重复的账号:" + accountName);
			return null;
		} else {
			TMAccount tMAccount = instList.get(0);
			String salt = tMAccount.getSalt();
			log.info("passwd:" + DigestUtils.md5Hex(DigestUtils.md5Hex(passwd) + tMAccount.getSalt()));
			if (tMAccount.getPassword().equals(DigestUtils.md5Hex(DigestUtils.md5Hex(passwd) + tMAccount.getSalt())))
			{
				return tMAccount;
			}
			log.info("用户名密码错误:" + accountName);
			return null;
		}
	}
	public Result<AccountPasswordVo> addAccount(NewAccount newAccount)
	{
		String customerName = newAccount.getCustomerName();
		String accountName = newAccount.getAccountName();
		String roleName=newAccount.getRoleName();
		log.info("添加客户是："+customerName+"账号："+accountName+"角色："+roleName);
		if(StringUtils.isBlank(customerName)
				|| StringUtils.isBlank(accountName) || roleName == null) {

			return  ResultGenerator.genFailResult("必填项为空，新增账号失败");
		}
		//检查数据库中是否已经有了同样的账号
		if(this.findBy("accountName",accountName)!=null)
		{
			return  ResultGenerator.genFailResult("已经存在相同的账号："+accountName);
		}
		TMAccount tMAccount = new TMAccount();
		AccountPasswordVo accountPasswordVo = new AccountPasswordVo();
		BeanUtils.copyProperties(newAccount, tMAccount);
		//salt随机10位字符串
		//password随机8位字符串
		Long accountId = Long.valueOf(Long.parseLong(Sequence.nextVal()));
		//String salt= RandomStringUtils.randomAscii(10);
		String salt= RandomStringUtils.randomAlphanumeric(10);
		String password= RandomStringUtils.randomAlphanumeric(8);
		tMAccount.setAccountId(accountId);
		tMAccount.setSalt(salt);
		tMAccount.setPassword(DigestUtils.md5Hex(DigestUtils.md5Hex(password)+salt));
		tMAccount.setAccountType(new Byte("2"));
		this.save(tMAccount);
		accountPasswordVo.setAccountName(accountName);
		accountPasswordVo.setPassword(password);
		return ResultGenerator.genSuccessResult(accountPasswordVo);
	}

	public Result deleteAllbyCustomer(Long customerId)
	{
		Condition condition = new Condition(TMAccount.class);
		condition.createCriteria().andEqualTo("customerId", customerId).andNotEqualTo("accountType",new Byte("3"));
		List<TMAccount> instList = tMAccountMapper.selectByCondition(condition);
		for(TMAccount tmAccount:instList)
		{
			tmAccount.setAccountType(new Byte("3"));
			this.update(tmAccount);
		}
			return ResultGenerator.genSuccessResult(String.format("id为%d的客户删除成功", customerId));
	}
	
	public TMAccount getAccount( String accountName)
	{
		Condition condition = new Condition(TMAccount.class);
		condition.createCriteria().andEqualTo("accountName", accountName).andNotEqualTo("accountType",new Byte("3"));
		List<TMAccount> instList = tMAccountMapper.selectByCondition(condition);
		if(null!=instList&&instList.size()==1){
			return instList.get(0);
		}else
			return null;
	}

	public void updateRoleNameByRoleId(String roleName, BigInteger roleId) {
		tMAccountMapper.updateRoleNameByRoleId(roleName, roleId);
	}

	public Result editAccount(AccountVo accountVo) {
		if (null == accountVo || StringUtils.isBlank(accountVo.getAccountId())|| StringUtils.isBlank(accountVo.getRoleId())) {
			return ResultGenerator.genFailResult("ID不能为空");
		}
		TMAccount tmAccount = new TMAccount();
		tmAccount.setAccountId(Long.valueOf(accountVo.getAccountId()));
		tmAccount.setRoleId(new BigInteger(accountVo.getRoleId()));
		tmAccount.setRoleName(accountVo.getRoleName());
		//BeanUtils.copyProperties(accountVo, tMAccount);
		log.info("更新：" + accountVo.getAccountId() + ":" + accountVo.getRoleName() + ":" + accountVo.getRoleId());
		this.update(tmAccount);
		return ResultGenerator.genSuccessResult("编辑成功");

	}

	public void updateCustomerName(Long customerId, String customerName) {
		tMAccountMapper.updateCustomerName(customerId, customerName);
	}

	public void updateByRoleId(BigInteger roleId,String roleName,BigInteger sourceRoleId) {
		TMAccount tmAccount = new TMAccount();
		tmAccount.setRoleId(roleId);
		tmAccount.setRoleName(roleName);
		Condition condition = new Condition(TMAccount.class);
		condition.createCriteria().andEqualTo("roleId", sourceRoleId);
		int count=tMAccountMapper.updateByCondition(tmAccount,condition);
		log.info("更新："+count+"条记录");
	}

	public boolean isCurrentAccount(Long accountId) {
		Long curAccountId = EnvUtil.getEnv().getAccountId();
//		Long curCustomerId = EnvUtil.getEnv().getCustomerId();
		if(!curAccountId.equals(accountId)){
			return false;
		}
		return true;
	}
	Integer getAllUserGroupByDay(LocalDate endDay)
	{
		return tMAccountMapper.getAllUserGroupByDay(endDay);
	}
	public List<TMAccount> getAccountNameByLike(String accountName,String customerName) {
		Condition condition = new Condition(TMAccount.class);
		Condition.Criteria criteria = condition.createCriteria();
		if(StringUtils.isNotBlank(customerName)) {
			criteria.andEqualTo("customerName", customerName);
		}
		if(StringUtils.isNotBlank(accountName)) {
			criteria.andLike("accountName", "%" + accountName + "%").andNotEqualTo("accountType", 3);
		}
		List<TMAccount> instList = tMAccountMapper.selectByCondition(condition);

		return instList;
	}
}
