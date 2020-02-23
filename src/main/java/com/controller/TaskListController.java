package com.controller;

import com.dao.TaskListDao;
import com.entity.TaskList;
import com.util.JSONUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

@Controller
@RequestMapping("/taskList")
public class TaskListController {
    private final Logger LOGGER= LoggerFactory.getLogger(TaskListController.class);
    @Autowired
    private TaskListDao taskListDao;

    /**
     * 创建清单接口
     */
    @RequestMapping("/saveTaskList")
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
}
