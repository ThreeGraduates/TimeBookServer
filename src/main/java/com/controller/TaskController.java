package com.controller;

import com.dao.TaskDao;
import com.entity.Task;
import com.entity.TaskStatus;
import com.util.JSONUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/task")
public class TaskController {
    private final Logger LOGGER= LoggerFactory.getLogger(TaskController.class);
    @Autowired
    private TaskDao taskDao;

    /**
     * 清单下添加任务接口
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
}
