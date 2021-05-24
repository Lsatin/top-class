package com.lsatin.topclass.web.basic.dao.reflection.type;

import java.lang.reflect.Type;
import java.sql.JDBCType;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class TypeHandlerRegistry {

    private final Map<JDBCType, TypeHandler<?>> JDBC_TYPE_HANDLER_MAP = new EnumMap<JDBCType, TypeHandler<?>>(JDBCType.class);
    private final Map<Class<?>, TypeHandler<?>> JAVA_TYPE_HANDLE_MAP = new HashMap<>();

    public TypeHandlerRegistry() {
        register(Boolean.class, new BooleanTypeHandler());
        register(boolean.class, new BooleanTypeHandler());
        register(JDBCType.BOOLEAN, new BooleanTypeHandler());
        register(JDBCType.BIT, new BooleanTypeHandler());

        register(Date.class, new DateTypeHandler());

        register(String.class, new StringTypeHandler());
        register(JDBCType.VARCHAR, new StringTypeHandler());
        register(JDBCType.CHAR, new StringTypeHandler());
        register(JDBCType.CLOB, new StringTypeHandler());

        register(Integer.class, new IntegerTypeHandler());
        register(int.class, new IntegerTypeHandler());
        register(JDBCType.INTEGER, new IntegerTypeHandler());

        register(Long.class, new LongTypeHandler());
        register(long.class, new LongTypeHandler());
        register(JDBCType.BIGINT, new LongTypeHandler());
    }

    public <T> void register(Class<T> javaType, TypeHandler<? extends T> typeHandler) {
        JAVA_TYPE_HANDLE_MAP.put(javaType, typeHandler);
    }

    public <T> void register(JDBCType jdbcType, TypeHandler<? extends T> typeHandler) {
        JDBC_TYPE_HANDLER_MAP.put(jdbcType, typeHandler);
    }


    public <T> TypeHandler<T> getTypeHandler(Type type) {
        return (TypeHandler<T>) JAVA_TYPE_HANDLE_MAP.get(type);
    }

    public <T> TypeHandler<T> getTypeHandler(JDBCType jdbcType) {
        return (TypeHandler<T>) JDBC_TYPE_HANDLER_MAP.get(jdbcType);
    }

}
