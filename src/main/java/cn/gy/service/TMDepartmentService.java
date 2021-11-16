package cn.gy.service;

import cn.gy.bean.Department;
import cn.gy.bean.Member;
import cn.gy.bean.TMAccount;
import cn.gy.core.service.AbstractService;
import cn.gy.core.web.Result;
import cn.gy.core.web.ResultGenerator;
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
public class TMDepartmentService extends AbstractService<Department> {
	@Resource
	private TMDepartmentMapper tmDepartmentMapper;

	public List<Department> getList(String mame) {
		return tmDepartmentMapper.getList(mame);
	}

	public Result<String> add(Department department)
	{
		String departmentName = department.getDepartmentName();
		log.info("添加：{}",departmentName);
		if(StringUtils.isBlank(departmentName)) {
			return  ResultGenerator.genFailResult("必填项为空，新增账号失败");
		}
		//检查数据库中是否已经有了同样的账号
		if(this.findBy("departmentName",departmentName)!=null)
		{
			return  ResultGenerator.genFailResult("已经存在相同的部门："+departmentName);
		}
		this.save(department);
		return ResultGenerator.genSuccessResult("add success");
	}

	public Result deleteAllbyDepartment(long id)
	{
		deleteById(id);
		return ResultGenerator.genSuccessResult(String.format("id为%d的删除成功", id));
	}


	public Result updateDepartment(Department department) {
		if (null == department || department.getId()==0 ) {
			return ResultGenerator.genFailResult("ID不能为空");
		}
		log.info("更新：" + department.getId());
		this.update(department);
		return ResultGenerator.genSuccessResult("编辑成功");
	}


	public List<Department> getAccountNameByLike(String departmentName) {
		Condition condition = new Condition(TMAccount.class);
		Condition.Criteria criteria = condition.createCriteria();
		if(StringUtils.isNotBlank(departmentName)) {
			criteria.andLike("departmentName", "%" + departmentName + "%");
		}
		List<Department> instList = tmDepartmentMapper.selectByCondition(condition);
		return instList;
	}
}
