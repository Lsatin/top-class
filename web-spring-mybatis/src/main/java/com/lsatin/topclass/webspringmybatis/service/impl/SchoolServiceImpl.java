package com.lsatin.topclass.webspringmybatis.service.impl;


import com.lsatin.topclass.webspringmybatis.basic.service.AbstractBasicService;
import com.lsatin.topclass.webspringmybatis.dao.SchoolDao;
import com.lsatin.topclass.webspringmybatis.pojo.School;
import com.lsatin.topclass.webspringmybatis.service.SchoolService;

import java.util.List;

public class SchoolServiceImpl extends AbstractBasicService<School, SchoolDao> implements SchoolService {

    private SchoolDao dao;

    @Override
    public void setDao(SchoolDao dao) {
        this.dao = dao;
    }

    @Override
    public SchoolDao getDao() {
        return dao;
    }

    @Override
    protected void preSave(School param) {
        final List<School> select = dao.select(param, null, null);
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
                    dao.update(param);
                } else {
                    dao.insert(param);
                }
            }
        } catch (Exception e) {
            Logger.error("执行保存方法异常：{}", e.getMessage());
        }
        return param;
    }

    @Override
    public School deleteSchool(School param) {
        dao.delete(param);
        return param;
    }
}
