package cn.gy.dao;

import cn.gy.bean.TMMenu;
import cn.gy.core.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigInteger;
import java.util.List;

public interface TMMenuMapper extends Mapper<TMMenu> {
    List<TMMenu> findMenuByRole(@Param("roleId") BigInteger roleId);
}