package com.lsatin.topclass.web.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 属性加载器工具类
 *
 * <p>参考{@code org.springframework.core.io.support.PropertiesLoaderUtils}
 */
public abstract class PropertiesLoaderUtils {

    /**
     * 加载一个属性配置
     * @param path 路径
     * @return {@link Properties} 属性配置
     */
    public static Properties loadProperties(String path) throws IOException {
        Properties props = new Properties();
        fillProperties(props, path);
        return props;
    }

    /**
     * 填充属性配置
     * @param props 属性配置实例
     * @param path 路径
     */
    public static void fillProperties(Properties props, String path) throws IOException {
        try(InputStream is = PropertiesLoaderUtils.class.getClassLoader().getResourceAsStream(path)) {
            props.load(is);
        }
    }

}
