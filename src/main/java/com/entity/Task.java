package com.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    private Integer flag;      //0:任务已完成  1：任务未完成
    private Integer priority;  //任务优先级
    private Date createDate;
    private Date expireDate;  //任务到期时间
    private String repeat;    //重复次数
    private String remark;    //备注
    private Long userId;
    private Long checklistId;
}
