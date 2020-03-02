package com.controller;

import com.dao.AppTimeDao;
import com.entity.AppTime;
import com.entity.User;
import com.util.JSONUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Controller
@RequestMapping("/appTime")
public class AppTimeController {
    private final Logger LOGGER= LoggerFactory.getLogger(AppTimeController.class);
    @Autowired
    private AppTimeDao appTimeDao;

    /**
     *  保存用户今日app使用时间
     */
    @RequestMapping("/saveAppTimes")
    @ResponseBody
    public void saveAppTimes(HttpServletRequest request,HttpServletResponse response) throws IOException {
        InputStream is=request.getInputStream();
        BufferedReader br=new BufferedReader(new InputStreamReader(is));
        String result=br.readLine();
        JSONArray jsonArray=JSONArray.fromObject(result);
        List<AppTime> list=(List)JSONArray.toCollection(jsonArray,AppTime.class);
        if(list.size()!=0){
            for(AppTime appTime:list){
                AppTime appTime1=this.appTimeDao.findByUserIdAndCreateWeek(appTime.getUserId(),appTime.getCreateWeek());
                if(appTime1!=null){
                    this.appTimeDao.delete(appTime1);
                }
            }

            this.appTimeDao.save(list);
            LOGGER.info("saveAppTimes:{}",list.toString());
        }
    }

    /**
     *  获得用户某个App的近期使用折线图
     */
    @RequestMapping("/getAppTimesByUserId")
    @ResponseBody
    public void getAppTimesByUserId(HttpServletRequest request,HttpServletResponse response) throws IOException {
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        Long userId=Long.parseLong(request.getParameter("userId"));
        String appName=request.getParameter("appName");
        List<AppTime> appTimes=this.appTimeDao.findByUserIdAndAppNameOrderByCreateDateAsc(userId,appName);
        //将List转为JSONArray
        JSONArray jsonArray=JSONArray.fromObject(appTimes);
        response.getWriter().append(jsonArray.toString());
    }
}
