package cn.gy.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class PropUtil {

    public static Properties getProperties(String filepath) {
        Properties prop = new Properties();
        try(InputStream resourceAsStream = PropUtil.class.getClassLoader().getResourceAsStream(filepath)) {
            prop.load(resourceAsStream);
        } catch (IOException e) {
            log.error("加载prop失败", e);
        }
        return prop;
    }

    public static String getProperty(Properties prop, String key) {
        if (prop == null) {
            return null;
        }
        String value = prop.getProperty(key);
        if (StringUtils.isBlank(value)) {
            return null;
        }
        return value;
    }

    public static String getProperty(Properties prop, String key, String defaultValue) {
        if (prop == null) {
            return defaultValue;
        }
        String value = prop.getProperty(key);
        if (StringUtils.isBlank(value)) {
            value = defaultValue;
        }
        return value;
    }

}
