package com.lsatin.topclass.webspringmybatis.dao.impl;

import com.lsatin.topclass.webspringmybatis.basic.dao.AbstractDao;
import com.lsatin.topclass.webspringmybatis.dao.SchoolDao;
import com.lsatin.topclass.webspringmybatis.pojo.School;
import org.apache.ibatis.session.SqlSessionFactory;

public class SchoolDaoImpl extends AbstractDao<School> implements SchoolDao {

    private SqlSessionFactory sqlSessionFactory;

    @Override
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }
}
