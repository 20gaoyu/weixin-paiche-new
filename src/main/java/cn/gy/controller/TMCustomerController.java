package cn.gy.controller;


import cn.gy.bean.CustomNameVo;
import cn.gy.bean.CustomerHomePageVo;
import cn.gy.bean.CustomerVo;
import cn.gy.bean.LoginPageInfoVo;
import cn.gy.bean.NewCustomer;
import cn.gy.bean.PageInfoVo;
import cn.gy.bean.TMAccount;
import cn.gy.bean.TMCustomer;
import cn.gy.constant.CustomerSourceEnum;
import cn.gy.core.web.Result;
import cn.gy.core.web.ResultGenerator;
import cn.gy.service.TMAccountService;
import cn.gy.service.TMCustomerService;
import cn.gy.util.DigestUtil;
import cn.gy.util.EnvUtil;
import cn.gy.validation.Insert;
import cn.gy.validation.Update;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
import java.util.ArrayList;
import java.util.List;

/**
* Created by JDChen on 2019/07/17.
*/
@Api(tags = "客户管理")
@RestController
@RequestMapping("/aiverify/v1/tMCustomer")
@Slf4j
public class TMCustomerController {
    @Resource
    private TMCustomerService tMCustomerService;
    
    @Resource
    private TMAccountService tMAccountService;


    @ApiOperation(value = "新增客户")
    @PostMapping
    public Result add(@Validated({Insert.class})
    				  @RequestBody NewCustomer newCustomer) {
    	Condition condition = new Condition(TMCustomer.class);
    	Condition.Criteria TMCustomerCriteria = condition.createCriteria();
    	TMCustomerCriteria.andEqualTo("customerName", newCustomer.getCustomerName());
    	List<TMCustomer> list = tMCustomerService.findByCondition(condition);
    	if(list != null && !list.isEmpty()) {
    		return ResultGenerator.genFailResult("客户名称已存在");
    	}
    	if(tMCustomerService.findBy("account", newCustomer.getAccount()) != null 
    			|| tMAccountService.findBy("accountName", newCustomer.getAccount()) != null) {
    		return ResultGenerator.genFailResult("账号已存在");
    	}
    	tMCustomerService.addCustomer(newCustomer);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "删除客户")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
    	TMCustomer deleteTMCustomer = tMCustomerService.findBy("customerId", id);
    	if( deleteTMCustomer == null) {
    		return ResultGenerator.genFailResult(
                    String.format("id为%d的客户不存在", id));
    	}
    	return tMCustomerService.deleteCustomer(id,deleteTMCustomer);
        
    }

    @ApiOperation(value = "编辑客户")
    @PutMapping
    public Result update(@Validated({Update.class})
    					 @RequestBody TMCustomer tMCustomer) {
    	TMCustomer editTMCustomer = tMCustomerService.findBy("customerId", tMCustomer.getCustomerId());
    	if(editTMCustomer.getCustomerName().equals(tMCustomer.getCustomerName())) {
    		tMCustomerService.update(tMCustomer);
    	}else {
    		Condition condition = new Condition(TMCustomer.class);
        	Condition.Criteria tMCustomerCriteria = condition.createCriteria();
        	tMCustomerCriteria.andEqualTo("customerName", tMCustomer.getCustomerName());
        	List<TMCustomer> list = tMCustomerService.findByCondition(condition);
        	if(list != null && !list.isEmpty()) {
        		return ResultGenerator.genFailResult("客户名称已存在");
        	}
			tMCustomerService.doUpdate(tMCustomer);
		}
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "分页展示客户信息")
    @ApiImplicitParams({
                @ApiImplicitParam(name = "page", value = "当前页", required = true, paramType = "query", dataType = "int", example = "1"),
                @ApiImplicitParam(name = "size", value = "单页记录条数", required = true, paramType = "query", dataType = "int", example = "10"),
                @ApiImplicitParam(name = "qStatus", value = "状态；0：无效；1：有效", paramType = "query", dataType = "string"),
                @ApiImplicitParam(name = "qName", value = "客户名称", paramType = "query", dataType = "string"),
                @ApiImplicitParam(name = "qSource", value = "客户来源", paramType = "query", dataType = "string")
        })
    @GetMapping
    public Result<PageInfoVo<CustomerVo>> list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size,
											   @RequestParam(required = false) String qStatus, @RequestParam(required = false) String qName,
											   @RequestParam(required = false) String qSource) {
        PageHelper.startPage(page, size);
        List<CustomerVo> list = tMCustomerService.getCustomerList(qStatus, qName, qSource);
        PageInfo<CustomerVo> pageInfo = new PageInfo<>(list);
        List<CustomerVo> customerVoList = pageInfo.getList();
        customerVoList.forEach(t -> {t.setSecretKey(DigestUtil.decryptDesWithPBE(t.getSecretKey()));
        t.setCustomerSource(CustomerSourceEnum.getEnumName(Integer.parseInt(t.getCustomerSource())));});
        return ResultGenerator.genSuccessResult(
                new PageInfoVo<>(customerVoList,pageInfo.getTotal()));
    }

//	@ApiOperation(value = "客户首页")
//	@GetMapping("/homePage")
//	public Result<CustomerHomePageVo> getCustomerHomePage() {
//		try{
//			return ResultGenerator.genSuccessResult(tMCustomerService.getCustomerHomePageData());
//		}catch(Exception e){
//			return ResultGenerator.genFailResult(e.getMessage());
//		}
//	}

	@ApiOperation(value = "管理员获取所有客户，非管理员获取当前客户")
	@GetMapping("/reviewCallback/getCustomer")
	public Result getCustomer(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "") String customerName)
	{
		Long accountId = EnvUtil.getEnv().getAccountId();
		TMAccount tMAccount = tMAccountService.findById(accountId);
		PageHelper.startPage(page, size);
		List<CustomNameVo> listVo=new ArrayList<>();
		//管理员获取所有客户
		if(tMAccount.getAccountType().equals(new Byte("1"))){

			List<TMCustomer> list=tMCustomerService.getCustomerNameByLike(customerName);
			for(TMCustomer tmCustomer:list)
			{
				CustomNameVo customNameVo=new CustomNameVo();
				customNameVo.setCustomerId(String.valueOf(tmCustomer.getCustomerId()));
				customNameVo.setCustomerName(tmCustomer.getCustomerName());
				listVo.add(customNameVo);
			}
			PageInfo<TMCustomer> pageInfo = new PageInfo<>(list);
			return ResultGenerator.genSuccessResult(
					new LoginPageInfoVo<>(listVo, pageInfo.getTotal(),"1"));
		}else{
		//非管理员获取当前客户
			Long customerId = tMAccount.getCustomerId();
			TMCustomer tMCustomer = tMCustomerService.findById(customerId);
			CustomNameVo customNameVo=new CustomNameVo();
			customNameVo.setCustomerId(String.valueOf(tMCustomer.getCustomerId()));
			customNameVo.setCustomerName(tMCustomer.getCustomerName());
			listVo.add(customNameVo);
			PageInfo<CustomNameVo> pageInfo = new PageInfo<>(listVo);
			return ResultGenerator.genSuccessResult(
					new LoginPageInfoVo<>(listVo, pageInfo.getTotal(), "0"));
		}
	}

}
