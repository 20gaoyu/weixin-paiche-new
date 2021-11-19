package cn.gy.controller;


import cn.gy.bean.*;
import cn.gy.constant.PositionEnum;
import cn.gy.constant.AuditStatusEnum;

import cn.gy.core.web.Result;
import cn.gy.core.web.ResultCode;
import cn.gy.core.web.ResultGenerator;
import cn.gy.service.TMAccountService;
import cn.gy.service.TMCustomerService;
import cn.gy.service.TMDispatchCarDetailService;
import cn.gy.service.TMMemberService;
import cn.gy.service.TMMenuService;
import cn.gy.util.DigestUtil;
import cn.gy.util.EnvUtil;
import cn.gy.util.MiniprogramUtil;
import cn.gy.util.SendCompanyMessage;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
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
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by gaoyu on 2019/07/18.
 */

@Api(tags = "账号管理")
@RestController
@RequestMapping("/aiverify/v1")
@Slf4j
public class TMAccountController {
    @Resource
    private TMAccountService tMAccountService;
    @Resource
    private TMCustomerService tMCustomerService;
    @Resource
    private TMMenuService tMMenuService;
    @Resource
    private TMMemberService tmMemberService;
    @Resource
    private TMDispatchCarDetailService tmDispatchCarDetailService;

