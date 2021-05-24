package com.lsatin.topclass.web.dao.impl;

import com.lsatin.topclass.web.basic.dao.AbstractBasicDao;
import com.lsatin.topclass.web.basic.dao.builder.SqlCommandType;
import com.lsatin.topclass.web.dao.SchoolDao;
import com.lsatin.topclass.web.pojo.School;

public class SchoolDaoImpl extends AbstractBasicDao<School> implements SchoolDao {
    @Override
    public void before(School param, SqlCommandType commandType) {
        switch (commandType) {
            case INSERT:
                param.setCreatedTime(System.currentTimeMillis());
                param.setUpdatedTime(System.currentTimeMillis());
                break;
            case UPDATE:
                param.setUpdatedTime(System.currentTimeMillis());
                break;
            default:
                break;
        }
    }
}
