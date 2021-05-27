package com.lsatin.topclass.webspringmybatis.service;

import com.lsatin.topclass.basic.service.BasicService;
import com.lsatin.topclass.webspringmybatis.pojo.School;

/**
 * 学校业务层
 */
public interface SchoolService extends BasicService<School, Long> {

    School deleteSchool(School param);

}
