package com.lsatin.topclass.webspring.basic.dao.reflection.parameter;

import com.lsatin.topclass.webspring.basic.dao.builder.SqlCommandType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface ParameterHandler<T> {
    PreparedStatement bind(Connection conn, T param, Integer page, Integer size, SqlCommandType commandType) throws SQLException;
    default void before(T param, SqlCommandType commandType) {}
    default void after() {}
}
