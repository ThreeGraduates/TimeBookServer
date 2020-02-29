package com.controller;

import com.dao.TaskDao;
import com.dao.TaskListDao;
import com.dao.UserDao;
import com.entity.Task;
import com.entity.TaskList;
import com.entity.User;
import com.util.JSONUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/taskList")
public class TaskListController {
    private final Logger LOGGER= LoggerFactory.getLogger(TaskListController.class);
    @Autowired
    private TaskListDao taskListDao;
    @Autowired
    private TaskDao taskDao;
    @Autowired
    private UserDao userDao;

    /**
     * 创建清单接口
     */
    @RequestMapping("/saveTaskList")
    @ResponseBody
    public void saveTaskList(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
        JSONObject jsonObject= JSONUtils.getJsonObjFromRequest(request);
        String title=jsonObject.getString("title");
        Integer color=jsonObject.getInt("colorId");
        Long userId=jsonObject.getLong("userId");
        String createTime=jsonObject.getString("createTime");
        Date date = DateUtils.parseDate(createTime,new String[]{"yyyy-MM-dd"});
        TaskList taskList=this.taskListDao.save(new TaskList(title,date,userId,color));
        if(taskList!=null){
            response.getWriter().append(taskList.getId()+"");
        }else{
            response.getWriter().append("error");
        }
    }

    /**
     * 今天、明天、即将到来任务列表
     */
    @RequestMapping("/getTasksTodayAndTomorrowAndSoon")
    @ResponseBody
    public void getTasksTodayAndTomorrowAndSoon(HttpServletRequest request,HttpServletResponse response) throws IOException {
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        Integer flag=Integer.parseInt(request.getParameter("flag"));
        Long userId=Long.parseLong(request.getParameter("userId"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<Task> tasks=new ArrayList<>();
        if(flag==0){ //今天
            tasks=this.taskDao.findByUserIdAndCreateDateOrExpireDate(userId,sdf.format(new Date()));
        }else{
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(new Date());
            calendar.add(calendar.DATE,1);
            if(flag==1){  //明天
                tasks=this.taskDao.findByUserIdAndCreateDateOrExpireDate(userId,sdf.format(calendar.getTime()));
            }else{    //即将到来
                tasks=this.taskDao.findTasksComeSoon(userId,sdf.format(calendar.getTime()));
            }
        }
        JSONArray array=new JSONArray();
        for(Task task:tasks){
            JSONObject obj=new JSONObject();
            TaskList taskList=this.taskListDao.findOne(task.getChecklistId());
            obj.put("list_title",taskList.getTitle());
            obj.put("list_colorId",taskList.getColorId());
            //任务详情
            obj.put("id",task.getId());
            obj.put("title",task.getTitle());
            obj.put("count",task.getCount());
            obj.put("flag",task.getFlag());
            obj.put("priority",task.getPriority());
            if(task.getCreateDate()!=null){ obj.put("createDate",sdf.format(task.getCreateDate())); }
            if(task.getExpireDate()!=null){obj.put("expireDate",sdf.format(task.getExpireDate()));}
            SimpleDateFormat sdf2= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if(task.getStartDatetime()!=null){ obj.put("startDatetime",sdf2.format(task.getStartDatetime()));}
            if(task.getCompleteDatetime()!=null){obj.put("completeDatetime",sdf2.format(task.getCompleteDatetime()));}
            obj.put("useTime",task.getUseTime());
            obj.put("repeat",task.getRepeat());
            obj.put("remark",task.getRemark());
            array.add(obj);
        }
        response.getWriter().append(array.toString());
    }

    /**
     *  点击清单，获取清单任务列表
     */
    @RequestMapping("/getTasksByListId")
    @ResponseBody
    public void getTasksByListId(HttpServletRequest request,HttpServletResponse response) throws IOException {
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        Long listId=Long.parseLong(request.getParameter("taskListId"));
        TaskList taskList=this.taskListDao.findOne(listId);
        User user=this.userDao.findById(taskList.getUserId());

        Integer sumTime=this.taskDao.getAllTomatoCountByChecklistIdAndFlag(listId,0)*user.getTomatoTime();
        Integer usedTime=this.taskDao.getUsedTimeByChecklistIdAndFlag(listId,0);

        JSONArray array=new JSONArray();
        // index=0
        JSONObject obj1=new JSONObject();
        obj1.put("estimatedTime", sumTime-usedTime);
        obj1.put("usedTime", this.taskDao.getUsedTimeByChecklistId(listId));
        obj1.put("unfinishedTask", this.taskDao.getCountByChecklistIdAndFlag(listId,0));
        obj1.put("finishedTask", this.taskDao.getCountByChecklistIdAndFlag(listId,1));
        array.add(obj1);
        // 任务详情列表
        List<Task> tasks=this.taskDao.findByChecklistId(listId);
        for(Task task:tasks){
            JSONObject obj=new JSONObject();
            obj.put("id",task.getId());
            obj.put("title",task.getTitle());
            obj.put("count",task.getCount());
            obj.put("flag",task.getFlag());
            obj.put("priority",task.getPriority());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if(task.getCreateDate()!=null){ obj.put("createDate",sdf.format(task.getCreateDate())); }
            if(task.getExpireDate()!=null){obj.put("expireDate",sdf.format(task.getExpireDate()));}
            SimpleDateFormat sdf2= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if(task.getStartDatetime()!=null){ obj.put("startDatetime",sdf2.format(task.getStartDatetime()));}
            if(task.getCompleteDatetime()!=null){obj.put("completeDatetime",sdf2.format(task.getCompleteDatetime()));}
            obj.put("useTime",task.getUseTime());
            obj.put("repeat",task.getRepeat());
            obj.put("remark",task.getRemark());
            array.add(obj);
        }
        response.getWriter().append(array.toString());
    }


    /**
     * 清单管理，获取清单列表
     */
    @RequestMapping("/getTaskListsTaskByUserId")
    @ResponseBody
    public void getUserDetailSetup(HttpServletRequest request,HttpServletResponse response) throws IOException {
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        Long userId=Long.parseLong(request.getParameter("userId"));
        List<TaskList> taskLists=this.taskListDao.findByUserId(userId);
        JSONArray array=new JSONArray();
        for(TaskList list:taskLists){
            JSONObject object=new JSONObject();
            object.put("id",list.getId());
            object.put("title",list.getTitle());
            object.put("colorId",list.getColorId());
            array.add(object);
        }
        response.getWriter().append(array.toString());
    }

    /**
     * 删除清单接口
     */
    @RequestMapping("/deleteTaskListById")
    @ResponseBody
    public void deleteTaskListById(HttpServletRequest request,HttpServletResponse response) throws IOException {
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        Long listId=Long.parseLong(request.getParameter("id"));
        //删除清单
        this.taskListDao.delete(listId);
        //删除该清单下的任务
        this.taskDao.deleteByChecklistId(listId);
        response.getWriter().append("success");
    }
}
