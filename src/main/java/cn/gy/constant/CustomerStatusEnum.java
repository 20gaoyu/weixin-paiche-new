package cn.gy.constant;

import lombok.Getter;

/**
 * 
 * @author JDChen
 *
 */
@Getter
public enum CustomerStatusEnum {
	INVALID(0, "无效"),
    VALID(1, "有效");
    private Integer type;
    private String name;

    CustomerStatusEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }
}
