package cn.gy.dao;



import cn.gy.bean.CustomerVo;
import cn.gy.bean.TMCustomer;
import cn.gy.core.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TMCustomerMapper extends Mapper<TMCustomer> {
	void updateStatusById(@Param("customerId") Long customerId);

	List<CustomerVo> getCustomerList(@Param("qStatus") String qStatus, @Param("qName") String qName, @Param("qSource") String qSource);

	TMCustomer getCustomerInfoByAk(@Param("apiKey") String apiKey);
}