    @ApiOperation(value = "登录")
    //@AlwaysAllowAccess
    @GetMapping("/login")
    public Result login(@RequestParam(defaultValue = "") String account, @RequestParam(defaultValue = "") String passwd) {
        TMAccount tmAccount = tMAccountService.judgeAccount(account, passwd);
        if (null != tmAccount) {
            tmAccount.setLastLoginTime(new Date());
            tMAccountService.update(tmAccount);
            log.info("账号登录：" + account);
            TMCustomer tMCustomer = tMCustomerService.findById(tmAccount.getCustomerId());
            //添加登录信息获取
            if (null == tMCustomer) {
                return ResultGenerator.genFailResult("登录失败，账号没有归属的客户");
            }
            log.info("sessionID ：" +  EnvUtil.getEnv().getRequest().getSession().getId());
            EnvUtil.getEnv().setAccountId(tmAccount.getAccountId());
            EnvUtil.getEnv().setCustomerId(tmAccount.getCustomerId());
            EnvUtil.getEnv().setCustomerName(tmAccount.getCustomerName());
            EnvUtil.getEnv().setApiKey(tMCustomer.getApiKey());
            EnvUtil.getEnv().setAccount(account);
            EnvUtil.getEnv().setTimeout(600);
            EnvUtil.getEnv().setReset(String.valueOf(tmAccount.getIfResetPasswd()));
            LoginVo loginVo = new LoginVo();
            loginVo.setAccountName(account);
            loginVo.setCustomerId(String.valueOf(tmAccount.getCustomerId()));
            loginVo.setApiKey(tMCustomer.getApiKey());
            loginVo.setAccountId(String.valueOf(tmAccount.getAccountId()));
            loginVo.setCustomerName(tmAccount.getCustomerName());
            EnvUtil.getEnv().setLoginVo(loginVo);

            if (0 == tmAccount.getIfResetPasswd()) {
                LoginUrlVo loginUrlVo =new LoginUrlVo();
                loginUrlVo.setIf_reset_passwd(false);
                return ResultGenerator.genSuccessResult(loginUrlVo);
            }
            //request.getSession().setAttribute("account", account);//将用户信息放到session中
            //request.getSession().setAttribute("IfResetPasswd", tmAccount.getIfResetPasswd());//将用户信息放到session中
            //request.getSession().setMaxInactiveInterval(600);//设置session存储时间，以秒为单位，3600=60*60即为60分钟
            else
            {
                List<MenuTree> menus = tMMenuService.getDisplayMenuByRole();
                if(menus==null||menus.size()==0||null==menus.get(0))
                {
                    return ResultGenerator.genFailResult("登录失败，该账号角色没有分配任何菜单");
                }
                else {
                    LoginUrlVo loginUrlVo =new LoginUrlVo();
                    loginUrlVo.setIf_reset_passwd(true);
                    loginUrlVo.setUrl(menus.get(0).getUrl());
                    return ResultGenerator.genSuccessResult(loginUrlVo);
                }
            }
        } else {
            return ResultGenerator.genFailResult("登录失败，账号或密码错误");
        }
    }
    @PostMapping("/wxlogin")
    public Result wxlogin(@RequestBody WeixinVo weixinVo) {
            log.info("小程序登录后获取用户在小程序里的openid和unionId-----开始:{}",weixinVo);
            // 获取前端传递的数据
            //AppID，AppSecret和传递过来的code,调用 code2Session 接口获取openId,unionId和session_key
            MiniprogramResult miniprogramResult = MiniprogramUtil.getOpenId(weixinVo.getCode());
            if (miniprogramResult != null) {
                // 获取用户的唯一标识openid
                String openid = miniprogramResult.getOpenid();
                // 获取用户的唯一标识unionid
                String unionid = miniprogramResult.getUnionid();
                // 获取会话秘钥
                String session_key = miniprogramResult.getSession_key();
                // 下面就可以写自己的业务代码了
                Member member = tmMemberService.findByOenId(openid);
                if(member==null) {
                    if(weixinVo==null||weixinVo.getTelephone()==null||"".equals(weixinVo.getTelephone())){
                        return ResultGenerator.genFailResult(ResultCode.UNAUTHORIZED,"填写号码注册");
                    }
                    member = tmMemberService.findByTele(weixinVo.getTelephone());
                    if(member==null){
                        return ResultGenerator.genFailResult("没有对应信息，请通知管理员填加");
                    }else{
                        member.setOpenId(openid);
                        tmMemberService.update(member);
                    }
                    return ResultGenerator.genSuccessResult(member);
                }else{
                    return ResultGenerator.genSuccessResult(member);
                }

            }else{
                return ResultGenerator.genFailResult("获取小程序唯一id失败");
            }
        // 第一步：用户同意授权，获取code
//        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appid +
//                "&redirect_uri=" + http +
//                "&response_type=code" +
//                "&scope=snsapi_userinfo" +
//                "&state=STATE#wechat_redirect";
//        return "redirect:"  + url;
    }
    @PostMapping("/wxquery")
    public Result wxquery(@RequestBody Member member) {
        log.info("小程序登录后获取自己详单记录:{}",member);
        if (member != null) {
            // 获取用户的唯一标识openid
            String openid = member.getOpenId();
            // 获取用户的唯一标识unionid
            // 下面就可以写自己的业务代码了
            if(openid==null||"".equals(openid)){
                    return ResultGenerator.genFailResult(ResultCode.UNAUTHORIZED,"填写号码注册");
            }else{
                List<DispatchCarDetail> list=new ArrayList<>();
                if(PositionEnum.WORKER.getName().equals(member.getPosition())){
                     list = tmDispatchCarDetailService.getListByAudit("applicant",member.getAccountName());
                }else if(PositionEnum.AUDITD_IRECTOR.getName().equals(member.getPosition())){
                     list = tmDispatchCarDetailService.getListByAudit("oneAudit",member.getAccountName());
                }else if(PositionEnum.AUDITD_SCHEDULING.getName().equals(member.getPosition())){
                    list = tmDispatchCarDetailService.getListByAudit("twoAudit",member.getAccountName());
                }else{

                }
                return ResultGenerator.genSuccessResult(list);
              }
        }else{
            return ResultGenerator.genFailResult(ResultCode.UNAUTHORIZED,"填写号码注册");
        }
    }

