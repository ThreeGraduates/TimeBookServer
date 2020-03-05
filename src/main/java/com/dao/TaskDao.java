package com.dao;

import com.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface TaskDao extends JpaRepository<Task,Long> {
    List<Task> findByUserId(Long userId);
    List<Task> findByUserIdAndFlag(Long userId,Integer flag);

    /**
     * 得到今天、明天任务总用时
     */
    @Query(value = "select sum(count) from `task` where user_id=?1 and (create_date=?2 or expire_date=?2) and flag!=2",nativeQuery = true)
    Integer getSumTimeByUserIdAndExpireDateAndCreateDate(Long userId, String expireDate);

    /**
     * 得到今天、明天任务总的已用时间
     */
    @Query(value = "select sum(use_time) from `task` where user_id=?1 and (create_date=?2 or expire_date=?2) and flag!=2",nativeQuery = true)
    Integer getUseTimeByUserIdAndExpireDateAndCreateDate(Long userId, String expireDate);

    /**
     * 得到即将到来任务总用时
     */
    @Query(value = "select sum(count) from `task` where user_id=?1 and (create_date>?2 or expire_date>?2) and flag!=2",nativeQuery = true)
    Integer getSoonSumTimeByUserIdAndExpireDateAndCreateDate(Long userId, String expireDate);

    /**
     * 得到即将到来任务总的已用时间
     */
    @Query(value = "select sum(use_time) from `task` where user_id=?1 and (create_date>?2 or expire_date>?2) and flag!=2",nativeQuery = true)
    Integer getSoonUseTimeByUserIdAndExpireDateAndCreateDate(Long userId, String expireDate);

    /**
     * 得到清单任务总用时
     */
    @Query(value = "select sum(count) from `task` where user_id=?1 and checklist_id=?2 and flag!=2",nativeQuery = true)
    Integer getSumTimeByUserIdAndChecklistId(Long userId,Long checklistId);
    /**
     * 得到清单任务总的已用时间
     */
    @Query(value = "select sum(use_time) from `task` where user_id=?1 and checklist_id=?2 and flag!=2",nativeQuery = true)
    Integer getUseTimeByUserIdAndChecklistId(Long userId,Long checklistId);
    /**
     * 得到清单任务总数
     */
    @Query(value = "select count(*) from `task` where user_id=?1 and checklist_id=?2 and flag!=2",nativeQuery = true)
    Integer getTaskCountByUserIdAndChecklistId(Long userId,Long checklistId);

    @Query(value = "select * from `task` where checklist_id=?1 and flag!=2 order by expire_date asc ",nativeQuery = true)
    List<Task> findByChecklistId(Long checklistId);

    /**
     * 根据清单,已用时间
     */
    @Query(value = "select sum(use_time) from `task` where checklist_id=?1",nativeQuery = true)
    Integer getUsedTimeByChecklistId(Long checklistId);

    @Query(value = "select sum(use_time) from `task` where checklist_id=?1 and flag=?2",nativeQuery = true)
    Integer getUsedTimeByChecklistIdAndFlag(Long checklistId,Integer flag);

    @Query(value = "select sum(count) from `task` where checklist_id=?1 and flag=?2",nativeQuery = true)
    Integer getAllTomatoCountByChecklistIdAndFlag(Long checklistId,Integer flag);

    @Query(value = "select count(*) from `task` where checklist_id=?1 and flag=?2",nativeQuery = true)
    Integer getCountByChecklistIdAndFlag(Long checklistId,Integer flag);

    /**
     * 获取今天、明天、即将到来任务列表
     */
    @Query(value = "select * from `task` where user_id=?1 and (create_date=?2 or expire_date=?2) and flag!=2 order by expire_date asc ",nativeQuery = true)
    List<Task> findByUserIdAndCreateDateOrExpireDate(Long userId,String date);

    @Query(value = "select * from `task` where user_id=?1 and create_date>?2 and flag!=2 order by expire_date asc ",nativeQuery = true)
    List<Task> findTasksComeSoon(Long userId,String date);

    @Modifying
    @Transactional
    void deleteByChecklistId(Long id);

    /**
     * 图表统计--一个月内用户番茄任务完成情况
     */
    @Query(value = "select count(*) from `task` where user_id=?1 and create_date>?2 and create_date<?3 and flag!=2",nativeQuery = true)
    Integer findByUserIdAndCreateDate(Long userId,String startDate,String endDate);

    @Query(value = "select count(*) from `task` where user_id=?1 and create_date>?2 and create_date<?3 and flag=?4",nativeQuery = true)
    Integer findByUserIdAndCreateDateAndFlag(Long userId,String startDate,String endDate,int flag);
}
