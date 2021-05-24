package com.lsatin.topclass.web.context;

import com.lsatin.topclass.web.basic.dao.reflection.DefaultReflectorFactory;
import com.lsatin.topclass.web.basic.dao.reflection.ReflectorFactory;
import com.lsatin.topclass.web.util.PropertiesLoaderUtils;
import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * 上下文加载器
 * <p>参考{@code org.springframework.web.context.ContextLoader}
 */
public class ContextLoader {

    /** 默认数据源策略路径 */
    public static final String DEFAULT_DATASOURCE_STRATEGIES_PATH = "/db.properties";

    /** 数据源集 */
    public static final DataSource dataSource;

    /** 反射器工厂 */
    public static final ReflectorFactory reflectorFactory;

    private static final Properties defaultStrategies;

    static {
        reflectorFactory = new DefaultReflectorFactory();
        try {
            defaultStrategies = PropertiesLoaderUtils.loadProperties(DEFAULT_DATASOURCE_STRATEGIES_PATH);
        } catch (IOException e) {
            throw new IllegalStateException("不能加载数据源路径 'classpath:/db.properties': " + e.getMessage());
        }
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setURL(defaultStrategies.getProperty("jdbc.url", ""));
        mysqlDataSource.setUser(defaultStrategies.getProperty("jdbc.username", ""));
        mysqlDataSource.setPassword(defaultStrategies.getProperty("jdbc.password", ""));
        dataSource = mysqlDataSource;

    }

}
