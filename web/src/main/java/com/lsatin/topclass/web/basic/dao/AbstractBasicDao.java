package com.lsatin.topclass.web.basic.dao;

import com.lsatin.topclass.basic.dao.BasicDao;
import com.lsatin.topclass.web.basic.dao.builder.SqlBound;
import com.lsatin.topclass.web.basic.dao.builder.SqlCommandType;
import com.lsatin.topclass.web.basic.dao.reflection.ReflectionException;
import com.lsatin.topclass.web.basic.dao.reflection.Reflector;
import com.lsatin.topclass.web.basic.dao.reflection.invoker.Invoker;
import com.lsatin.topclass.web.basic.dao.reflection.parameter.ParameterHandler;
import com.lsatin.topclass.web.basic.dao.reflection.result.ResultSetHandler;
import com.lsatin.topclass.web.basic.dao.reflection.type.TypeHandler;
import com.lsatin.topclass.web.basic.dao.reflection.type.TypeHandlerRegistry;
import com.lsatin.topclass.web.basic.dao.reflection.type.TypeReference;
import com.lsatin.topclass.web.context.ContextLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 抽象基础类
 * <p>默认实现{@link BasicDao}</p>
 */
public abstract class AbstractBasicDao<T> extends TypeReference<T> implements BasicDao<T, Long>, ResultSetHandler<List<T>>, ParameterHandler<T> {

    /** LOG4J */
    private final static Logger LOGGER = LogManager.getLogger();
    /** 当前使用数据源 */
    private DataSource dataSource;
    protected final Class<T> type;
    protected final Reflector reflector;
    protected final TypeHandlerRegistry typeHandlerRegistry;

    protected AbstractBasicDao() {
        type = (Class<T>) getRawType();
        dataSource = ContextLoader.dataSource;
        reflector = ContextLoader.reflectorFactory.findForClass(type);
        typeHandlerRegistry = new TypeHandlerRegistry();
    }

    protected void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    protected DataSource getDataSource() {
        return dataSource;
    }

    public List<T> handle(ResultSet rs) {
        List<T> result = null;
        try {
            result = new ArrayList<>(rs.getRow());
            while (rs.next()) {
                Object o = reflector.getDefaultConstructor().newInstance();
                for (String column : reflector.getSetablePropertyNames()) {
                    TypeHandler<Object> typeHandle = typeHandlerRegistry.getTypeHandler(reflector.getSetterType(column));
                    reflector.getSetInvoker(column).invoke(o, new Object[]{ typeHandle.getResult(rs, column)});
                }
                result.add((T) o);
            }
        } catch (SQLException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            LOGGER.error("handle ResultSet error: ", e);
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                LOGGER.error("close ResultSet error: ", e.getCause());
            }
        }
        return result;
    }

    @Override
    public PreparedStatement bind(Connection conn, T param, Integer page, Integer size, SqlCommandType commandType) throws SQLException {
        if (size == null) {
            size = 50;
        }
        if (page == null) {
            page = 0;
        }

        // bind by before
        before(param, commandType);

        final SqlBound.Builder builder = new SqlBound.Builder();
        try {
            String tableName = reflector.getAnnotationInvoker(param.getClass().getName()).invoke(param, new Object[]{"value"}).toString();
            String[] propertyNames = reflector.getGetablePropertyNames();
            builder.from(tableName);
            for (String name : propertyNames) {
                String writeColumn = reflector.getAnnotationInvoker(name).invoke(param, new Object[]{"value"}).toString();
                writeColumn = writeColumn.isEmpty() ? name : writeColumn;
                String readColumn = writeColumn + " AS " + name;
                Object invoke = reflector.getGetInvoker(name).invoke(param, null);
                builder.read(tableName, readColumn);
                if (invoke != null && !invoke.toString().isEmpty()) {
                    switch (commandType) {
                        case UPDATE:
                            builder.write(tableName, writeColumn, invoke);
                            if (writeColumn.equals("id")) {
                                builder.addEqual(tableName, writeColumn, invoke);
                            }
                            break;
                        case INSERT:
                            builder.write(tableName, writeColumn, invoke);
                            break;
                        default:
                            builder.addEqual(tableName, writeColumn, invoke);
                            break;
                    }
                }
            }
            builder.limit(page, size);
        } catch (Exception e) {
            throw new ReflectionException("组成SQL条件错误，原因：", e);
        }
        SqlBound sqlBound = builder.build();
        String sql = sqlBound.sql(commandType);
        final Object[] values = sqlBound.values(commandType);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("执行SQL: {}", sql);
            LOGGER.debug("执行参数: {}", Arrays.toString(values));
        }

        final PreparedStatement preparedStatement = conn.prepareStatement(sql);
        for (int i = 0; i < values.length; i++) {
            final TypeHandler<Object> typeHandler = typeHandlerRegistry.getTypeHandler(values[i] == null ? String.class : values[i].getClass());
            typeHandler.setParameter(preparedStatement, i + 1, values[i], JDBCType.VARCHAR);
        }
        return preparedStatement;
    }

    @Override
    public void before(T param, SqlCommandType commandType) {
        ParameterHandler.super.before(param, commandType);
    }

    @Override
    public void after() {
        ParameterHandler.super.after();
    }

    @Override
    public int count(T param) {
        int result = 0;
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = bind(connection, param, null, null, SqlCommandType.COUNT);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result = resultSet.getInt(1);
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<T> select(T param, Integer page, Integer size) {
        List<T> result = null;
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement bind = bind(connection, param, page, size, SqlCommandType.SELECT);
            ResultSet resultSet = bind.executeQuery();
            result = handle(resultSet);
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public T selectByPrimaryKey(Long id) {
        T result = null;
        try {
            final Object obj = reflector.getDefaultConstructor().newInstance();
            final Invoker invoker = reflector.getSetInvoker("id");
            invoker.invoke(invoker, new Object[]{id});
            Connection connection = dataSource.getConnection();
            PreparedStatement bind = bind(connection, (T) obj, null, null, SqlCommandType.SELECT);
            ResultSet resultSet = bind.executeQuery();
            final List<T> handle = handle(resultSet);
            if (handle != null && handle.size() > 0) {
                result = handle.get(0);
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public T insert(T param) {
        try {
            final Connection connection = dataSource.getConnection();
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            connection.setAutoCommit(false);
            final PreparedStatement bind = bind(connection, param, null, null, SqlCommandType.INSERT);
            bind.executeUpdate();
            connection.commit();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return param;
    }

    @Override
    public T update(T param) {
        try {
            final Connection connection = dataSource.getConnection();
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            connection.setAutoCommit(false);
            final PreparedStatement bind = bind(connection, param, null, null, SqlCommandType.UPDATE);
            bind.executeUpdate();
            connection.commit();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return param;
    }

    @Override
    public int delete(T param) {
        try {
            final Connection connection = dataSource.getConnection();
            PreparedStatement bind = bind(connection, param, null, null, SqlCommandType.DELETE);
            return bind.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
