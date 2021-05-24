package com.lsatin.topclass.web.service;

import com.lsatin.topclass.web.basic.service.BasicService;
import com.lsatin.topclass.web.pojo.School;

import java.util.List;

/**
 * 学校业务层
 */
public interface SchoolService extends BasicService<School, Long> {

    School deleteSchool(School param);

}
