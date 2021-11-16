package cn.gy.service;

import cn.gy.bean.Car;
import cn.gy.bean.Department;
import cn.gy.bean.Member;
import cn.gy.bean.TMAccount;
import cn.gy.core.service.AbstractService;
import cn.gy.core.web.Result;
import cn.gy.core.web.ResultGenerator;
import cn.gy.dao.TMCarMapper;
import cn.gy.dao.TMDepartmentMapper;
import cn.gy.dao.TMMemberMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by gaoyu on 2019/07/18.
 */
@Slf4j
@Service
@Transactional
public class TMCarService extends AbstractService<Car> {
	@Resource
	private TMCarMapper tmCarMapper;

	public List<Car> getList(String user) {
		return tmCarMapper.getList(user);
	}

	public Result<String> add(Car car)
	{
		String license = car.getLicense();
		log.info("添加车牌是："+license);
		if(StringUtils.isBlank(license)) {
			return  ResultGenerator.genFailResult("必填项为空，新增账号失败");
		}
		//检查数据库中是否已经有了同样的账号
		if(this.findBy("license",license)!=null)
		{
			return  ResultGenerator.genFailResult("已经存在相同的车牌："+license);
		}
		//salt随机10位字符串
		//password随机8位字符串
		this.save(car);
		return ResultGenerator.genSuccessResult("add success");
	}

	public Result deleteAllbyCar(long id)
	{
		deleteById(id);
	    return ResultGenerator.genSuccessResult(String.format("id为%d的删除成功", id));
	}


	public Result updateCar(Car car) {
		if (null == car || car.getId()==0 ) {
			return ResultGenerator.genFailResult("ID不能为空");
		}
		log.info("更新：" + car.getId());
		this.update(car);
		return ResultGenerator.genSuccessResult("编辑成功");

	}

}
