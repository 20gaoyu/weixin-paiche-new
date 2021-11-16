package cn.gy.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;

import java.util.Arrays;

public class DevUtil {

    public static boolean isDev(){
        Environment env =  SpringUtil.getBean("environment", Environment.class);
        return Arrays.stream(env.getActiveProfiles())
                .anyMatch(s -> StringUtils.containsAny(s, "dev", "local"));
    }

}
