package com.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String image;
    private String email;
    //番茄时长，单位：分钟
    private Integer tomatoTime;
    //短时休息，单位：分钟
    private Integer shortBreak;
    //长时休息，单位：分钟
    private Integer longBreak;
    //长时休息间隔，单位：番茄数
    private Integer longRestInterval;
    private Date createTime;
    private String signature;

    public User(String email, String password) {
        this.email=email;
        this.password = password;
    }
}
