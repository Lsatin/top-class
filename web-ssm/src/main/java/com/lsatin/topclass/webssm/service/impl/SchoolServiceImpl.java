package com.lsatin.topclass.webssm.service.impl;



import com.lsatin.topclass.basic.constant.Constants;
import com.lsatin.topclass.basic.pojo.Pagination;
import com.lsatin.topclass.webssm.basic.service.AbstractBasicService;
import com.lsatin.topclass.webssm.dao.SchoolDao;
import com.lsatin.topclass.webssm.pojo.School;
import com.lsatin.topclass.webssm.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolServiceImpl extends AbstractBasicService<School, SchoolDao> implements SchoolService {

    private SchoolDao dao;

    @Autowired
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
            LOGGER.error("执行保存方法异常：", e);
            e.printStackTrace();
        }
        return param;
    }

    @Override
    public School deleteSchool(School param) {
        dao.delete(param);
        return param;
    }

    @Override
    public Pagination<List<School>> list(Pagination<School> pagination) {
        Pagination<School> clone = new Pagination<>();
        try {
            clone = pagination.clone();
        } catch (CloneNotSupportedException e) {
            LOGGER.error("克隆分页器对象失败：", e);
        }
        int count = dao.count(pagination.getData());
        if (pagination.getCurrent() != null) {
            Pagination.Page current = pagination.getCurrent();
            if (current.getSize() == null || current.getSize() < 1) {
                current.setSize(Constants.DEFAULT_PAGINATION_SIZE);
            }
            if (current.getPage() == null || current.getPage() < 0) {
                current.setPage(Constants.DEFAULT_PAGINATION_PAGE - 1);
            } else {
                current.setPage((current.getPage() - 1) * current.getSize());
            }
        } else {
            Pagination.Page page = new Pagination.Page(Constants.DEFAULT_PAGINATION_PAGE - 1, Constants.DEFAULT_PAGINATION_SIZE);
            pagination.setCurrent(page);
        }
        List<School> select = dao.select(pagination);
        return new Pagination<>(clone.getCurrent().getPage(), clone.getCurrent().getSize(), count, select);
    }
}
