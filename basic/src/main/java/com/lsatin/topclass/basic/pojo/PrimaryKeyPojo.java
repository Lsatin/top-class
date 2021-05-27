package com.lsatin.topclass.basic.pojo;

/**
 * 主键对象
 * <p>标记一个实体对象主键，由各个子类实现
 */
public interface PrimaryKeyPojo<PK> {
    /** 获取主键 */
    PK getId();
    /** 设置主键 */
    void setId(PK id);
}
