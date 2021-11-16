package cn.gy.dao;


import cn.gy.bean.Car;
import cn.gy.core.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TMCarMapper extends Mapper<Car> {
	List<Car> getList(@Param("license") String license);

}