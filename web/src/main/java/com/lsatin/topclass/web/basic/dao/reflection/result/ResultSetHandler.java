package com.lsatin.topclass.web.basic.dao.reflection.result;

import java.sql.ResultSet;

public interface ResultSetHandler<T> {
    T handle(ResultSet rs);
}
