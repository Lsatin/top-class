package com.lsatin.topclass.webspringmybatis.basic.pojo;

import com.lsatin.topclass.basic.pojo.LockedPojo;
import com.lsatin.topclass.basic.pojo.PrimaryKeyPojo;

import java.io.Serializable;

public abstract class RecordPojo implements PrimaryKeyPojo<Long>, LockedPojo<Integer>, Serializable {

    /** 主键索引 */
    protected Long id;

    /** 创建时间 */
    protected Long createdTime;

    /** 创建者 */
    protected String createdBy;

    /** 更新时间 */
    protected Long updatedTime;

    /** 更新者 */
    protected String updatedBy;

    /** 乐观锁 */
    protected Integer version;

    /** 有效性 */
    protected Boolean valid;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Long createdTime) {
        this.createdTime = createdTime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Long getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Long updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    @Override
    public Integer getVersion() {
        return version;
    }

    @Override
    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return ", id=" + id +
                ", createdTime=" + createdTime +
                ", createdBy='" + createdBy + '\'' +
                ", updatedTime=" + updatedTime +
                ", updatedBy='" + updatedBy + '\'' +
                ", version=" + version +
                ", valid=" + valid +
                '}';
    }
}
