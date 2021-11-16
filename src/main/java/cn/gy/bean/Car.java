package cn.gy.bean;

import cn.gy.validation.Update;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Table(name = "t_m_car")
public class Car {

    @Id
    @NotNull(groups = {Update.class}, message = "id不能为空")
    @Column(name = "id")
    private  Long id;
    @Column(name = "license")
    private  String license;
    @Column(name = "brand")
    private  String brand;
    @Column(name = "type")
    private  String type;
    @Column(name = "create_time")
    private  Date createTime;
    @Column(name = "mileage")
    private Long mileage;

}
