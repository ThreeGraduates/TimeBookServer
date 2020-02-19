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

    @Query(value = "select sum(count) from `task` where user_id=?1 and expire_date=?2",nativeQuery = true)
    Integer getSumTimeByUserIdAndExpireDate(Long userId, String expireDate);

    @Query(value = "select count(*) from `task` where user_id=?1 and expire_date=?2",nativeQuery = true)
    Integer getTaskCountByUserIdAndExpireDate(Long userId, String expireDate);

    @Query(value = "select sum(count) from `task` where user_id=?1 and expire_date>?2",nativeQuery = true)
    Integer getFutureSumTimeByUserIdAndExpireDate(Long userId, String expireDate);

    @Query(value = "select count(*) from `task` where user_id=?1 and expire_date>?2",nativeQuery = true)
    Integer getFutureTaskCountByUserIdAndExpireDate(Long userId, String expireDate);

    @Query(value = "select sum(count) from `task` where user_id=?1 and checklist_id=?2",nativeQuery = true)
    Integer getSumTimeByUserIdAndChecklistId(Long userId,Long checklistId);

    @Query(value = "select count(*) from `task` where user_id=?1 and checklist_id=?2",nativeQuery = true)
    Integer getTaskCountByUserIdAndChecklistId(Long userId,Long checklistId);

}
