package com.lsatin.topclass.webspring.pojo;


import com.lsatin.topclass.webspring.basic.dao.annotation.Column;
import com.lsatin.topclass.webspring.basic.dao.annotation.Table;
import com.lsatin.topclass.webspring.basic.pojo.RecordPojo;

import java.io.Serializable;

/**
 * 学校pojo
 */
@Table(value = "t_school")
public class School extends RecordPojo implements Serializable {

    /** 学校名称 */
    @Column
    private String name;

    /** 学校地址 */
    @Column
    private String address;

    /** 邮政编码 */
    @Column(value = "zip_code")
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
