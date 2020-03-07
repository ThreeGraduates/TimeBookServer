package com.controller;

import com.alibaba.fastjson.JSON;
import com.dao.PhoneTimeDao;
import com.entity.AppTime;
import com.entity.PhoneTime;
import com.util.JSONUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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
@RequestMapping("/phoneTime")
public class PhoneTimeController {
    private final Logger LOGGER= LoggerFactory.getLogger(PhoneTimeController.class);
    @Autowired
    private PhoneTimeDao phoneTimeDao;

    /**
     *  保存用户今日手机使用时间
     */
    @RequestMapping("/saveOne")
    @ResponseBody
    public void saveOne(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject jsonObject=JSONUtils.getJsonObjFromRequest(request);
        //将jsonObject对象解析为java bean对象
        PhoneTime phoneTime=JSON.parseObject(jsonObject.toString(),PhoneTime.class);
        PhoneTime phoneTime1=this.phoneTimeDao.findByUserIdAndCreateWeek(phoneTime.getUserId(),phoneTime.getCreateWeek());
        if(phoneTime1!=null){
            this.phoneTimeDao.delete(phoneTime1);
        }
        this.phoneTimeDao.save(phoneTime);
    }

    /**
     *  获得用户某个App的近期使用折线图
     */
    @RequestMapping("/getPhoneTimesByUserId")
    @ResponseBody
    public void getPhoneTimesByUserId(HttpServletRequest request,HttpServletResponse response) throws IOException {
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        Long userId=Long.parseLong(request.getParameter("userId"));
        List<PhoneTime> phoneTimes=this.phoneTimeDao.findByUserIdOrderByCreateDateAsc(userId);
        JSONArray jsonArray=JSONArray.fromObject(phoneTimes);
        response.getWriter().append(jsonArray.toString());
    }
}
