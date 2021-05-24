package com.lsatin.topclass.web.service.impl;

import com.lsatin.topclass.web.basic.service.AbstractBasicService;
import com.lsatin.topclass.web.dao.SchoolDao;
import com.lsatin.topclass.web.dao.impl.SchoolDaoImpl;
import com.lsatin.topclass.web.pojo.School;
import com.lsatin.topclass.web.service.SchoolService;

import java.util.List;

public class SchoolServiceImpl extends AbstractBasicService<School, SchoolDao> implements SchoolService {

    private SchoolDao schoolDao;

    public SchoolServiceImpl() {
        this.schoolDao = new SchoolDaoImpl();
    }

    @Override
    protected void setDao(SchoolDao dao) {
        this.schoolDao = dao;
    }

    @Override
    protected SchoolDao getDao() {
        return schoolDao;
    }

    @Override
    protected void preSave(School param) {
        final List<School> select = schoolDao.select(param, null, null);
        if (select != null && select.size() > 0) {
            final Integer version = param.getVersion();
            if (version != null) {
                param.setVersion(version + 1);
            }
        }
    }

    @Override
    public School save(School param) {
        super.save(param);
        try {
            if (param != null) {
                if (param.getId() != null && param.getId() > 0) {
                    getDao().update(param);
                } else {
                    getDao().insert(param);
                }
            }
        } catch (Exception e) {
            Logger.error("执行保存方法异常：{}", e.getMessage());
        }
        return param;
    }

    @Override
    public School deleteSchool(School param) {
        getDao().delete(param);
        return param;
    }
}
