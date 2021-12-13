package cn.gy.service;

import cn.gy.bean.DispatchCarDetail;
import cn.gy.bean.DispatchCarDetailVo;
import cn.gy.bean.DispatchCarDetailWebVo;
import cn.gy.bean.Member;
import cn.gy.bean.TMAccount;
import cn.gy.constant.AuditStatusEnum;
import cn.gy.core.service.AbstractService;
import cn.gy.core.web.Result;
import cn.gy.core.web.ResultGenerator;
import cn.gy.dao.TMDispatchCarDetailMapper;
import cn.gy.dao.TMMemberMapper;
import cn.gy.util.SendCompanyMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.ArrayList;
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
	@Resource
	private SendCompanyMessage sendCompanyMessage;
	public List<DispatchCarDetailWebVo> getList(String name) {
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
	public Result updateDetailWeb(DispatchCarDetailVo dispatchCarDetail) {
		if (null == dispatchCarDetail || dispatchCarDetail.getId()==0 ) {
			return ResultGenerator.genFailResult("ID不能为空");
		}
		//BeanUtils.copyProperties(accountVo, tMAccount);
		log.info("web更新：{}" + dispatchCarDetail.getId());
		DispatchCarDetail dataBaseDetail = this.findById(dispatchCarDetail.getId());
		if(!"取消".equals(dataBaseDetail.getOperation())&&
				"取消".equals(dispatchCarDetail.getOperation())
				&&AuditStatusEnum.COMPLETE.getName().equals(dispatchCarDetail.getStatus())){
			String appLicantUserId = sendCompanyMessage.getUserId(dispatchCarDetail.getTelephone());
			log.info("订单被取消，通知相关人员：{}" + dispatchCarDetail.getId());
			sendCompanyMessage.sendWeChatMsgText(appLicantUserId, "1", "", "订单已经取消:\n" +dispatchCarDetail.toString(), "0");
			sendCompanyMessage.sendWeChatMsgText(dispatchCarDetail.getDriver(), "1", "", "订单已经取消:\n" +dispatchCarDetail.toString(), "0");
			dispatchCarDetail.setStatus(AuditStatusEnum.CANCLE_WEB.getName());
		}
		this.update(dispatchCarDetail);
		return ResultGenerator.genSuccessResult("编辑成功");

	}

	public List<DispatchCarDetail> getListByAudit(String field,String name) {
		Condition condition = new Condition(DispatchCarDetail.class);
		condition.setOrderByClause("create_time desc limit 10");
		Condition.Criteria criteria = condition.createCriteria();
		criteria.andEqualTo(field,name);
		List<DispatchCarDetail> instList = tmDispatchCarDetailMapper.selectByCondition(condition);
		return instList;
	}
	public List<DispatchCarDetail> getListByNocomment(String field,String name) {
		Condition condition = new Condition(DispatchCarDetail.class);
		condition.setOrderByClause("create_time desc limit 10");
		Condition.Criteria criteria = condition.createCriteria();
		criteria.andEqualTo(field, name).andEqualTo("status", AuditStatusEnum.COMPLETE.getName())
				.andCondition("(if_comment is null or if_comment ='')");
		List<DispatchCarDetail> instList = tmDispatchCarDetailMapper.selectByCondition(condition);
		return instList;
	}
	public List<DispatchCarDetail> getListByAuditing(String field,String name) {
		Condition condition = new Condition(DispatchCarDetail.class);
		Condition.Criteria criteria = condition.createCriteria();
		List<String> list=new ArrayList<>();
		list.add(AuditStatusEnum.AUDITDING.getName());
		list.add(AuditStatusEnum.SCHEDULING.getName());
		criteria.andEqualTo(field, name).andIn("status", list);
		List<DispatchCarDetail> instList = tmDispatchCarDetailMapper.selectByCondition(condition);
		return instList;
	}
	public List<DispatchCarDetail> getListByTime(String startTime,String endTime) {
		Condition condition = new Condition(DispatchCarDetail.class);
		condition.setOrderByClause("create_time desc");
		Condition.Criteria criteria = condition.createCriteria();
		criteria.andBetween("createTime", startTime,endTime);
		List<DispatchCarDetail> instList = tmDispatchCarDetailMapper.selectByCondition(condition);
		return instList;
	}
}
