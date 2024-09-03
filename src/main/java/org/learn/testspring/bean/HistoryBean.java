package org.learn.testspring.bean;

import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
public class HistoryBean {
    private int id;
    private String version;
    private String title;
    private String detail;
    private LocalDateTime time;
    private String done;

    public void set_T_Time(String time) {
        Instant instant = Instant.parse(time);
        ZoneId chinaZone = ZoneId.of("Asia/Shanghai");
        this.time = LocalDateTime.ofInstant(instant, chinaZone);
    }
}
