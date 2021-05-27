package com.lsatin.topclass.webspringmybatis.basic.dao;

import com.lsatin.topclass.basic.dao.BasicDao;
import com.lsatin.topclass.basic.pojo.Pagination;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public abstract class AbstractDao<T> implements BasicDao<T, Long> {

    public abstract void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory);

    public abstract SqlSessionFactory getSqlSessionFactory();

    @Override
    public int count(T param) {
        SqlSession sqlSession = getSqlSessionFactory().openSession();
        Object o = sqlSession.selectOne(getClass().getName() + "." + "count", param);
        sqlSession.close();
        return (int) o;
    }

    @Override
    public List<T> select(T param, Integer page, Integer size) {
        SqlSession sqlSession = getSqlSessionFactory().openSession();
        Pagination<T> pagination = new Pagination<>();
        pagination.setData(param);
        pagination.setCurrent(new Pagination.Page(page, size));
        List<Object> list = sqlSession.selectList(getClass().getName() + "." + "select", pagination);
        sqlSession.close();
        return (List<T>) list;
    }

    @Override
    public T selectByPrimaryKey(Long id) {
        SqlSession sqlSession = getSqlSessionFactory().openSession();
        Object o = sqlSession.selectOne(getClass().getName() + "." + "selectByPrimaryKey", id);
        sqlSession.close();
        return (T) o;
    }

    @Override
    public T insert(T param) {
        return null;
    }

    @Override
    public T update(T param) {
        return null;
    }

    @Override
    public int delete(T param) {
        return 0;
    }
}
