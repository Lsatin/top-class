package com.lsatin.topclass.webspringmybatis.pojo;



import com.lsatin.topclass.webspringmybatis.basic.pojo.RecordPojo;

import java.io.Serializable;

/**
 * 学校pojo
 */
public class School extends RecordPojo implements Serializable {

    /** 学校名称 */
    private String name;

    /** 学校地址 */
    private String address;

    /** 邮政编码 */
    private String zipCode;

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
        return "School{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", zipCode='" + zipCode + '\'' +
                super.toString();
    }
}
