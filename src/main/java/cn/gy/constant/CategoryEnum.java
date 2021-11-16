package cn.gy.constant;

import lombok.Getter;

import java.util.Arrays;

/**
 * 
 * @author JDChen
 *
 */
@Getter
public enum CategoryEnum {
	INVALID(0, "失效"),
	ORDINARY(1, "普通"),
	MEDIASTORAGE(2, "媒体存储"),
	CDN(3, "CDN"),
	ADMIN(4, "系统"),
    SUPER_ADMIN(5,"系统");
    private Integer type;
    private String name;

    CategoryEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    /**
     *  根据type返回name
     * @param type
     * @return
     */
    public static String getEnumName(Integer type){
        CategoryEnum categoryEnum = Arrays.stream(CategoryEnum.values()).filter(t -> t.getType().equals(type)).findAny().orElse(null);
        String name = "";
        if(categoryEnum != null){
            name = categoryEnum.getName();
        }
        return name;
    }
}
