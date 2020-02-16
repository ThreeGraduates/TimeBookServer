package com.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
@Entity
@Table(name = "tasklist")
@Data
@NoArgsConstructor
public class TaskList {
    @Id
    private Long id;
    private String title;
    private Date createTime;
    private Long userId;
    private String color;

    public TaskList(String title, Date createTime, Long userId, String color) {
        this.title = title;
        this.createTime = createTime;
        this.userId = userId;
        this.color = color;
    }
}
