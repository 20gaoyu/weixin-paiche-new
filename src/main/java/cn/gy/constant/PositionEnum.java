package cn.gy.constant;

import lombok.Getter;

/**
 * 
 * @author JDChen
 *
 */
@Getter
public enum PositionEnum {
	AUDITD_IRECTOR(0,"审核主任"),
    AUDITD_SCHEDULING(1,"调度"),
    DRIVER(1,"司机"),
	WORKER(2,"职员");
    private Integer type;
    private String name;

    PositionEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }
}