    @PostMapping("/wxsubmit")
    public Result wxquery(@RequestBody DispatchCarDetailVo dispatchCarDetail) {
        log.info("小程序登录后提交申请记录:{}",dispatchCarDetail);

        if (dispatchCarDetail != null) {
            // 下面就可以写自己的业务代码了
            Member member = tmMemberService.getMember(dispatchCarDetail.getDepartment(), PositionEnum.AUDITD_IRECTOR.getName());
            if(member==null){
                return ResultGenerator.genFailResult(ResultCode.UNAUTHORIZED,"填写号码注册");
            }else{
                dispatchCarDetail.setStatus(AuditStatusEnum.AUDITDING.getName());
                dispatchCarDetail.setOneAudit(member.getAccountName());
                tmDispatchCarDetailService.add(dispatchCarDetail);
                SendCompanyMessage weChat = new SendCompanyMessage();
                weChat.sendWeChatMsgText("", "2", "", "微信测试"+dispatchCarDetail.getOneAudit(), "0");
                return ResultGenerator.genSuccessResult("提交成功");
            }
        }else{
            return ResultGenerator.genFailResult(ResultCode.UNAUTHORIZED,"填写号码注册");
        }
    }
    @PostMapping("/wxaudit")
    public Result wxaudit(@RequestBody DispatchCarDetailVo dispatchCarDetail) {
        log.info("小程序登录后审批记录:{}",dispatchCarDetail);
        SendCompanyMessage weChat = new SendCompanyMessage();
        if (dispatchCarDetail != null) {
            // 下面就可以写自己的业务代码了
            if(dispatchCarDetail.getIfComment()!=null||!"".equals(dispatchCarDetail.getIfComment())){
                dispatchCarDetail.setStatus(AuditStatusEnum.CANCLE.getName());
                tmDispatchCarDetailService.updateDetail(dispatchCarDetail);
                weChat.sendWeChatMsgText("", "2", "", "微信测试"+"驳回", "0");
                return ResultGenerator.genSuccessResult("审批成功");
            }
            Member member = tmMemberService.getMember(dispatchCarDetail.getDepartment(), PositionEnum.AUDITD_SCHEDULING.getName());
            if(member==null){
                return ResultGenerator.genFailResult(ResultCode.UNAUTHORIZED,"没有对应调度");
            }else{
                if(dispatchCarDetail.getTwoAudit()==null){
                    dispatchCarDetail.setTwoAudit(member.getAccountName());
                    dispatchCarDetail.setStatus(AuditStatusEnum.SCHEDULING.getName());
                    tmDispatchCarDetailService.updateDetail(dispatchCarDetail);
                    weChat.sendWeChatMsgText("", "2", "", "微信测试"+dispatchCarDetail.getTwoAudit(), "0");
                    return ResultGenerator.genSuccessResult("审批成功");
                }else{
                    member = tmMemberService.getMember(dispatchCarDetail.getDepartment(), PositionEnum.DRIVER.getName());
                    if(member==null){
                        return ResultGenerator.genFailResult(ResultCode.UNAUTHORIZED,"没有驾驶员");
                    }else{
                        dispatchCarDetail.setStatus(AuditStatusEnum.COMPLETE.getName());
                        tmDispatchCarDetailService.updateDetail(dispatchCarDetail);
                        weChat.sendWeChatMsgText("", "2", "", "微信测试"+dispatchCarDetail.getTwoAudit(), "0");
                        return ResultGenerator.genSuccessResult("审批成功");
                    }
                }

            }
        }else{
            return ResultGenerator.genFailResult(ResultCode.UNAUTHORIZED,"填写号码注册");
        }
    }
    @ApiOperation(value = "退出")
    //@AlwaysAllowAccess
    @GetMapping(value = "/logout")
    public Result logout() {
        EnvUtil.getEnv().removeAccount();
//        if (session != null) {
//            String account = (String)session.getAttribute("account");//从当前session中获取用户信息
//            log.info("session中账号："+account+"退出");
//            session.removeAttribute("account");
//            session.invalidate();//关闭session
//        }
        return ResultGenerator.genSuccessResult("成功退出");
    }

    @ApiOperation(value = "新增账号")
    @PostMapping("/tMAccount/newAccount")
    public Result add(@RequestBody NewAccount newAccount) {

        return tMAccountService.addAccount(newAccount);
    }

