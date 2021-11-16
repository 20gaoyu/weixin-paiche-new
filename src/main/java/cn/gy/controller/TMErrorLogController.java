package cn.gy.controller;


import cn.gy.bean.AccountNameVo;
import cn.gy.bean.ErrorLogQueryCondition;
import cn.gy.bean.ErrorLogVo;
import cn.gy.bean.PageInfoVo;
import cn.gy.bean.TMAccount;
import cn.gy.bean.TMErrorLog;
import cn.gy.core.web.Result;
import cn.gy.core.web.ResultGenerator;
import cn.gy.service.TMAccountService;
import cn.gy.service.TMErrorLogService;
import cn.gy.util.DomainUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "错误日志")
@RestController
@Slf4j
@RequestMapping("/aiverify/v1/errorLog")
public class TMErrorLogController {

    @Resource
    private TMErrorLogService tmErrorLogService;
    @Resource
    private TMAccountService tmAccountService;

    @ApiOperation(value = "分页展示错误日志信息")
    @PostMapping("/dec")
    public Result<PageInfoVo<ErrorLogVo>> list(@RequestBody ErrorLogQueryCondition errorLogQueryCondition
    )
    {
        PageHelper.startPage(errorLogQueryCondition.getPage(), errorLogQueryCondition.getSize());
        List<TMErrorLog> list = tmErrorLogService.queryList(errorLogQueryCondition.getStartTime(),errorLogQueryCondition.getEndTime(),errorLogQueryCondition.getAccountId(),errorLogQueryCondition.getCustomerId());
        PageInfo<TMErrorLog> pageInfo = new PageInfo<>(list);
        List<ErrorLogVo> voList =pageInfo.getList().stream().map(tmErrorLog -> {
            ErrorLogVo errorLogVo= DomainUtil.transferTo(tmErrorLog,ErrorLogVo.class);
            return  errorLogVo;
        }).collect(Collectors.toList());
        return ResultGenerator.genSuccessResult(
                new PageInfoVo<>(voList, pageInfo.getTotal()));
    }
    @ApiOperation(value = "日志详情")
    @GetMapping("/detail")
    public Result detail(@RequestParam(required = true) Long id){
        JSONObject result = new JSONObject();
        result.put("errorContent", tmErrorLogService.findById(id).getErrorContent());
        return ResultGenerator.genSuccessResult(result) ;
    }
    @ApiOperation(value = "获取账号名称")
    @GetMapping("/getAccountName")
    public Result getAccountName(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size,@RequestParam(required = false) String accountName,@RequestParam(required = false) String customerName){
        PageHelper.startPage(page, size);
        List<TMAccount> list=tmAccountService.getAccountNameByLike(accountName,customerName);
        PageInfo<TMAccount> pageInfo = new PageInfo<>(list);
        List<AccountNameVo> listVo=new ArrayList<>();
        //log.info("list"+list.size()+"page"+pageInfo.getList().size());
        for(TMAccount tmAccount:pageInfo.getList())
        {
            AccountNameVo accountNameVo=new AccountNameVo();
            accountNameVo.setAccountId(String.valueOf(tmAccount.getAccountId()));
            accountNameVo.setAccountName(tmAccount.getAccountName());
            listVo.add(accountNameVo);
        }
        return ResultGenerator.genSuccessResult(
                new PageInfoVo<>(listVo, pageInfo.getTotal()));
    }
}
