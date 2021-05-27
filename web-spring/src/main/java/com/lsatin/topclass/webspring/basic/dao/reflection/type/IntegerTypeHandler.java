package com.lsatin.topclass.webspring.basic.dao.reflection.type;

import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IntegerTypeHandler extends AbstractTypeHandler<Integer> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Integer parameter, JDBCType jdbcType) throws SQLException {
        ps.setInt(i, parameter);
    }

    @Override
    public Integer getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return rs.getInt(columnName);
    }

    @Override
    public Integer getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getInt(columnIndex);
    }
}
