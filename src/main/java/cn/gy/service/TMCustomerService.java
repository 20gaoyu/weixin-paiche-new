package cn.gy.service;


import cn.gy.bean.CustomNameVo;
import cn.gy.bean.CustomerVo;
import cn.gy.bean.NewCustAuditEvent;
import cn.gy.bean.NewCustomer;
import cn.gy.bean.TMCustomer;
import cn.gy.constant.CustomerStatusEnum;
import cn.gy.constant.SysConstant;
import cn.gy.core.service.AbstractService;
import cn.gy.core.web.Result;
import cn.gy.core.web.ResultGenerator;
import cn.gy.dao.TMCustomerMapper;
import cn.gy.util.DigestUtil;
import cn.gy.util.Sequence;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Created by JDChen on 2019/07/17.
 */
@Service
@Transactional
@Slf4j
public class TMCustomerService extends AbstractService<TMCustomer> {
	public static final String CACHE_KEY="Customer-Cache";
    @Resource
    private TMCustomerMapper tMCustomerMapper;

	@Resource
	private TMAccountService tMAccountService;


	@Autowired
	private ApplicationContext applicationContext;

	@Cacheable(cacheNames = CACHE_KEY)
	public String findCustomerName(Long customerId){
		 return findById(customerId).getCustomerName();
	}

    public TMCustomer getCustomerByApiKey(String apiKey){
    	TMCustomer searchCustomer = new TMCustomer();
    	searchCustomer.setApiKey(apiKey);
		return Optional.ofNullable(find(searchCustomer)).orElse(Lists.newArrayList()).get(0);
	}

	public void updateStatusById(Long id) {
		tMCustomerMapper.updateStatusById(id);
	}

	public List<CustomerVo> getCustomerList(String qStatus, String qName, String qSource) {
		return tMCustomerMapper.getCustomerList(qStatus,qName,qSource);
	}

	public void addCustomer(NewCustomer newCustomer) {
		Long customerId = Long.parseLong(Sequence.nextVal());
    	Long appId = Long.valueOf(Sequence.nextVal(10));
    	String apiKey = Sequence.randomNum();
    	String secretKey = Sequence.randomNum();
    	String encryptSecretKey = DigestUtil.encryptDesWithPBE(secretKey);
    	TMCustomer tMCustomer = new TMCustomer();
    	BeanUtils.copyProperties(newCustomer, tMCustomer);
    	tMCustomer.setCustomerId(customerId);
    	tMCustomer.setAppId(appId);
    	tMCustomer.setApiKey(apiKey);
    	tMCustomer.setSecretKey(encryptSecretKey);

        this.save(tMCustomer);
		//发布NewCustAuditEvent事件
		applicationContext.publishEvent(new NewCustAuditEvent(this,tMCustomer));
		tMCustomer.setStatus(CustomerStatusEnum.VALID.getType().byteValue());
	}

	public Result deleteCustomer(Long id, TMCustomer deleteTMCustomer) {

    	try {
			deleteTMCustomer.setStatus(CustomerStatusEnum.INVALID.getType().byteValue());
			this.updateStatusById(id);
		} catch (Exception e) {
			log.error("{}", ExceptionUtils.getStackTrace(e));
			return ResultGenerator.genFailResult("删除客户失败，请稍候再试");
		}
    	return ResultGenerator.genSuccessResult();
	}

	@CachePut(cacheNames = CACHE_KEY)
	public void doUpdate(TMCustomer tMCustomer) {
		this.update(tMCustomer);
		tMAccountService.updateCustomerName(tMCustomer.getCustomerId(),tMCustomer.getCustomerName());
	}

	public List<TMCustomer> getCustomerNameByLike(String customerName) {
		Condition condition = new Condition(TMCustomer.class);
		List<CustomNameVo> list=new ArrayList<>();
		condition.createCriteria().andLike("customerName","%"+customerName+"%").andEqualTo("status",1);
		List<TMCustomer> instList = tMCustomerMapper.selectByCondition(condition);
		return instList;
	}


}
