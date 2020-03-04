package com.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 用户每天使用APP时间统计表
 */
@Entity
@Table(name = "phone_time")
@Data
@NoArgsConstructor
public class PhoneTime {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String createDate;
    private Integer createWeek;
    private Long time;

    @Override
    public String toString() {
        return "PhoneTime{" +
                "id=" + id +
                ", userId=" + userId +
                ", createDate='" + createDate + '\'' +
                ", createWeek=" + createWeek +
                ", time=" + time +
                '}';
    }
}
