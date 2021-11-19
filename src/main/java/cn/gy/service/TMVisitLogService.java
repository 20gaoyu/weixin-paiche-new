package cn.gy.service;

import cn.gy.bean.ActiveCntStatVo;
import cn.gy.bean.TMVisitLog;
import cn.gy.bean.VisitLogVo;
import cn.gy.core.service.AbstractService;
import cn.gy.dao.TMVisitLogMapper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static cn.gy.constant.SysConstant.INTERVAL_STAT_DAY;


/**
 * Created by gaoyu on 2019/08/27.
 */
@Service
@Transactional
public class TMVisitLogService extends AbstractService<TMVisitLog> {
    @Resource
    private TMVisitLogMapper tMVisitLogMapper;


    public List<Integer> getActUserWithDay(LocalDate startDay, LocalDateTime endDay) {
        DateTimeFormatter formatterDay = DateTimeFormatter.ofPattern("yyyyMMdd");
        List<Integer> listUserCnt=new ArrayList<>();
        List<ActiveCntStatVo> listActiveUsers=tMVisitLogMapper.getActUserGroupByDay(startDay,endDay);
        for(int i=1;i<=INTERVAL_STAT_DAY;i++)
        {
            boolean flag=false;
            String startDayStr=formatterDay.format(startDay.plusDays(i-1L));
            for(ActiveCntStatVo activeCntStatVo :listActiveUsers) {
                if (startDayStr.equals(activeCntStatVo.getDayId())) {
                    listUserCnt.add(activeCntStatVo.getActiveCnt());
                    flag=true;
                    break;
                }
            }
            if(!flag)
            {
                listUserCnt.add(0);
            }
        }
        return listUserCnt;
    }

    public PageInfo<VisitLogVo> getVisitLog(String qcustomerId, String qaccountId, String qurl, String startTime, String endTime) {
        Condition condition = new Condition(TMVisitLog.class);
        Condition.Criteria criteria = condition.createCriteria();
        if(StringUtils.isNotBlank(qcustomerId)){
            criteria.andEqualTo("customerId",qcustomerId);
        }
        if(StringUtils.isNotBlank(qaccountId)){
            criteria.andEqualTo("accountId",qaccountId);
        }
        if(StringUtils.isNotBlank(qurl)){
            criteria.andLike("visitUrl", "%" + qurl + "%");
        }
        criteria.andBetween("visitTime",startTime,endTime);
        condition.orderBy("visitTime").desc();
        List<TMVisitLog> tmVisitLogs = this.findByCondition(condition);
        PageInfo<TMVisitLog> origPageInfo = new PageInfo<>(tmVisitLogs);

        List<VisitLogVo> visitLogVos = tmVisitLogs.stream().map(t -> {
            VisitLogVo visitLogVo = new VisitLogVo();
            visitLogVo.setAccountName(t.getAccountName());
            visitLogVo.setCustomerName(t.getCustomerName());
            visitLogVo.setVisitDate(t.getVisitTime());
            visitLogVo.setVisitIp(t.getVisitorIp());
            visitLogVo.setVisitUrl(t.getVisitUrl());
            visitLogVo.setContent(t.getRequestContent());
            return visitLogVo;
        }).collect(Collectors.toList());
        PageInfo<VisitLogVo> pageInfo = new PageInfo<>(visitLogVos);
        BeanUtils.copyProperties(origPageInfo,pageInfo, "list");

        return pageInfo;

    }
}
