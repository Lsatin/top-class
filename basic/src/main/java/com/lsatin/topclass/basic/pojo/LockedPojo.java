package com.lsatin.topclass.basic.pojo;

/**
 * 数据锁
 * <p>标记一个实体对象锁，由各个子类实现
 */
public interface LockedPojo<T> {
    /**
     * 获取锁
     * @return {@link T}
     */
    T getVersion();

    /**
     * 加锁
     * @param version 锁
     */
    void setVersion(T version);
}
