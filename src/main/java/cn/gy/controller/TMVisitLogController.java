package cn.gy.controller;

import cn.gy.bean.LoginVo;
import cn.gy.bean.PageInfoVo;
import cn.gy.bean.TMVisitLog;
import cn.gy.bean.VisitLogVo;
import cn.gy.core.web.Result;
import cn.gy.core.web.ResultGenerator;
import cn.gy.service.TMVisitLogService;
import cn.gy.util.EnvUtil;
import cn.gy.util.IPUtil;
import cn.gy.util.InterceptParmUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by JDChen on 2019/09/09
 */
@Api(tags = "用户访问日志")
@RestController
@RequestMapping("/aiverify/v1/visitLog")
@Slf4j
public class TMVisitLogController {
    @Resource
	private TMVisitLogService tMVisitLogService;

    private static final DateTimeFormatter formatterDay = DateTimeFormatter.ofPattern("yyyyMMdd");

    @ApiOperation(value = "记录用户访问日志")
    @PostMapping
    public Result saveVisitLog(HttpServletRequest request) {
        try {
            LoginVo loginVo = EnvUtil.getEnv().getLoginVo();
            String url = request.getRequestURI();
            String queryurl = request.getQueryString();
            if(null!=queryurl){
                url += "?"+queryurl;
            }
            LocalDateTime localDateTime = LocalDateTime.now();
            Date date = Date.from(localDateTime.toInstant(ZoneOffset.of("+8")));

            TMVisitLog tmVisitLog = new TMVisitLog();
            tmVisitLog.setAccountId(Long.parseLong(loginVo.getAccountId()));
            tmVisitLog.setAccountName(loginVo.getAccountName());
            tmVisitLog.setCustomerId(Long.parseLong(loginVo.getCustomerId()));
            tmVisitLog.setCustomerName(loginVo.getCustomerName());
            tmVisitLog.setVisitorIp(IPUtil.getIpAddress(request));
            tmVisitLog.setVisitUrl(url);
            tmVisitLog.setRequestContent(InterceptParmUtil.getBodyParms(request));
            tmVisitLog.setCreateTime(date);
            tmVisitLog.setVisitTime(date);
            tmVisitLog.setDayId(Integer.parseInt(formatterDay.format(localDateTime)));
            tMVisitLogService.save(tmVisitLog);
            return ResultGenerator.genSuccessResult();
        }catch (Exception e){
            return ResultGenerator.genFailResult(e.getMessage());
        }

    }

    @ApiOperation(value = "分页展示用户访问日志")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页", required = true, paramType = "query", dataType = "int", example = "1"),
            @ApiImplicitParam(name = "size", value = "单页记录条数", required = true, paramType = "query", dataType = "int", example = "10"),
            @ApiImplicitParam(name = "qcustomerId", value = "客户id", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "qaccountId", value = "账号id", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "qurl", value = "模糊查询访问地址", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "qstartTime", value = "查询起始时间，格式是：yyyy-MM-dd HH:mm:ss", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "qendTime", value = "查询结束时间，格式是：yyyy-MM-dd HH:mm:ss", required = true, paramType = "query", dataType = "string")
    })
    @GetMapping("/list")
    public Result<PageInfoVo<VisitLogVo>> list(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size,
                                               @RequestParam(required = false) String qcustomerId, @RequestParam(required = false) String qaccountId,
                                               @RequestParam(required = false) String qurl, @RequestParam String qstartTime,
                                               @RequestParam String qendTime) {

        try {
            if(StringUtils.isBlank(qstartTime) || StringUtils.isBlank(qendTime)){
                return ResultGenerator.genFailResult("请输入起始时间");
            }
            PageHelper.startPage(page, size);
            PageInfo<VisitLogVo> pageInfo = tMVisitLogService.getVisitLog(qcustomerId,qaccountId,qurl,qstartTime,qendTime);
            return ResultGenerator.genSuccessResult(
                    new PageInfoVo<>(pageInfo.getList(),pageInfo.getTotal()));

        }catch (Exception e){
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }

}
