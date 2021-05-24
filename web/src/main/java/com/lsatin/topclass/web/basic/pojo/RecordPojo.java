package com.lsatin.topclass.web.basic.pojo;

import com.lsatin.topclass.web.basic.dao.annotation.Column;

import java.io.Serializable;

public abstract class RecordPojo implements PrimaryKeyPojo<Long>, LockedPojo<Integer>, Serializable {

    /** 主键索引 */
    @Column
    protected Long id;

    /** 创建时间 */
    @Column(value = "created_time")
    protected Long createdTime;

    /** 创建者 */
    @Column(value = "created_by")
    protected String createdBy;

    /** 更新时间 */
    @Column(value = "updated_time")
    protected Long updatedTime;

    /** 更新者 */
    @Column(value = "updated_by")
    protected String updatedBy;

    /** 乐观锁 */
    @Column
    protected Integer version;

    /** 有效性 */
    @Column
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
