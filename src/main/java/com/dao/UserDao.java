package com.dao;

import com.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository<User,String>, JpaSpecificationExecutor<User> {
    User findByEmail(String email);

    User findById(Long userId);

}
