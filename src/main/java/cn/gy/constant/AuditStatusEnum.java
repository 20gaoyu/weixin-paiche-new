package cn.gy.constant;

import lombok.Getter;

/**
 * 
 * @author JDChen
 *
 */
@Getter
public enum AuditStatusEnum {
	AUDITDING(0,"审核中"),
    SCHEDULING(1,"调度中"),
    COMPLETE(2,"已完成"),
    CANCLE_WEB(3,"已完成"),
	CANCLE(4,"驳回");
    private Integer type;
    private String name;

    AuditStatusEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }
}
