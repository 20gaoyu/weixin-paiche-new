package cn.gy.constant;

import lombok.Getter;

/**
 * 
 * @author JDChen
 *
 */
@Getter
public enum OperationEnum {
	LEVELONE(0, "不能增加不能删除"),
	LEVELTWO(1, "只能增加不能删除"),
	LEVELThREE(2, "可以删除不能增加");
    private Integer type;
    private String name;

    OperationEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }
}
