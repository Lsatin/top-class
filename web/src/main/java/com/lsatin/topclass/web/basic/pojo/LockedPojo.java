package com.lsatin.topclass.web.basic.pojo;

public interface LockedPojo<T> {
    T getVersion();
    void setVersion(T version);
}
