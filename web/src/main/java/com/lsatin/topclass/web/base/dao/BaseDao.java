package com.lsatin.topclass.web.base.dao;

/**
 * 基类持久层
 */
public abstract class BaseDao {

    /**
     * SQL 展示列后缀
     */
    protected String SQL_COLUMN_SUFFIX = "date_format(create_date, '%Y-%m-%d %H:%i:%s') as createDate, creator as creator, date_format(modify_date, '%Y-%m-%d %H:%i:%s') as modifyDate, modifier as modifier";

    /**
     * SQL分隔符(,)
     */
    protected String SQL_SEPARATOR = ",";

    /**
     * SQL 条件(and)
     */
    protected String SQL_AND = "and";

}
