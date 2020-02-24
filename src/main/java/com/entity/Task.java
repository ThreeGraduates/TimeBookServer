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
    private Integer count;     //任务所需番茄数
    private Integer flag;      //0:已完成  1:未完成  2:中途放弃
    private Integer priority;  //任务优先级

    private Date createDate;   //任务创建日期
    private Date expireDate; //任务到期日期
    private Timestamp startDatetime;  //任务开始时间
    private Timestamp completeDatetime;  //任务完成时间
    private Integer useTime;  //任务已用时间，单位：分钟

    private String repeat;    //重复次数
    private String remark;    //备注

    private Long userId;
    private Long checklistId;
}
