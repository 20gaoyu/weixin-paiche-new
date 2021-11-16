package cn.gy.service;

import cn.gy.bean.DispatchCarDetail;
import cn.gy.bean.Member;
import cn.gy.bean.TMAccount;
import cn.gy.core.service.AbstractService;
import cn.gy.core.web.Result;
import cn.gy.core.web.ResultGenerator;
import cn.gy.dao.TMDispatchCarDetailMapper;
import cn.gy.dao.TMMemberMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by gaoyu on 2019/07/18.
 */
@Slf4j
@Service
@Transactional
public class TMDispatchCarDetailService extends AbstractService<DispatchCarDetail> {
	@Resource
	private TMDispatchCarDetailMapper tmDispatchCarDetailMapper;

	public List<DispatchCarDetail> getList(String name) {
		return tmDispatchCarDetailMapper.getList(name);
	}

	public Result<String> add(DispatchCarDetail dispatchCarDetail)
	{
		this.save(dispatchCarDetail);
		return ResultGenerator.genSuccessResult("add success");
	}

	public Result deleteAllbyDetail(long id)
	{
		deleteById(id);
		return ResultGenerator.genSuccessResult(String.format("id为%d的删除成功", id));
	}


	public Result updateDetail(DispatchCarDetail dispatchCarDetail) {
		if (null == dispatchCarDetail || dispatchCarDetail.getId()==0 ) {
			return ResultGenerator.genFailResult("ID不能为空");
		}
		//BeanUtils.copyProperties(accountVo, tMAccount);
		log.info("更新：" + dispatchCarDetail.getId());
		this.update(dispatchCarDetail);
		return ResultGenerator.genSuccessResult("编辑成功");

	}


	public List<DispatchCarDetail> getAccountNameByLike(String applicant,String status) {
		Condition condition = new Condition(TMAccount.class);
		Condition.Criteria criteria = condition.createCriteria();
		if(StringUtils.isNotBlank(applicant)) {
			criteria.andEqualTo("applicant", applicant);
		}
		if(StringUtils.isNotBlank(status)) {
			criteria.andEqualTo("status", status);
		}
		List<DispatchCarDetail> instList = tmDispatchCarDetailMapper.selectByCondition(condition);
		return instList;
	}
}
