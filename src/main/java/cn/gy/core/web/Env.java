package cn.gy.core.web;

import cn.gy.bean.LoginVo;
import cn.gy.constant.SysConstant;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Data
@NoArgsConstructor
public class Env {
    private HttpServletRequest request;

    private HttpServletResponse response;

    public Env(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public Long getAccountId()
    {
        return (Long)request.getSession().getAttribute(SysConstant.SESSION_USER_ACCOUNT_ID);
    }
    public void setAccountId(Long accountId)
    {
        request.getSession().setAttribute(SysConstant.SESSION_USER_ACCOUNT_ID,accountId);
    }

    public Long getCustomerId()
    {
        return (Long)request.getSession().getAttribute(SysConstant.SESSION_USER_CUSTOMER_ID);
    }
    public void setCustomerId(Long customerId)
    {
        request.getSession().setAttribute(SysConstant.SESSION_USER_CUSTOMER_ID,customerId);
    }
    public String getCustomerName()
    {
        return (String)request.getSession().getAttribute(SysConstant.SESSION_USER_CUSTOMER_NAME);
    }
    public void setCustomerName(String customerName)
    {
        request.getSession().setAttribute(SysConstant.SESSION_USER_CUSTOMER_NAME,customerName);
    }
    public String getApiKey()
    {
        return (String) request.getSession().getAttribute(SysConstant.SESSION_USER_API_KEY);
    }
    public void setApiKey(String apiKey)
    {
        request.getSession().setAttribute(SysConstant.SESSION_USER_API_KEY,apiKey);
    }


    public String getAccount()
    {
        return (String) request.getSession().getAttribute(SysConstant.SESSION_USER_ACCOUNT);
    }
    public void setAccount(String account)
    {
         request.getSession().setAttribute(SysConstant.SESSION_USER_ACCOUNT,account);
    }

    public LoginVo getLoginVo()
    {
        return (LoginVo) request.getSession().getAttribute(SysConstant.SESSION_LOGIN_VO);
    }
    public void setLoginVo(LoginVo loginVo)
    {
        request.getSession().setAttribute(SysConstant.SESSION_LOGIN_VO,loginVo);
    }

    public String getReset()
    {
        return (String) request.getSession().getAttribute(SysConstant.SESSION_USER_RESET);
    }
    public void setReset(String reset)
    {
        request.getSession().setAttribute(SysConstant.SESSION_USER_RESET,reset);
    }
    public void setTimeout(Integer time)
    {
        request.getSession().setMaxInactiveInterval(time);
    }
    public void removeAccount()
    {
        request.getSession().removeAttribute(SysConstant.SESSION_USER_ACCOUNT);
        request.getSession().removeAttribute(SysConstant.SESSION_USER_RESET);
        request.getSession().invalidate();
    }
}
