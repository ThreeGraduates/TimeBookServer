package com.controller;

import com.dao.TaskDao;
import com.entity.AppTime;
import com.entity.Task;
import com.entity.TaskStatus;
import com.util.JSONUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.jni.Time;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/task")
public class TaskController {
    private final Logger LOGGER= LoggerFactory.getLogger(TaskController.class);
    @Autowired
    private TaskDao taskDao;

    /**
     * 清单下添加任务接口(废弃)
     */
    @RequestMapping("/addTask")
    public void addTask(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject jsonObject = JSONUtils.getJsonObjFromRequest(request);
        String title = jsonObject.getString("title");
        Integer count = jsonObject.getInt("count");
        Integer priority = jsonObject.getInt("priority");
        String expireDate = jsonObject.getString("expireDate");
    }

    /**
     * 图表统计--获取用户番茄任务状态饼状图
     */
    @RequestMapping("/getTaskStatusPieChart")
    @ResponseBody
    public void getPieChart(HttpServletRequest request,HttpServletResponse response) throws IOException {
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        Long userId=Long.parseLong(request.getParameter("userId"));
        String year=request.getParameter("year");
        String month=request.getParameter("month");
        String startDate=year+"-"+month+"-01";
        String endDate=year+"-"+month+"-31";
        Integer allTasks=this.taskDao.findByUserIdAndCreateDate(userId,startDate,endDate);
        Integer task0=this.taskDao.findByUserIdAndCreateDateAndFlag(userId,startDate,endDate,0);  //已完成
        Integer task1=this.taskDao.findByUserIdAndCreateDateAndFlag(userId,startDate,endDate,1);  //未完成
        Integer task2=this.taskDao.findByUserIdAndCreateDateAndFlag(userId,startDate,endDate,2);  //中途放弃
        if(allTasks!=0){
            JSONObject object=new JSONObject();
            NumberFormat ddf1= NumberFormat.getNumberInstance() ;
            ddf1.setMaximumFractionDigits(3);
            object.put("finish",ddf1.format((float)task0/(float)allTasks));
            object.put("unFinish",ddf1.format((float)task1/(float)allTasks));
            object.put("abandon",ddf1.format((float)task2/(float)allTasks));
            response.getWriter().append(object.toString());
        }else{
            response.getWriter().append("empty");
        }
    }

    /**
     *  将已完成任务变为未完成，未完成任务变为已完成
     */
    @RequestMapping("/transFormTaskStatus")
    @ResponseBody
    public void transFormTaskStatus(HttpServletRequest request,HttpServletResponse response) throws ParseException, IOException {
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        Long taskId=Long.parseLong(request.getParameter("taskId"));
        Integer originFlag=Integer.parseInt(request.getParameter("flag"));
        Task task=this.taskDao.findOne(taskId);
        if(originFlag==0){  //未完成 -->已完成
            task.setFlag(1);
            java.util.Date date = new java.util.Date();
            Timestamp timeStamp = new Timestamp(date.getTime());
            task.setCompleteDatetime(timeStamp);
        }else if(originFlag==1){  //已完成 -->未完成
            task.setFlag(0);
            task.setCompleteDatetime(null);
        }
        Task task1=this.taskDao.save(task);
        if(task1!=null){
            response.getWriter().append("success");
        }else{
            response.getWriter().append("error");
        }
    }

    /**
     * 保存任务
     */
    @RequestMapping("/saveTask")
    @ResponseBody
    public void saveTask(HttpServletRequest request,HttpServletResponse response) throws IOException, ParseException {
        JSONObject jsonObject=JSONUtils.getJsonObjFromRequest(request);
        long checklistId=jsonObject.getLong("checkListId");
        int count=jsonObject.getInt("count");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        String createDateStr=jsonObject.getString("createDate");
        String expireDateStr=jsonObject.getString("expireDate");
        Date createDate=null;
        if(!StringUtils.isBlank(createDateStr)&&!"null".equals(createDateStr)){ createDate=sdf2.parse(createDateStr); }
        Date expireDate=null;
        if(!StringUtils.isBlank(expireDateStr)&&!"null".equals(expireDateStr)){ expireDate=sdf2.parse(expireDateStr); }
        int flag=jsonObject.getInt("flag");
        int priority=jsonObject.getInt("priority");
        String remark=jsonObject.getString("remark");
        String repeat=jsonObject.getString("repeat");
        String startDatetimeStr=jsonObject.getString("startDatetime");
        String completeDatetimeStr=jsonObject.getString("completeDatetime");
        Timestamp startDatetime=null;
        if(!StringUtils.isBlank(startDatetimeStr)&&!"null".equals(startDatetimeStr)){
            startDatetime= new Timestamp(sdf1.parse(startDatetimeStr).getTime());
        }
        Timestamp completeDatetime=null;
        if(!StringUtils.isBlank(completeDatetimeStr)&&!"null".equals(completeDatetimeStr)){completeDatetime=new Timestamp(sdf1.parse(completeDatetimeStr).getTime());}
        String title=jsonObject.getString("title");
        int useTime=jsonObject.getInt("useTime");
        long userId=jsonObject.getLong("userId");

        Task task=new Task(title,count,flag,priority,createDate,expireDate,startDatetime,completeDatetime,useTime,repeat,remark,userId,checklistId);
        this.taskDao.save(task);
    }

    /**
     * 删除任务
     */
    @RequestMapping("/deleteTask")
    @ResponseBody
    public void deleteTask(HttpServletRequest request,HttpServletResponse response) throws IOException {
        Long taskId=Long.parseLong(request.getParameter("taskId"));
        this.taskDao.delete(taskId);
        response.getWriter().append("success");
    }
}
