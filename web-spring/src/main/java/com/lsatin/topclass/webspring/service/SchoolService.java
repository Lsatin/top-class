package com.lsatin.topclass.webspring.service;

import com.lsatin.topclass.basic.service.BasicService;
import com.lsatin.topclass.webspring.pojo.School;

/**
 * 学校业务层
 */
public interface SchoolService extends BasicService<School, Long> {

    School deleteSchool(School param);

}
