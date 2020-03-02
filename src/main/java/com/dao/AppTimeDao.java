package com.dao;

import com.entity.AppTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppTimeDao extends JpaRepository<AppTime,String> {

    List<AppTime> findByUserIdAndAppNameOrderByCreateDateAsc(Long userId,String appName);

    AppTime findByUserIdAndCreateWeekAndAppName(Long userId,Integer week,String appName);
}
