package com.dao;

import com.entity.TaskList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskListDao extends JpaRepository<TaskList,Long> {
}
