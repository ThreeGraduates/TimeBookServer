package com.service;

import com.dao.TaskListDao;
import org.springframework.beans.factory.annotation.Autowired;

public class TaskListService {
    @Autowired
    private TaskListDao taskListDao;
}
