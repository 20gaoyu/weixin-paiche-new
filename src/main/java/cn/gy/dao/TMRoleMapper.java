package cn.gy.dao;


import cn.gy.bean.RoleVo;
import cn.gy.bean.TMRole;
import cn.gy.core.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigInteger;
import java.util.List;

public interface TMRoleMapper extends Mapper<TMRole> {

	void updateStatusById(@Param("roleId") BigInteger roleId);

	List<RoleVo> getRoleList(@Param("qName") String qName, @Param("qCategory") String[] qCategory);
}