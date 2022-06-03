package com.ew.school_epidemic.Task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ew.school_epidemic.entity.Area;
import com.ew.school_epidemic.service.impl.AreaServiceImpl;
import com.ew.school_epidemic.utils.RiskAreaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.selector.Json;
import us.codecraft.webmagic.utils.HttpConstant;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ew
 * @date 2022/3/21 13:52
 */
@Component
public class AreaProcesser  {
    private Area area;
    private Map map;
    @Autowired
    private AreaServiceImpl areaService;

    @Scheduled(initialDelay = 1000,fixedDelay = 12*60*60*1000)
    public void process(){
        map=new HashMap<String,Area>();
//        Map map1=new HashMap<String,String>();
        //得到风险地区JSON数据
        String risk = new RiskAreaUtil().get_risk();
        JSONObject jsonObject = JSON.parseObject(risk);
        JSONObject data = jsonObject.getJSONObject("data");
        String end_update_time = data.get("end_update_time").toString();
        int YEAR = Integer.parseInt(end_update_time.substring(0, 4));
        int MONTH = Integer.parseInt(end_update_time.substring(5, 7));
        int DAY = Integer.parseInt(end_update_time.substring(8, 10));
        //拿到高风险地区数据
        JSONArray hightlist = data.getJSONArray("highlist");
        //拿到中风险地区数据
        JSONArray middlelist = data.getJSONArray("middlelist");
        for (int i=0;i<hightlist.size();i++){
            JSONObject jsonObject1 = hightlist.getJSONObject(i);
            Object area_name = jsonObject1.get("area_name");
            Object communitys = jsonObject1.get("communitys");
            String name=area_name.toString();
            LocalDate times = LocalDate.of(YEAR,MONTH,DAY);
            QueryWrapper queryWrapper=new QueryWrapper();
            queryWrapper.eq("time",times);
            queryWrapper.eq("areaname",name);
            queryWrapper.last("LIMIT 1");
            Area one = areaService.getOne(queryWrapper);
            if (one!=null){
            }else {
                area=new Area();
                area.setAreaname(name);
                area.setArealevel("3");
                area.setTime(times);
                areaService.save(area);
            }
        }
        for (int j=map.size();j<middlelist.size();j++){
            JSONObject jsonObject1 = middlelist.getJSONObject(j);
            Object area_name = jsonObject1.get("area_name");
            Object communitys = jsonObject1.get("communitys");
            String name=area_name.toString();
            LocalDate times = LocalDate.of(YEAR,MONTH,DAY);
            QueryWrapper queryWrapper=new QueryWrapper();
            queryWrapper.eq("time",times);
            queryWrapper.eq("areaname",name);
            queryWrapper.last("LIMIT 1");
            Area one = areaService.getOne(queryWrapper);
            if (one!=null){
            }else {
                area=new Area();
                area.setAreaname(name);
                area.setArealevel("2");
                area.setTime(times);
                areaService.save(area);
            }
        }
    }
}
