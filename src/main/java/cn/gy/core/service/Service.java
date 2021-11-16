package cn.gy.core.service;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.math.BigInteger;
import java.util.List;

/**
 * Service 层 基础接口，其他Service 接口 请继承该接口
 */
public interface Service<T> {

    int save(T model);// 持久化

    void save(List<T> models);// 批量持久化

    void deleteById(Integer id);// 通过主鍵刪除

    void deleteById(Long id);// 通过主鍵刪除

    void deleteByIds(String ids);// 批量刪除 eg：ids -> “1,2,3,4”

    void update(T model);// 更新

    List<T> find(T record);// 根据对象属性查找，属性请用包装类型

    T findOne(T record);// 根据对象属性查找，超过一个返回会报错，属性请用包装类型

    T findById(Integer id);// 通过ID查找

    T findById(Long id);// 通过ID查找

    T findById(BigInteger id);// 通过ID查找
    T findBy(String fieldName, Object value);// 通过Model中某个成员变量名称（非数据表中column的名称）查找,value需符合unique约束

    List<T> findByIds(String ids);// 通过多个ID查找//eg：ids -> “1,2,3,4”

    List<T> findByCondition(Condition condition);// 根据条件查找

    List<T> findByCondition(Example.Builder builder);// 根据条件查找

    List<T> findAll();// 获取所有

    int countByCondition(Condition condition);// 根据条件获取数量

    int countByCondition(Example.Builder builder);// 根据条件获取数量

    int count(T model);

}
