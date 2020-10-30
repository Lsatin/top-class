package com.lsatin.topclass.web.service.impl;

import com.lsatin.topclass.web.dao.SchoolDao;
import com.lsatin.topclass.web.dao.impl.SchoolDaoImpl;
import com.lsatin.topclass.web.pojo.School;
import com.lsatin.topclass.web.service.SchoolService;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class SchoolServiceImpl implements SchoolService {

    private final SchoolDao schoolDao = new SchoolDaoImpl();

    @Override
    public List<School> getList() {
        return schoolDao.select();
    }

    @Override
    public List<School> getList(School school) {
        return schoolDao.select(school);
    }

    @Override
    public School getSchool(String id) {
        return schoolDao.selectByPrimaryKey(id);
    }

    @Override
    public int saveSchool(School school) {
        return StringUtils.isNotEmpty(school.getId()) ? schoolDao.updateByPrimaryKey(school) : schoolDao.insert(school);
    }

    @Override
    public int deleteSchool(School school) {
        return schoolDao.delete(school);
    }
}