    @ApiOperation(value = "删除账号")
    @DeleteMapping("/tMAccount/delaccount/{id}")
    public Result delete(@PathVariable Long id) {
        TMAccount tMAccount = tMAccountService.findBy("accountId", id);
        if (tMAccount == null) {
            return ResultGenerator.genFailResult(
                    String.format("id为%d的客户不存在", id));
        } else if (1 == tMAccount.getAccountType()) {
            return ResultGenerator.genFailResult(
                    String.format("id为%d为系统用户不能删除", id));
        } else {
            tMAccount.setAccountType(new Byte("3"));
            tMAccountService.update(tMAccount);
            return ResultGenerator.genSuccessResult(String.format("id为%d删除成功", id));
        }
    }

    @ApiOperation(value = "获取当前账号")
    @GetMapping("/tMAccount/getCurAccount")
    public Result getCurAccount() {
        JSONObject result = new JSONObject();
        result.put("accountName", EnvUtil.getEnv().getAccount());
        result.put("accountId", EnvUtil.getEnv().getAccountId());
        TMAccount account = tMAccountService.findById(EnvUtil.getEnv().getAccountId());
        result.put("completedGuide", account.getCompletedGuide());
        return ResultGenerator.genSuccessResult(result);
    }

    @ApiOperation(value = "重置密码")
    @GetMapping("/tMAccount/{id}/reset")
    public Result resetPasswd(@PathVariable Long id) {
        TMAccount tMAccount = tMAccountService.findBy("accountId", id);
        if (tMAccount == null) {
            return ResultGenerator.genFailResult(
                    String.format("id为%d的客户不存在", id));
        } else if (1 == tMAccount.getAccountType()) {
            return ResultGenerator.genFailResult(
                    String.format("id为%d为系统用户不能修改", id));
        } else {
            String salt = RandomStringUtils.randomAlphanumeric(10);
            String password = RandomStringUtils.randomAlphanumeric(8);
            tMAccount.setSalt(salt);
            tMAccount.setPassword(DigestUtils.md5Hex(DigestUtils.md5Hex(password) + salt));
            AccountPasswordVo accountPasswordVo = new AccountPasswordVo();
            accountPasswordVo.setAccountName(tMAccount.getAccountName());
            accountPasswordVo.setPassword(password);
            //httpsession.setAttribute("IfResetPasswd",1);
            tMAccountService.update(tMAccount);
            return ResultGenerator.genSuccessResult(accountPasswordVo);
        }
    }

    @ApiOperation(value = "修改密码")
    @GetMapping("/tMAccount/modify")
    public Result modifyPasswd(@RequestParam(defaultValue = "") String oldpasswd, @RequestParam(defaultValue = "") String newpasswd) {
        String account = EnvUtil.getEnv().getAccount();
        TMAccount tmAccount = tMAccountService.judgeAccount(account, oldpasswd);
        log.info("密码修改为：" + newpasswd);
        if (account == null) {
            return ResultGenerator.genFailResult(
                    String.format("账号没有登录不能修改密码"));
        } else if (tmAccount == null) {
            return ResultGenerator.genFailResult(
                    String.format("原密码错误"));
        } else if (1 == tmAccount.getAccountType()) {
            return ResultGenerator.genFailResult(
                    String.format("账号为%s为系统用户不能修改", account));
        } else {
            String salt = RandomStringUtils.randomAlphanumeric(10);
            tmAccount.setIfResetPasswd(1);
            tmAccount.setSalt(salt);
            tmAccount.setPassword(DigestUtils.md5Hex(DigestUtils.md5Hex(newpasswd) + salt));
            tMAccountService.update(tmAccount);
            EnvUtil.getEnv().setReset("1");
            return ResultGenerator.genSuccessResult("密码修改成功");
        }
    }

