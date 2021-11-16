package cn.gy.bean;

import cn.gy.validation.Update;
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
    @Column(name = "department_name")
    private  String departmentName;
    @Column(name = "create_time")
    private  Date createTime;
    @Column(name = "parent_id")
    private  Long parentId;
}
