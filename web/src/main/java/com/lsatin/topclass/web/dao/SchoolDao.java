package com.lsatin.topclass.web.dao;

import com.lsatin.topclass.web.base.dao.BaseDao;
import com.lsatin.topclass.web.pojo.School;

import java.util.List;

/**
 * 学校持久层
 */
public interface SchoolDao {

    /**
     * 查询学校根据主键
     * @param id　主键
     * @return {@link School}
     */
    School selectByPrimaryKey(String id);

    /**
     * 查询所有
     * @return {@link School}
     */
    List<School> select();

    /**
     * 查询所有,按条件
     * @param school {@link School}
     * @return {@link School}
     */
    List<School> select(School school);

    /**
     * 更新学校根据主键
     * @param school 学校
     * @return {@link Integer}
     */
    int updateByPrimaryKey(School school);

    /**
     * 批量更新
     * @param list 学校集
     */
    void update(List<School> list);

    /**
     * 增加学校
     * @param school 学校
     * @return {@link School}
     */
    int insert(School school);

    /**
     * 删除根据学校内容
     * @param school 学校
     */
    int delete(School school);

}
