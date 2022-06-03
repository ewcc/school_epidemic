package com.ew.school_epidemic.controller;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ew.school_epidemic.entity.Details;
import com.ew.school_epidemic.entity.History;
import com.ew.school_epidemic.entity.Hot;
import com.ew.school_epidemic.mapper.DetailsMapper;
import com.ew.school_epidemic.mapper.HistoryMapper;
import com.ew.school_epidemic.mapper.HotMapper;
import com.ew.school_epidemic.service.DetailsService;
import com.ew.school_epidemic.service.HistoryService;
import com.ew.school_epidemic.service.HotService;
import com.ew.school_epidemic.service.impl.DetailsServiceImpl;
import com.ew.school_epidemic.service.impl.HistoryServiceImpl;
import com.ew.school_epidemic.service.impl.HotServiceImpl;
import com.huaban.analysis.jieba.JiebaSegmenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author ew
 * @since 2022-02-07
 */
@Controller
public class IndexController {

    @Autowired
    HistoryServiceImpl historyService;
    @Autowired
    HistoryMapper historyMapper;

    @Autowired
    DetailsServiceImpl detailsService;

    @Autowired
    DetailsMapper detailsMapper;

    @RequestMapping("/time")
    @ResponseBody
    public String getTime()
    {
        String date = DateFormat.getDateTimeInstance().format(new Date());
        return date;
    }


    @RequestMapping("/c1")
    @ResponseBody
    public JSONObject getDateC1()
    {
        History today = historyMapper.findToday();
        JSONObject json = new JSONObject();
        json.put("confirm",today.getConfirm());
        json.put("suspect",today.getSuspect());
        json.put("heal",today.getHeal());
        json.put("dead",today.getDead());
        return json;
    }

    @RequestMapping("/c2")
    @ResponseBody
    public JSONArray c2()
    {
        JSONArray list = new JSONArray();
        //查找省份
        List<String> province = detailsMapper.findProvince();
        //查找省份确诊数
        List<Integer> provinceValue = detailsMapper.findProvinceValue();
        for (int i = 0; i <province.size() ; i++) {
            JSONObject js = new JSONObject();
            js.put("name",province.get(i));
            js.put("value",provinceValue.get(i));
            list.add(js);
        }
        return list;

    }
    
    @RequestMapping("/l1")
    @ResponseBody
    public JSONObject l1()
    {
        //List<History> eachDayTotal = historyService.list();
        List<History> eachDayTotal = historyMapper.findEachDayTotal();
        JSONObject json = new JSONObject();
        List<String> daylist=new ArrayList<>();
        List<Integer> confirmlist=new ArrayList<>();
        List<Integer> heallist=new ArrayList<>();
        List<Integer> deadlist=new ArrayList<>();
        List<Integer> suspectlist=new ArrayList<>();

        for (History history : eachDayTotal) {
            daylist.add(history.getDs());
            confirmlist.add(history.getConfirm());
            heallist.add(history.getHeal());
            deadlist.add(history.getDead());
            suspectlist.add(history.getSuspect());
        }
        json.put("day",daylist);
        json.put("confirm",confirmlist);
        json.put("suspect",suspectlist);
        json.put("heal",heallist);
        json.put("dead",deadlist);
        return json;
    }


    @RequestMapping("/r1")
    @ResponseBody
    public JSONObject r1()
    {
        //List<History> eachDayTotal = historyService.list();
        List<History> eachDayTotal = historyMapper.findEachDayAdd();
        JSONObject json = new JSONObject();
        List<String> daylist=new ArrayList<>();
        List<Integer> confirmlist=new ArrayList<>();
        List<Integer> heallist=new ArrayList<>();
        List<Integer> deadlist=new ArrayList<>();
        List<Integer> suspectlist=new ArrayList<>();

        for (History history : eachDayTotal) {
            daylist.add(history.getDs());
            confirmlist.add(history.getConfirmAdd());
            heallist.add(history.getHealAdd());
            deadlist.add(history.getDeadAdd());
            suspectlist.add(history.getSuspectAdd());
        }

        json.put("day",daylist);
        json.put("confirm",confirmlist);
        json.put("suspect",suspectlist);
        json.put("heal",heallist);
        json.put("dead",deadlist);
        return json;
    }


}
