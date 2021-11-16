package cn.gy.core.mapper;

import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

import java.util.Set;

public class BatchUpdateProvider extends MapperTemplate {
    public BatchUpdateProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }
    public String batchUpdateByPrimaryKeySelective(MappedStatement statement) {
        return this.isSelectiveSQL(statement,true);
    }

    public String batchUpdateByPrimaryKey(MappedStatement statement) {
        return this.isSelectiveSQL(statement,false);
    }

    private String isSelectiveSQL(MappedStatement statement, boolean isSelective){
        StringBuilder builder = new StringBuilder();
        builder.append("<foreach collection=\"list\" item=\"record\" separator=\";\" >");
        //获取实体类对应的Class对象
        Class<?> entityClass = super.getEntityClass(statement);
        //获取实体类在数据库中对应的表名
        String tableName = super.tableName(entityClass);
        //生成update子句
        String updateClause = SqlHelper.updateTable(entityClass, tableName);
        builder.append(updateClause);
        builder.append("<set>");
        //获取所有字段信息
        Set<EntityColumn> columns = EntityHelper.getColumns(entityClass);
        class IdClass{
            String idColumn = null;
            String idHolder = null;
        }
        IdClass ic = new IdClass();
        columns.forEach(e->{
            if (e.isId()){//当前字段是否为主键
                ic.idColumn = e.getColumn();//缓存主键的字段名和字段值
                //返回格式:#{record.age,jdbcType=NUMERIC,typeHandler=MyTypeHandler}
                ic.idHolder = e.getColumnHolder("record");
            }else {
                //使用非主键字段拼接SET子句
                String column = e.getColumn();
                String columnHolder = e.getColumnHolder("record");
                if (isSelective){
                    builder.append("<if test=\"record.").append(e.getProperty()+" != null\">");
                    builder.append(column).append("=").append(columnHolder).append(",");
                    builder.append("</if>");
                }else {
                    builder.append(column).append("=").append(columnHolder).append(",");
                }
            }
        });
        builder.append("</set>");
        builder.append("where ").append(ic.idColumn).append("=").append(ic.idHolder);
        builder.append("</foreach>");
        return builder.toString();
    }
}
