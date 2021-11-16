package cn.gy.constant;

import lombok.Getter;

import java.util.Arrays;

/**
 * 
 * @author JDChen
 *
 */
@Getter
public enum CustomerSourceEnum {
	CDN(0, "CDN"),
	MEDIASTORAGE(1, "媒体存储"),
    ADMIN(2, "系统管理员");
    private Integer type;
    private String name;

    CustomerSourceEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    /**
     *  根据type返回name
     * @param type
     * @return
     */
    public static String getEnumName(Integer type){
        CustomerSourceEnum customerSourceEnum = Arrays.stream(CustomerSourceEnum.values()).filter(t -> t.getType().equals(type)).findAny().orElse(null);
        String name = "";
        if(customerSourceEnum != null){
            name = customerSourceEnum.getName();
        }
        return name;
    }

    /**
     *  根据name返回type
     * @param scene
     * @return
     */
    public static Integer getEnumType(String scene){
        CustomerSourceEnum customerSourceEnum = Arrays.stream(CustomerSourceEnum.values()).filter(t -> t.getName().equals(scene)).findAny().orElse(null);
        Integer type = -1;
        if(customerSourceEnum != null){
            type = customerSourceEnum.getType();
        }
        return type;
    }
}
