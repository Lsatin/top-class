package com.lsatin.topclass.web.pojo;

import com.lsatin.topclass.web.base.pojo.AbstractPojo;

/**
 * 学校pojo
 */
public class School extends AbstractPojo {

    /**
     * 主键
     */
    private String id;

    /**
     * 学校名称
     */
    private String name;

    /**
     * 学校地址
     */
    private String address;

    /**
     * 邮政编码
     */
    private String zipCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public String toString() {
        return "school{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", zipCode='" + zipCode + '\'' +
                '}' + super.toString();
    }
}
