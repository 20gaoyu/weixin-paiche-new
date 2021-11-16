package cn.gy.core.mapper;

import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;

public interface BatchUpdateMapper<T> {
    @UpdateProvider(type=BatchUpdateProvider.class, method="dynamicSQL")
    void batchUpdateByPrimaryKeySelective(List<T> list);

    @UpdateProvider(type=BatchUpdateProvider.class, method="dynamicSQL")
    void batchUpdateByPrimaryKey(List<T> list);

}
