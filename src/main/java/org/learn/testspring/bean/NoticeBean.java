package org.learn.testspring.bean;

import lombok.Data;

@Data
public class NoticeBean {
    private int id;
    private int user_id;
    private String type;
    private String message;
    private String is_read;
}
