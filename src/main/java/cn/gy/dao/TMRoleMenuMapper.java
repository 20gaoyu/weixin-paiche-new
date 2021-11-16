package cn.gy.dao;


import cn.gy.bean.HasMenuVo;
import cn.gy.bean.MenuVo;
import cn.gy.bean.TMRoleMenu;
import cn.gy.core.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigInteger;
import java.util.List;

public interface TMRoleMenuMapper extends Mapper<TMRoleMenu> {

	List<MenuVo> getAssignMenuList(@Param("roleId") BigInteger roleId);

	List<MenuVo> getUnAssignMenuList(@Param("roleId") BigInteger roleId);

	void insertBatch(@Param("idList") List<BigInteger> idList, @Param("list") List<MenuVo> list,
                     @Param("roleId") BigInteger roleId, @Param("creator") String creator);

	List<HasMenuVo> getThirdLevelMenu(@Param("roleId") BigInteger roleId);

	void deletedByRoleId(@Param("roleId") BigInteger roleId);

	void deletedByMenuId(@Param("menuId") Integer menuId);


}