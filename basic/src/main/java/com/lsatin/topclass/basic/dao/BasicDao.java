package com.lsatin.topclass.basic.dao;

import java.util.List;

/**
 * 基础持久层
 * <p>一个标记超接口，实现方式由各个子接口决定。
 */
public interface BasicDao<T, PK> {

    /**
     * 计数
     * @param param 条件
     * @return {@link Integer}
     */
    int count(T param);

    /**
     * 查询
     * <p>如果，<code>param</code>为<code>null</code>，则扫描全表</p>
     * @param param 查询条件
     * @param page 页脚
     * @param size 页量
     * @return {@link T} 返回实体
     */
    List<T> select(T param, Integer page, Integer size);

    /**
     * 查询
     * <p>如果，<code>id</code>为<code>null</code>，则抛出异常</p>
     * @param id 主键
     * @return {@link T} 返回实体
     */
    T selectByPrimaryKey(PK id);

    /**
     * 插入
     * @param param 实体
     * @return
     */
    T insert(T param);

    /**
     * 更新
     * <p>如果，<code>param</code>为<code>null</code>，则抛出异常</p>
     * @param param 更新实体
     * @return {@link T} 更新后实体
     */
    T update(T param);

    /**
     * 删除
     * <p>如果，<code>param</code>为<code>null</code>，则抛出异常</p>
     * @param param 删除实体
     */
    int delete(T param);

}
