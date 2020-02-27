package com.controller;

import com.dao.AdminDao;
import com.dao.UserDao;
import com.entity.Admin;
import com.entity.User;
import com.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 后台管理系统
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminDao adminDao;
    @Autowired
    private UserService userService;
    @Autowired
    private UserDao userDao;
    /**
     * 管理员登录
     */
    @RequestMapping("/login")
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

    @RequestMapping("/index")
    public String toIndex(){
        return "admin/index";
    }

    /**
     *  后端-分页展示所有用户详情
     */
    @RequestMapping(value = "/findAllUsers",method = RequestMethod.GET)
    @ResponseBody
    public Map findAll(@RequestParam("limit") String limitStr,
                       @RequestParam("offset") String offsetStr,
                       @RequestParam(value = "username",required = false) String username,
                       @RequestParam(value = "email",required = false) String email){
        int pageSize=Integer.parseInt(StringUtils.isBlank(limitStr)?"10":limitStr);
        int offset=Integer.parseInt(StringUtils.isBlank(offsetStr)?"0":offsetStr);
        int page=offset==0?offset:offset/pageSize;
        Page<User> userList=this.userService.findAllByPage(pageSize,page,username,email);
        Map<String,Object> data=new HashMap<>();
        data.put("total",userList.getTotalElements());
        data.put("rows",userList.getContent());
        return data;
    }

    /**
     * 查看用户番茄任务完成情况
     */
    @RequestMapping("/toUserChart")
    public String toUserDetail(Long userId, Model model){
        User user=this.userDao.findById(userId);
        model.addAttribute("user",user);
        return "admin/userChart";
    }
}
