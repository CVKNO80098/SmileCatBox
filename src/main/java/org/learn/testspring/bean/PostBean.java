package org.learn.testspring.bean;

import lombok.Data;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
public class PostBean {
    private String id;
    private int overhead;
    private String title;
    private String content;
    private LocalDateTime time;
    private int author;
    private LocalDateTime target_date;
    private String cover;
    private int visits;
    private int star_num;
    private String type;
    private String privacy;

//    public void setTime(String time) {
//        Instant instant = Instant.parse(time);
//        ZoneId chinaZone = ZoneId.of("Asia/Shanghai");
//        this.time = LocalDateTime.ofInstant(instant, chinaZone);
//    }
}
//this is a ^ nya