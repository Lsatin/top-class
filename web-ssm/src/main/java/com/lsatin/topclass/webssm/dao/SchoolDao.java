package com.lsatin.topclass.webssm.dao;


import com.lsatin.topclass.basic.dao.BasicDao;
import com.lsatin.topclass.basic.pojo.Pagination;
import com.lsatin.topclass.webssm.pojo.School;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 学校持久层
 */
@Repository
public interface SchoolDao extends BasicDao<School, Long> {

    List<School> select(Pagination<School> pagination);

}
