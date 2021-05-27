package com.lsatin.topclass.webspring.basic.dao.reflection.result;

import java.sql.ResultSet;

public interface ResultSetHandler<T> {
    T handle(ResultSet rs);
}
