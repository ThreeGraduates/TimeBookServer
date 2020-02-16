package com.controller;

import com.dao.UserDao;
import com.entity.User;
import com.util.JSONUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

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
        JSONObject jsonObject=JSONUtils.getJsonObjFromRequest(request);
        String email=jsonObject.getString("email");
        String password=jsonObject.getString("password");
        LOGGER.info("saveUser-email:{},password:{}",email,password);
        if(!StringUtils.isBlank(email)){
            User user=this.userDao.findByEmail(email);
            if(user!=null){
                response.getWriter().append("exist");
                return;
            }
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
            response.getWriter().append(user.getId()+"");
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
        JSONObject jsonObject=JSONUtils.getJsonObjFromRequest(request);
        String email=jsonObject.getString("email");
        String password=jsonObject.getString("password");
        LOGGER.info("login-email:{},password:{}",email,password);
        User user=this.userDao.findByEmail(email);
        if(user!=null&&user.getPassword().equals(password)){
            response.getWriter().append(user.getId()+"");
        }else{
            response.getWriter().append("error");
        }
    }

    /**
     * 重置密码发送邮件接口
     */
    @RequestMapping("sendEmail")
    @ResponseBody
    public void sendEmail(String email,HttpServletRequest request) throws MessagingException {
        Properties props=new Properties();
        props.setProperty("mail.debug", "true");
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.host", "smtp.163.com");
        props.setProperty("mail.transport.protocol", "smtp");
        Session session = Session.getInstance(props);
        Message msg = new MimeMessage(session);
        msg.setSubject("重置密码");
        String path=request.getScheme()+"://"+ request.getServerName()+":"+request.getLocalPort();
        String msgContent ="请打开以下链接重置密码：<br/><br/>"
                + "<a href='"+path+"/user/resetPwd?email="+email+"'>"+path+"/user/resetPwd?email="+email+"</a><br/><br/>"
                + "感谢使用本系统。" + "<br/><br/>"
                + "此为自动发送邮件，请勿直接回复！";
        msg.setContent(msgContent, "text/html;charset=utf-8");
        msg.setFrom(new InternetAddress("timebookemail@163.com"));
        Transport transport = session.getTransport();
        transport.connect("timebookemail@163.com", "timebook2020");
        transport.sendMessage(msg, new Address[] {new InternetAddress(email)});
        transport.close();
    }

    @RequestMapping("/resetPwd")
    public String resetPwd(String email, Model model){
        model.addAttribute("email",email);
        return "resetPwd";
    }

    @PostMapping("/resetPassword")
    @ResponseBody
    public String resetPwd(String email,String password){
        if(!StringUtils.isBlank(email)&&!StringUtils.isBlank(password)){
            User user=this.userDao.findByEmail(email);
            user.setPassword(password);
            User user1=this.userDao.save(user);
            if(user1!=null){
                return "密码重置成功";
            }
        }
        return "密码重置失败";
    }

}
