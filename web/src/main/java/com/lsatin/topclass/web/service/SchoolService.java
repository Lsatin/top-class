package com.lsatin.topclass.web.service;

import com.lsatin.topclass.web.pojo.School;

import java.util.List;

/**
 * 学校业务层
 */
public interface SchoolService {

    /**
     * 获取列表
     * @return {@link School} 学校信息
     */
    List<School> getList();

    /**
     * 获取匹配学校
     * @param school 学校条件
     * @return {@link School} 学校信息
     */
    List<School> getList(School school);

    /**
     * 获取学校
     * @param id 主键
     * @return {@link School} 学校信息
     */
    School getSchool(String id);

    /**
     * 保存学校
     * <p>存在主键，更新记录。反之新增</p>
     * @param school 学校信息
     * @return {@link Integer} 影响条数
     */
    int saveSchool(School school);

    /**
     * 删除学校
     * @param school 学校
     * @return {@link Integer} 影响条数
     */
    int deleteSchool(School school);

}
