package com.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "tasklist")
@Data
@NoArgsConstructor
public class TaskList {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Date createTime;
    private Long userId;
    private Integer colorId;

    public TaskList(String title, Date createTime, Long userId, Integer colorId) {
        this.title = title;
        this.createTime = createTime;
        this.userId = userId;
        this.colorId = colorId;
    }
}
