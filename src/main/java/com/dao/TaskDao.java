package com.dao;

import com.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TaskDao extends JpaRepository<Task,Long> {
    List<Task> findByUserId(Long userId);
    List<Task> findByUserIdAndFlag(Long userId,Integer flag);

    @Query(value = "select sum(count) from `task` where user_id=?1 and create_date=?2",nativeQuery = true)
    Integer getSumTimeByUserIdAndCreateDate(Long userId, String createDate);

    @Query(value = "select count(*) from `task` where user_id=?1 and create_date=?2",nativeQuery = true)
    Integer getTaskCountByUserIdAndCreateDate(Long userId, String createDate);

    @Query(value = "select sum(count) from `task` where user_id=?1 and create_date>?2",nativeQuery = true)
    Integer getFutureSumTimeByUserIdAndCreateDate(Long userId, String createDate);

    @Query(value = "select count(*) from `task` where user_id=?1 and create_date>?2",nativeQuery = true)
    Integer getFutureTaskCountByUserIdAndCreateDate(Long userId, String createDate);

    @Query(value = "select sum(count) from `task` where user_id=?1 and checklist_id=?2",nativeQuery = true)
    Integer getSumTimeByUserIdAndChecklistId(Long userId,Long checklistId);

    @Query(value = "select count(*) from `task` where user_id=?1 and checklist_id=?2",nativeQuery = true)
    Integer getTaskCountByUserIdAndChecklistId(Long userId,Long checklistId);

}
