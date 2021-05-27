package com.lsatin.topclass.webspring.dao.impl;


import com.lsatin.topclass.webspring.basic.dao.AbstractBasicDao;
import com.lsatin.topclass.webspring.basic.dao.builder.SqlCommandType;
import com.lsatin.topclass.webspring.dao.SchoolDao;
import com.lsatin.topclass.webspring.pojo.School;
import org.springframework.stereotype.Repository;

//@Repository
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
