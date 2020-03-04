package com.dao;

import com.entity.PhoneTime;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhoneTimeDao extends JpaRepository<PhoneTime,String> {

    PhoneTime findByUserIdAndCreateWeek(Long userId, Integer week);

    List<PhoneTime> findByUserIdOrderByCreateDateAsc(Long userId);
}
