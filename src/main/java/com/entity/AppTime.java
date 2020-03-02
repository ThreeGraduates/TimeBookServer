package com.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 用户每天使用APP时间统计表
 */
@Entity
@Table(name = "app_time")
@Data
@NoArgsConstructor
public class AppTime {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String appName;
    private String createDate;
    private Integer createWeek;
    private Long time;

    @Override
    public String toString() {
        return "AppTime{" +
                "id=" + id +
                ", userId=" + userId +
                ", appName='" + appName + '\'' +
                ", createDate=" + createDate +
                ", createWeek=" + createWeek +
                ", time=" + time +
                '}';
    }
}
