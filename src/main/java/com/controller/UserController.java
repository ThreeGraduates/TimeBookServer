package com.controller;

import com.dao.UserDao;
import com.entity.User;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
        if(user!=null&&user.getPassword().equals(password)){
            response.getWriter().append(user.getId()+"");
        }else{
            response.getWriter().append("error");
        }
    }

//    /**
//     * 重置密码发送邮件
//     */
//    @RequestMapping("sendEmail")
//    public void sendEmail(String email) throws MessagingException {
//        Properties props=new Properties();
//        // 开启debug调试
//        props.setProperty("mail.debug", "true");
//        // 发送服务器需要身份验证
//        props.setProperty("mail.smtp.auth", "true");
//        // 设置邮件服务器主机名 使用163邮箱发送
//        props.setProperty("mail.host", "smtp.163.com");
//        // 发送邮件协议名称
//        props.setProperty("mail.transport.protocol", "smtp");
//        // 设置环境信息
//        Session session = Session.getInstance(props);
//        // 创建邮件对象
//        Message msg = new MimeMessage(session);
//        //设置邮件主题
//        msg.setSubject("重置密码");
//        // 设置邮件内容
//        String msgContent =  "点击以下链接重置您的密码:<br/><br/>"
//                + "<a href="+props.getProperty("url")+"/user/resetPwd>http://"+props.getProperty("url")+"/user/resetPwd</a><br/><br/>"
//                + "感谢使用本系统。" + "<br/><br/>"
//                + "此为自动发送邮件，请勿直接回复！";
//        //设置邮件内容为html格式
//        msg.setContent(msgContent, "text/html;charset=utf-8");
//        // 设置发件人
//        msg.setFrom(new InternetAddress("timebookemail@163.com"));
//
//        Transport transport = session.getTransport();
//        // 连接邮件服务器
//        transport.connect("timebookemail@163.com", "timebook2020");
//        // 发送邮件
//        transport.sendMessage(msg, new Address[] {new InternetAddress(email)});
//        // 关闭连接
//        transport.close();
//    }
}
