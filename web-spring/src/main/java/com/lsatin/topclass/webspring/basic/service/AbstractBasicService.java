package com.lsatin.topclass.webspring.basic.service;

import com.lsatin.topclass.basic.dao.BasicDao;
import com.lsatin.topclass.basic.pojo.Pagination;
import com.lsatin.topclass.basic.service.BasicService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBasicService<T, D extends BasicDao<T, Long>> implements BasicService<T, Long> {

    protected final static Logger Logger = LogManager.getLogger();

    public abstract void setDao(D dao);

    public abstract D getDao();

    protected abstract void preSave(T param);

    @Override
    public Pagination<List<T>> list(T param, Integer page, Integer size) {
        if (Logger.isTraceEnabled()) {
            Logger.trace("执行分页方法：{}", param.toString());
        }
        try {
            return new Pagination<>(page, size, getDao().count(param), getDao().select(param, (page - 1) * size, size));
        } catch (Exception e) {
            Logger.error("执行分页方法发生异常: ", e);
            return new Pagination<>(page, size, 0);
        }
    }

    @Override
    public List<T> list(T param) {
        if (Logger.isTraceEnabled()) {
            Logger.trace("执行列表方法：{}", param.toString());
        }
        try {
           return getDao().select(param, null, null);
        } catch (Exception e) {
            Logger.error("执行列表方法发生异常：", e);
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public T get(Long id) {
        if (Logger.isTraceEnabled()) {
            Logger.trace("执行查询方法：{}", id);
        }
        T result;
        try {
            result = getDao().selectByPrimaryKey(id);
        } catch (Exception e) {
            Logger.error("执行查询方法异常：", e);
            result = null;
        }
        return result;
    }

    @Override
    public T save(T param) {
        preSave(param);
        return param;
    }
}
