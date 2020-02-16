package com.controller;

import com.dao.TaskDao;
import com.entity.Task;
import com.util.JSONUtils;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        JSONObject jsonObject=JSONUtils.getJsonObjFromRequest(request);
        String title=jsonObject.getString("title");
        Integer count=jsonObject.getInt("count");
        Integer priority=jsonObject.getInt("priority");
        String expireDate=jsonObject.getString("expireDate");

    }
}
