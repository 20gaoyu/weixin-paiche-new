package cn.gy.service;

import cn.gy.bean.TMAccount;
import cn.gy.bean.TMErrorLog;
import cn.gy.constant.ErrorLogType;
import cn.gy.core.service.AbstractService;
import cn.gy.dao.TMErrorLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;


/**
 * Created by gaoyu on 2019/09/09.
 */
@Slf4j
@Service
@Transactional
public class TMErrorLogService extends AbstractService<TMErrorLog> {
    @Resource
    private TMErrorLogMapper tmErrorLogMapper;

    public List<TMErrorLog> queryList(Date startTime, Date endTime,Long accountId,Long customerId)
    {
        DateTimeFormatter formatterDay = DateTimeFormatter.ofPattern("yyyyMMdd");

        Condition condition = new Condition(TMErrorLog.class);
        Condition.Criteria criteria = condition.createCriteria();
        if (null != startTime && null != endTime) {
            criteria.andBetween("errorTime",startTime, endTime);
        } else if (null != startTime) {
            criteria.andGreaterThan("errorTime", startTime);
        } else if (null != endTime) {
            criteria.andLessThanOrEqualTo("errorTime", endTime);
        }
        if(null != customerId){
            criteria.andEqualTo("customerId", customerId);
        }
        if(null != accountId)
        {
            criteria.andEqualTo("accountId", accountId);
        }
        condition.setOrderByClause("customer_id,account_id,error_time desc");
        List<TMErrorLog> errorLogsList = tmErrorLogMapper.selectByCondition(condition);
        return errorLogsList;
    }
    public void logError(TMAccount tmAccount, ErrorLogType type, String errorDesc, String errorContent) {
        try {
            Long accountId = tmAccount.getAccountId();
            String accountName = tmAccount.getAccountName();
            Long customerId = tmAccount.getCustomerId();
            String customerName = tmAccount.getCustomerName();
            Integer errorCode = type.getCode();
            LocalDateTime currentTime = LocalDateTime.now();
            DateTimeFormatter formatterDay = DateTimeFormatter.ofPattern("yyyyMMdd");
            String tadayStr = formatterDay.format(currentTime);
            TMErrorLog errorLog = new TMErrorLog();
            errorLog.setAccountId(accountId);
            errorLog.setAccountName(accountName);
            errorLog.setCustomerId(customerId);
            errorLog.setCustomerName(customerName);
            errorLog.setDayId(Integer.valueOf(tadayStr));
            errorLog.setErrorDesc(errorDesc);
            errorLog.setErrorContent(errorContent);
            errorLog.setErrorCode(errorCode);
            errorLog.setErrorTime(new Date());
            save(errorLog);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("保存异常日志失败", e);
        }
    }
    public String getStackTrace(Exception e) {
        StringBuffer sb = new StringBuffer();
        String messgae=null;
        if (e != null) {
            for (StackTraceElement element : e.getStackTrace()) {
                sb.append("\r\n\t").append(element);
            }
        }
        if(sb.length()>6000)
        {
            messgae=sb.substring(0,5999);
        }
        else
        {
            messgae=sb.toString();
        }
        return messgae.length() == 0 ? null : messgae;
    }
}
