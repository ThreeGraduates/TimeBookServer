package com.controller;

import com.dao.UserDao;
import com.entity.User;
import com.service.UserService;
import com.util.JSONUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@Controller
@RequestMapping("/user")
public class UserController {
    private final Logger LOGGER=LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserService userService;

    /**
     * 用户注册接口
     */
    @RequestMapping("/saveUser")
    @ResponseBody
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
        User user=new User(email,password);
        user.setUsername(email);
        user.setSignature("珍惜时间，珍惜当下");
        user.setImage("/static/images/headImg.png");
        User user1=this.userDao.save(user);
        if(user1!=null){
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
    @ResponseBody
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
    public void sendEmail(String email,HttpServletRequest request,HttpServletResponse response) throws IOException {
        //判断邮箱是否存在
        User user=this.userDao.findByEmail(email);
        if(user==null){
            response.getWriter().append("noExist");
            return;
        }
        Properties props=new Properties();
        props.setProperty("mail.debug", "true");
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.host", "smtp.163.com");
        props.setProperty("mail.transport.protocol", "smtp");
        Session session = Session.getInstance(props);
        Message msg = new MimeMessage(session);
        try {
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
            response.getWriter().append("success");
        } catch (MessagingException e) {
            response.getWriter().append("fail");
            e.printStackTrace();
        }
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

    /**
     *  上传用户头像接口
     */
    @RequestMapping("/uploadUserImage")
    @ResponseBody
    public void uploadUserImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long userId = 0l;
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        try{
            List<FileItem> list = upload.parseRequest(request);
            for(FileItem item:list) {
                User user=null;
                if(item.isFormField()) {
                    userId = Long.parseLong(item.getString());
                    user=this.userDao.findById(userId);
                }else{
                    String pathName = item.getName();
                    String fileName = pathName.substring(pathName.lastIndexOf("\\")+1);
                    String serverPath = request.getServletContext().getRealPath("/");
                    item.write(new File(serverPath+"\\upload\\"+user.getId()+"\\"+fileName));
                    user.setImage("/upload/"+user.getId()+"/"+fileName);
                    User user1=this.userDao.save(user);
                    if(user1!=null){
                        response.getWriter().append("success");
                    }else{
                        response.getWriter().append("error");
                    }
                }
            }
        }catch(Exception e){
            response.getWriter().append("error");
            e.printStackTrace();
        }
    }

    /**
     *  修改username接口
     */
    @RequestMapping("/modifyUsername")
    @ResponseBody
    public void modifyUsername(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject jsonObject=JSONUtils.getJsonObjFromRequest(request);
        long userId=jsonObject.getLong("id");
        String username=jsonObject.getString("username");
        LOGGER.info("modifyUsername-userId:{},username:{}",userId,username);
        if(!StringUtils.isBlank(username)){
            User user=this.userDao.findById(userId);
            user.setUsername(username);
            User user1=this.userDao.save(user);
            if(user1!=null){
                response.getWriter().append("success");
                return;
            }
        }
        response.getWriter().append("error");
    }

    /**
     *  修改个性签名接口
     */
    @RequestMapping("/modifySignature")
    @ResponseBody
    public void modifySignature(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject jsonObject=JSONUtils.getJsonObjFromRequest(request);
        long userId=jsonObject.getLong("id");
        String signature=jsonObject.getString("signature");
        LOGGER.info("modifySignature-userId:{},signature:{}",userId,signature);
        if(!StringUtils.isBlank(signature)){
            User user=this.userDao.findById(userId);
            user.setSignature(signature);
            User user1=this.userDao.save(user);
            if(user1!=null){
                response.getWriter().append("success");
                return;
            }
        }
        response.getWriter().append("error");
    }



    /**
     *  后端-分页展示所有用户详情
     */
    @RequestMapping(value = "/findAll",method = RequestMethod.GET)
    @ResponseBody
    public Map findAll(HttpServletRequest request){
        String limitStr=request.getParameter("limit");
        int pageSize=Integer.parseInt(StringUtils.isBlank(limitStr)?"10":limitStr);
        String offsetStr=request.getParameter("offset");
        int offset=Integer.parseInt(StringUtils.isBlank(offsetStr)?"0":offsetStr);
        String searchStr=request.getParameter("search");
        int page=offset==0?offset:offset/pageSize;
        Page<User> userList=this.userService.findAllByPage(pageSize,page,searchStr);
        Map<String,Object> data=new HashMap<>();
        data.put("total",userList.getTotalElements());
        data.put("rows",userList.getContent());
        return data;
    }

    @RequestMapping("/toUserDetail")
    public String toUserDetail(){
        return "admin/userDetail";
    }
}
