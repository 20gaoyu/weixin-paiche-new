package cn.gy.core.service;


import cn.gy.core.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.math.BigInteger;
import java.util.List;

/**
 * 基于通用MyBatis Mapper插件的Service接口的实现
 */
public abstract class AbstractService<T> implements Service<T> {
    @Autowired
    protected Mapper<T> mapper;

    /**
     * 当前泛型真实类型的Class
     * */
    private Class<T> modelClass;

    public AbstractService() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        modelClass = (Class<T>) pt.getActualTypeArguments()[0];
    }

    @Override
    public int save(T model) {
        return mapper.insertSelective(model);
    }

    @Override
    public void save(List<T> models) {
        mapper.insertList(models);
    }

    @Override
    public void deleteById(Integer id) {
        mapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteByIds(String ids) {
        mapper.deleteByIds(ids);
    }

    @Override
    public void update(T model) {
        mapper.updateByPrimaryKeySelective(model);
    }

    @Override
    public List<T> find(T record) {
        return mapper.select(record);
    }

    @Override
    public T findOne(T record) {
        return mapper.selectOne(record);
    }

    @Override
    public T findById(Integer id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public void deleteById(Long id) {
        mapper.deleteByPrimaryKey(id);
    }

    @Override
    public T findById(Long id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public T findById(BigInteger id) {
        return mapper.selectByPrimaryKey(id);
    }
    @Override
    public T findBy(String fieldName, Object value) {
        Field field = null;
        try {
            T model = modelClass.newInstance();
            field = modelClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(model, value);
            return mapper.selectOne(model);
        } catch (ReflectiveOperationException e) {
            throw new ServiceException(e.getMessage(), e);
        }finally {
            if(field != null){
                field.setAccessible(false);
            }
        }
    }

    @Override
    public List<T> findByIds(String ids) {
        return mapper.selectByIds(ids);
    }

    @Override
    public List<T> findByCondition(Condition condition) {
        return mapper.selectByCondition(condition);
    }

    @Override
    public List<T> findByCondition(Example.Builder builder) {
        return mapper.selectByCondition(builder.build());
    }

    @Override
    public List<T> findAll() {
        return mapper.selectAll();
    }

    @Override
    public int countByCondition(Condition condition) {
        return mapper.selectCountByCondition(condition);
    }

    @Override
    public int countByCondition(Example.Builder builder) {
        return mapper.selectCountByCondition(builder.build());
    }

    @Override
    public int count(T model) {
        return mapper.selectCount(model);
    }
}
