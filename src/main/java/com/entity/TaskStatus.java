package com.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 用户番茄任务完成情况
 */
@Data
@AllArgsConstructor
public class TaskStatus {
    //已完成，未完成，中途放弃
    private String name;
    //所占比列
    private float share;

}