    @ApiOperation(value = "编辑角色")
    @PutMapping("/tMAccount/editAccount")
    public Result update(@RequestBody AccountVo accountVo) {
        if (null == accountVo || null == accountVo.getAccountId()) {
            return ResultGenerator.genFailResult("账号ID不能为空");
        }
        TMAccount tmAccount = new TMAccount();
        tmAccount.setAccountId(Long.valueOf(accountVo.getAccountId()));
        tmAccount.setRoleId(new BigInteger(accountVo.getRoleId()));
        tmAccount.setRoleName(accountVo.getRoleName());
        //BeanUtils.copyProperties(accountVo, tMAccount);
        log.info("更新：" + accountVo.getAccountId() + ":" + accountVo.getRoleName() + ":" + accountVo.getRoleId());
        tMAccountService.update(tmAccount);
        return ResultGenerator.genSuccessResult("编辑成功");
    }

    @ApiOperation(value = "获取账号的个人信息")
    @GetMapping("/tMAccount/getCustomer")
    public Result getCustomer(@RequestParam(defaultValue = "") String accountName) {
        if (StringUtils.isBlank(accountName)) {
            return ResultGenerator.genFailResult("账号名称不能为空");
        }
        TMAccount tmAccount = tMAccountService.getAccount(accountName);
        if (null == tmAccount) {
            return ResultGenerator.genFailResult("账号已经被删除或不存在");
        }
        TMCustomer tMCustomer = tMCustomerService.findById(tmAccount.getCustomerId());
        tMCustomer.setSecretKey(DigestUtil.decryptDesWithPBE(tMCustomer.getSecretKey()));
        return ResultGenerator.genSuccessResult(tMCustomer);
    }

    @ApiOperation(value = "分页展示客户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页", required = true, paramType = "query", dataType = "int", example = "1"),
            @ApiImplicitParam(name = "size", value = "单页记录条数", required = true, paramType = "query", dataType = "int", example = "10"),
            @ApiImplicitParam(name = "accountId", value = "账号ID", paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "customerId", value = "客户ID", paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "roleName", value = "角色名称", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "accountType", value = "账号类型", paramType = "query", dataType = "string")
    })
    @GetMapping("/tMAccount/queryaccount")
    public Result<PageInfoVo<AccountVo>> list(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size,
                                              @RequestParam(required = false) Long accountId, @RequestParam(required = false) Long customerId,
                                              @RequestParam(required = false) String roleName, @RequestParam(required = false) String accountType) {
        PageHelper.startPage(page, size);
        List<AccountVo> list = tMAccountService.getAccountList(accountId, customerId, roleName, accountType);
        PageInfo<AccountVo> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genSuccessResult(
                new PageInfoVo<>(pageInfo.getList(), pageInfo.getTotal()));
    }

    @ApiOperation(value = "记录已完成的用户指引")
    @PutMapping("/tMAccount/guide")
    public Result saveCompletedGuide(String guides) {
        TMAccount storeAccount = new TMAccount();
        storeAccount.setAccountId(EnvUtil.getEnv().getAccountId());
        storeAccount.setCompletedGuide(guides);
        tMAccountService.update(storeAccount);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "模糊搜索账户")
    @GetMapping("/tMAccount/getAccount")
    public Result<PageInfoVo<AccountNameVo>> getCustomer(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size,
                                                         @RequestParam(defaultValue = "") String accountName){
        PageHelper.startPage(page, size);
        Condition condition = new Condition(TMAccount.class);
        Condition.Criteria criteria = condition.createCriteria();
        criteria.andLike("accountName", "%" + accountName + "%");
        condition.orderBy("accountName");
        List<TMAccount> tmAccountList = tMAccountService.findByCondition(condition);
        PageInfo<TMAccount> pageInfo = new PageInfo<>(tmAccountList);

        List<TMAccount> list = pageInfo.getList();
        List<AccountNameVo> accountNameVos = list.stream().map(t -> {
            AccountNameVo accountNameVo = new AccountNameVo();
            accountNameVo.setAccountId(String.valueOf(t.getAccountId()));
            accountNameVo.setAccountName(t.getAccountName());
            return accountNameVo;
        }).collect(Collectors.toList());

        return ResultGenerator.genSuccessResult(
                new PageInfoVo<>(accountNameVos, pageInfo.getTotal()));

    }
}
