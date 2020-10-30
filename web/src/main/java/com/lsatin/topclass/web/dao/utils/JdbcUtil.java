package com.lsatin.topclass.web.dao.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;

/**
 * 数据库连接工具类
 */
public class JdbcUtil {

    /**
     * log4j
     */
    private static final Logger logger = LogManager.getLogger(JdbcUtil.class);

    /**
     * 数据库驱动类
     */
    private final String driverClassName;

    /**
     * JDBCurl
     */
    private final String url;

    /**
     * 数据库用户名
     */
    private final String username;

    /**
     * 数据库用户密码
     */
    private final String password;

    /**
     * 构造器
     *
     * @param builder 　构建器
     */
    private JdbcUtil(Builder builder) {
        this.driverClassName = builder.driverClassName;
        this.url = builder.url;
        this.username = builder.username;
        this.password = builder.password;
    }

    /**
     * 获取数据库连接
     *
     * @return {@link Connection}
     */
    private Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(this.driverClassName);
            connection = DriverManager.getConnection(this.url, this.username, this.password);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }

    public List<?> executeQuery(Class<?> clazz, String sql) {
        if (logger.isTraceEnabled())
            logger.trace("执行查询SQL[ {} ]", sql);
        ResultSet resultSet;
        Connection connection = getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            return ClassUtil.instanceList(clazz, resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public List<?> executeFuzzyQuery(Class<?> clazz, String sql, Object... args) {
        if (logger.isTraceEnabled()) {
            logger.trace("执行模糊查询SQL[ {} ]", sql);
            logger.trace("执行模糊查询参数[ {} ]", args.getClass().getTypeName());
        }
        ResultSet resultSet;
        Connection connection = getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            for (int i = 0, l = args.length; i < l; i++) {
                if (StringUtils.isEmpty(args[i].toString())) continue;
                statement.setObject(i + 1, args[i] + "%");
            }
            resultSet = statement.executeQuery();
            return ClassUtil.instanceList(clazz, resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public <T> T executeQuery(Class<T> clazz, String sql, Object... args) {
        if (logger.isTraceEnabled()) {
            logger.trace("执行查询SQL[ {} ]", sql);
            logger.trace("执行查询参数[ {} ]", args.getClass().getTypeName());
        }
        ResultSet resultSet;
        Connection connection = getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            for (int i = 0, l = args.length; i < l; i++) {
                statement.setObject(i + 1, args[i]);
            }
            resultSet = statement.executeQuery();
            return ClassUtil.instance(clazz, resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public int executeUpdate(String sql, Object... args) {
        if (logger.isTraceEnabled()) {
            logger.trace("执行更新SQL[ {} ]", sql);
            logger.trace("执行更新参数[ {} ]", args.getClass().getTypeName());
        }
        Connection connection = getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            for (int i = 0, l = args.length; i < l; i ++) {
                statement.setObject(i + 1, args[i]);
            }
            return statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    public int executeUpdate(String sql) {
        if (logger.isTraceEnabled())
            logger.trace("执行更新SQL[ {} ]", sql);
        Connection connection = getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            return statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    public boolean execute(String sql, Object... args) {
        if (logger.isTraceEnabled()) {
            logger.trace("执行SQL[ {} ]", sql);
            logger.trace("执行SQL参数[ {} ]", args.getClass().getTypeName());
        }
        Connection connection = getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            for (int i = 0, l = args.length; i < l; i ++) {
                statement.setObject(i + 1, args[i]);
            }
            return statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    /**
     * {@link JdbcUtil} 构建器
     */
    public static class Builder {

        /**
         * 数据库驱动类
         */
        private String driverClassName;

        /**
         * JDBCurl
         */
        private String url;

        /**
         * 数据库用户名
         */
        private String username;

        /**
         * 数据库用户密码
         */
        private String password;

        /**
         * 构建驱动类
         *
         * @param driver 数据库驱动程序类名称（绝对路径）
         * @return {@link Builder}
         */
        public Builder driver(String driver) {
            this.driverClassName = driver;
            return this;
        }

        /**
         * 构建url
         *
         * @param url JDBCurl
         * @return {@link Builder}
         */
        public Builder url(String url) {
            this.url = url;
            return this;
        }

        /**
         * 构建数据库用户名
         *
         * @param username 　数据库用户名
         * @return {@link Builder}
         */
        public Builder username(String username) {
            this.username = username;
            return this;
        }

        /**
         * 构建数据库用户密码
         *
         * @param password 数据库用户密码
         * @return {@link Builder}
         */
        public Builder password(String password) {
            this.password = password;
            return this;
        }

        /**
         * 构建
         *
         * @return {@link JdbcUtil}
         */
        public JdbcUtil build() {
            return new JdbcUtil(this);
        }

    }
}
