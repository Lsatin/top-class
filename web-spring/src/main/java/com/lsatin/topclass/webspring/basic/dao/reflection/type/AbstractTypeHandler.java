package com.lsatin.topclass.webspring.basic.dao.reflection.type;

import com.lsatin.topclass.webspring.basic.dao.reflection.result.ResultException;

import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractTypeHandler<T> extends TypeReference<T> implements TypeHandler<T> {

    public abstract void setNonNullParameter(PreparedStatement ps, int i, T parameter, JDBCType jdbcType) throws SQLException;

    public abstract T getNullableResult(ResultSet rs, String columnName) throws SQLException;

    public abstract T getNullableResult(ResultSet rs, int columnIndex) throws SQLException;

    @Override
    public void setParameter(PreparedStatement ps, int i, T parameter, JDBCType jdbcType) throws SQLException {
        if (parameter == null) {
            if (jdbcType == null) {
                throw new TypeException("参数为空，JDBC类型必填");
            }
            try {
                ps.setNull(i, jdbcType.getVendorTypeNumber());
            } catch (Exception e) {
                throw new TypeException("设置空参数错误，索引：" + i + "原因：", e);
            }
        } else {
            try {
                setNonNullParameter(ps, i, parameter, jdbcType);
            } catch (Exception e) {
                throw new TypeException("设置参数错误，索引：" + i + "原因：", e);
            }
        }
    }

    @Override
    public T getResult(ResultSet rs, String columnName) throws SQLException {
        T result;
        try {
            result = getNullableResult(rs, columnName);
        } catch (Exception e) {
            throw new ResultException("列 '" + columnName + "' 获取错误.  原因: " + e, e);
        }
        if (rs.wasNull()) {
            return null;
        } else {
            return result;
        }
    }

    @Override
    public T getResult(ResultSet rs, int columnIndex) throws SQLException {
        T result;
        try {
            result = getNullableResult(rs, columnIndex);
        } catch (Exception e) {
            throw new ResultException("列索引 '" + columnIndex + "' 获取错误.  原因: " + e, e);
        }
        if (rs.wasNull()) {
            return null;
        } else {
            return result;
        }
    }
}
