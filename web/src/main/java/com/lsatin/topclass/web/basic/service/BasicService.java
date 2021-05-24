package com.lsatin.topclass.web.basic.service;

import com.lsatin.topclass.web.basic.pojo.Pagination;

import java.util.List;

/**
 * 基础业务层
 *
 * @param <T>  实体类型
 * @param <PK> 主键类型
 */
public interface BasicService<T, PK> {

    /**
     * 分页列表
     *
     * @param param 条件
     * @param page  页脚
     * @param size  页量
     * @return {@link Pagination<List<T>>} 分页数据
     */
    Pagination<List<T>> list(T param, Integer page, Integer size);

    /**
     * 列表
     *
     * @param param 条件
     * @return {@link List<T>} 实体集合
     */
    List<T> list(T param);

    /**
     * 获取
     *
     * @param id 主键
     * @return {@link T} 实体
     */
    T get(PK id);

    /**
     * 保存
     *
     * @param param 实体
     * @return {@link T} 实体
     */
    T save(T param);

}
