package cn.gy.service;

import cn.gy.bean.*;
import cn.gy.core.service.AbstractService;
import cn.gy.core.web.Result;
import cn.gy.core.web.ResultGenerator;
import cn.gy.dao.TMAccountMapper;
import cn.gy.dao.TMMemberMapper;
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
public class TMMemberService extends AbstractService<Member> {
	@Resource
	private TMMemberMapper tmMemberMapper;

	public List<Member> getList(String memberName) {
		return tmMemberMapper.getList(memberName);
	}

	public Result<String> add(Member member)
	{
		String departmentName = member.getDepartmentName();
		String accountName = member.getAccountName();
		String position=member.getPosition();
		log.info("添加客户是："+departmentName+"账号："+accountName+"角色："+position);
		if(StringUtils.isBlank(departmentName)
				|| StringUtils.isBlank(accountName)) {
			return  ResultGenerator.genFailResult("必填项为空，新增账号失败");
		}
		this.save(member);
		return ResultGenerator.genSuccessResult("add success");
	}

	public Result deleteAllbyMember(long id)
	{
		deleteById(id);
		return ResultGenerator.genSuccessResult(String.format("id为%d的删除成功", id));
	}
	public Member findByTele(String telephone){
		Condition condition = new Condition(Member.class);
		condition.createCriteria().andEqualTo("telephone", telephone);
		List<Member> list = findByCondition(condition);
		if(list.isEmpty()){
			return null;
		}else{
			return list.get(0);
		}
	}
	public Member findByOenId(String openid){
		Condition condition = new Condition(Member.class);
		condition.createCriteria().andEqualTo("openId", openid);
		List<Member> list = findByCondition(condition);
		if(list.isEmpty()){
			return null;
		}else{
			return list.get(0);
		}
	}
	public Result updateMember(Member member) {
		if (null == member || member.getId()==0 ) {
			return ResultGenerator.genFailResult("ID不能为空");
		}
		//BeanUtils.copyProperties(accountVo, tMAccount);
		log.info("更新：" + member.getId() + ":" + member.getPosition());
		this.update(member);
		return ResultGenerator.genSuccessResult("编辑成功");

	}

	public Member getMember(String departmentName,String position) {
		Condition condition = new Condition(TMAccount.class);
		Condition.Criteria criteria = condition.createCriteria();
		criteria.andEqualTo("departmentName", departmentName);
		criteria.andLike("position", position);
		List<Member> instList = tmMemberMapper.selectByCondition(condition);
		if(instList.isEmpty()){
			return null;
		}else{
			return instList.get(0);
		}
	}

	public List<Member> getAccountNameByLike(String accountName,String departmentName) {
		Condition condition = new Condition(TMAccount.class);
		Condition.Criteria criteria = condition.createCriteria();
		if(StringUtils.isNotBlank(departmentName)) {
			criteria.andEqualTo("departmentName", departmentName);
		}
		if(StringUtils.isNotBlank(accountName)) {
			criteria.andLike("accountName", "%" + accountName + "%");
		}
		List<Member> instList = tmMemberMapper.selectByCondition(condition);

		return instList;
	}
}
