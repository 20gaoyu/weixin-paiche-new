package cn.gy.dao;


import cn.gy.bean.AccountVo;
import cn.gy.bean.TMAccount;
import cn.gy.core.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

public interface TMAccountMapper extends Mapper<TMAccount> {
	List<AccountVo> getAccountList(@Param("accountId") Long accountId, @Param("customerId") Long customerId, @Param("roleName") String roleName, @Param("accountType") String accountType);

    void updateRoleNameByRoleId(@Param("roleName") String roleName, @Param("roleId") BigInteger roleId);

    void updateCustomerName(@Param("customerId") Long customerId, @Param("customerName") String customerName);
    Integer getAllUserGroupByDay(@Param("endDay") LocalDate endDay);
}