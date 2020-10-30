package com.lsatin.topclass.web.base.pojo;

import java.io.Serializable;

/**
 * 抽象实体类
 */
public abstract class AbstractPojo implements Serializable {

    /**
     * 创建日期
     */
    private String createDate;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 修改时间
     */
    private String modifyDate;

    /**
     * 修改者
     */
    private String modifier;

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    @Override
    public String toString() {
        return "{createDate='" + createDate + '\'' +
                ", creator='" + creator + '\'' +
                ", modifyDate='" + modifyDate + '\'' +
                ", modifier='" + modifier + '\'' +
                '}';
    }
}
