package com.controller;

import com.dao.UserDao;
import com.entity.User;
import com.fasterxml.jackson.databind.util.JSONPObject;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/user")
public class UserController {
    private final Logger LOGGER=LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserDao userDao;

    /**
     * 用户注册接口
     */
    @RequestMapping("/saveUser")
    public void saveUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        InputStream is=request.getInputStream();
        BufferedReader br=new BufferedReader(new InputStreamReader(is));
        String result=br.readLine();
        JSONObject jsonObject=JSONObject.fromObject(result);
        String email=jsonObject.getString("email");
        String password=jsonObject.getString("password");
        LOGGER.info("saveUser-email:{},password:{}",email,password);
        if(!StringUtils.isBlank(email)){
            boolean matches = email.matches("[A-Za-z\\d]+([-_.][A-Za-z\\d]+)*@([A-Za-z\\d]+[-.])+[A-Za-z\\d]{2,4}");
            if(!matches){
                response.getWriter().append("email-format-error");
                return;
            }
        }else{
            response.getWriter().append("email-is-null");
            return;
        }
        if(!StringUtils.isBlank(password)){
            boolean matches = email.matches("^.{6,18}$");
            if(!matches){
                response.getWriter().append("password-format-error");
                return;
            }
        }else{
            response.getWriter().append("password-is-null");
            return;
        }
        User user=this.userDao.save(new User(email,password));
        if(user!=null){
            response.getWriter().append("success");
            return;
        }else{
            response.getWriter().append("error");
            return;
        }
    }

    /**
     * 用户登录接口
     */
    @RequestMapping("/login")
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        InputStream is=request.getInputStream();
        BufferedReader br=new BufferedReader(new InputStreamReader(is));
        String result=br.readLine();
        JSONObject jsonObject=JSONObject.fromObject(result);
        String email=jsonObject.getString("email");
        String password=jsonObject.getString("password");
        LOGGER.info("login-email:{},password:{}",email,password);
        User user=this.userDao.findByEmail(email);
        if(user!=null){
            if(!user.getPassword().equals(password)){
                response.getWriter().append("password-error");
                return;
            }
        }else{
            response.getWriter().append("email-is-null");
            return;
        }
        response.getWriter().append("success");
        return;
    }
}
