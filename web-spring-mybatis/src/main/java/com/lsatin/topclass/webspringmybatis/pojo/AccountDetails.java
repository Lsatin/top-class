package com.lsatin.topclass.webspringmybatis.pojo;

import com.lsatin.topclass.webspringmybatis.basic.pojo.RecordPojo;

import java.io.Serializable;

/**
 * 账户详情
 */
public class AccountDetails extends RecordPojo implements Serializable {

    /** 名字 */
    private String firstname;

    /** 姓氏 */
    private String lastname;

    /** 用户名称 */
    private String username;

    /** 用户昵称 */
    private String nickname;

    /** 性别：0男、1女、2其他（默认） */
    private String gender;

    /** 地址 */
    private String address;

    /** 地址1 */
    private String address1;

    /** 地址2 */
    private String address2;

    /** 电子邮箱 */
    private String email;

    /** 联系电话 */
    private String telephone;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public String toString() {
        return "AccountDetails{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", gender='" + gender + '\'' +
                ", address='" + address + '\'' +
                ", address1='" + address1 + '\'' +
                ", address2='" + address2 + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                super.toString();
    }
}
