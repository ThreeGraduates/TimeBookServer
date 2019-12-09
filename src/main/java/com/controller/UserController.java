package com.controller;

import com.dao.UserDao;
import com.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping
public class UserController {
    private final Logger LOGGER=LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserDao userDao;

    @GetMapping("/findAll")
    @ResponseBody
    public List<User> findAll(){
        LOGGER.info("userList:{}",userDao.findAll());
        return userDao.findAll();
    }
}
