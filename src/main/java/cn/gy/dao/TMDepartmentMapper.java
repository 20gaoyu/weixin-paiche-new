package cn.gy.dao;


import cn.gy.bean.Department;
import cn.gy.bean.DepartmentWebVo;
import cn.gy.core.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TMDepartmentMapper extends Mapper<Department> {
	List<Department> getList(@Param("name") String name);

}