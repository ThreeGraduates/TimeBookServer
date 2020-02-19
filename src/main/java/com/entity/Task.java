package com.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "task")
@Data
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Integer count;     //番茄数
    private Integer flag;      //0:已完成  1:未完成  2:中途放弃
    private Integer priority;  //任务优先级
    private Date createDate;   //任务创建日期
    private Timestamp startDateTime;  //任务开始时间
    private Timestamp expireDateTime; //任务到期时间
    private String repeat;    //重复次数
    private String remark;    //备注
    private Long userId;
    private Long checklistId;
}
