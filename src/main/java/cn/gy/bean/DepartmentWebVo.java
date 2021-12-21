package cn.gy.bean;

import cn.gy.validation.Update;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class DepartmentWebVo {


    private  Long id;
    @JsonProperty( "部门")
    private  String departmentName;
    @JsonProperty( "创建时间")
    @Column(name = "create_time")
    private  Date createTime;
    @JsonProperty( "操作")
    @Column(name = "parent_id")
    private  Long parentId;
}
