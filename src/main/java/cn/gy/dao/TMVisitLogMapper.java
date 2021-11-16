package cn.gy.dao;

import cn.gy.bean.ActiveCntStatVo;
import cn.gy.bean.TMVisitLog;
import cn.gy.core.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TMVisitLogMapper extends Mapper<TMVisitLog> {
    List<ActiveCntStatVo> getActUserGroupByDay(@Param("startDay") LocalDate startDay, @Param("endDay") LocalDateTime endDay);
}