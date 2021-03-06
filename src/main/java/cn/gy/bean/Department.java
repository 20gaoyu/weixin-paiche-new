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
@Table(name = "t_m_department")
public class Department {

    @Id
    @NotNull(groups = {Update.class}, message = "id不能为空")
    @Column(name = "id")
    private  Long id;
    @JSONField(name = "部门")
    @Column(name = "department_name")
    private  String departmentName;
    @JSONField(name = "创建时间")
    @Column(name = "create_time")
    private  Date createTime;
    @JSONField(name = "操作")
    @Column(name = "parent_id")
    private  Long parentId;
}
