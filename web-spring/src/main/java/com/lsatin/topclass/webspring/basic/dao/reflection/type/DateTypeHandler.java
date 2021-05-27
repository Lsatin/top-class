package com.lsatin.topclass.webspring.basic.dao.reflection.type;

import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class DateTypeHandler extends AbstractTypeHandler<Date> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Date parameter, JDBCType jdbcType) throws SQLException {
        ps.setDate(i, new java.sql.Date(parameter.getTime()));
    }

    @Override
    public Date getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return new Date(rs.getDate(columnName).getTime());
    }

    @Override
    public Date getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return new Date(rs.getDate(columnIndex).getTime());
    }
}
