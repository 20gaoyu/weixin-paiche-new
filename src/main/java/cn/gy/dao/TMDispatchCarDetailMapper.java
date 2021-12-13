package cn.gy.dao;


import cn.gy.bean.DispatchCarDetail;
import cn.gy.bean.DispatchCarDetailWebVo;
import cn.gy.bean.Member;
import cn.gy.core.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

public interface TMDispatchCarDetailMapper extends Mapper<DispatchCarDetail> {
	List<DispatchCarDetailWebVo> getList(@Param("name") String name);

    void updateRoleNameByRoleId(@Param("roleName") String roleName, @Param("roleId") BigInteger roleId);

    void updateCustomerName(@Param("customerId") Long customerId, @Param("customerName") String customerName);
    Integer getAllUserGroupByDay(@Param("endDay") LocalDate endDay);
}