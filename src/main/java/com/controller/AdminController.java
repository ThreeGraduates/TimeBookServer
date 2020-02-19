package com.controller;

import com.dao.AdminDao;
import com.entity.Admin;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * 后台管理系统
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminDao adminDao;
    /**
     * 管理员登录
     */
    @RequestMapping("/toLogin")
    public String toLogin(){
        return "admin/login";
    }

    @RequestMapping("/loginAuth")
    @ResponseBody
    public String loginAuth(String username, String pwd, HttpSession session){
        if(!StringUtils.isBlank(username)&&!StringUtils.isBlank(pwd)){
            Admin admin=this.adminDao.findByName(username);
            if(admin!=null){
                if(!admin.getPassword().equals(pwd)){
                    return "密码错误";
                }
            }else{
                return "账号不存在";
            }
        }
        session.setAttribute("username",username);
        return "登录成功";
    }

    @RequestMapping("/toIndex")
    public String toIndex(){
        return "admin/index";
    }
}
