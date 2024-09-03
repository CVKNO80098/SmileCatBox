package org.learn.testspring.bean;

import lombok.Data;

import java.sql.Date;

@Data
public class UserBean {
    private int id;
    private String avatar;
    private String nickname;
    private String username;
    private String password;
    private Date birthday;
    private int sex;
    private int power;
}
