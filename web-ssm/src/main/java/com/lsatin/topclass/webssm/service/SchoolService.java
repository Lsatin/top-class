package com.lsatin.topclass.webssm.service;

import com.lsatin.topclass.basic.pojo.Pagination;
import com.lsatin.topclass.basic.service.BasicService;
import com.lsatin.topclass.webssm.pojo.School;

import java.util.List;

/**
 * 学校业务层
 */
public interface SchoolService extends BasicService<School, Long> {

    School deleteSchool(School param);

    Pagination<List<School>> list(Pagination<School> pagination);

}
