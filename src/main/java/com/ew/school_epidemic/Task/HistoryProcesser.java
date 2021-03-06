package com.ew.school_epidemic.Task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ew.school_epidemic.entity.History;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.selector.Json;

import java.util.UUID;

/**
 * @author ew
 * @since 2022-02-07
 */
@Component
public class HistoryProcesser implements PageProcessor {


    @Override
    public void process(Page page) {
        if(page!=null)
        {
            saveHistory(page);
        }
    }

    private void saveHistory(Page page) {
        Json json = page.getJson();
        JSONObject jsonObject = JSON.parseObject(String.valueOf(json));

        //获取key为data的json对象
        JSONObject data = jsonObject.getJSONObject("data");
        //获取key为chinaDayAddList的json对象
        JSONArray chinaDayAddList = data.getJSONArray("chinaDayAddList");
        //获取key为chinaDayList的json对象
        JSONArray chinaDayList = data.getJSONArray("chinaDayList");
        for(int dateNum=0;dateNum<chinaDayAddList.size();dateNum++)
        {
            History history = new History();
            JSONObject dayAddInfo = chinaDayAddList.getJSONObject(dateNum);
            //两个list中一个是从1月13号开始,另一个是从1月20号开始
            int dateNumforDay=dateNum+7;
            //防止索引越界,五月一号腾讯服务器那边出了一次bug
            if(dateNumforDay>=chinaDayList.size())
            {
                dateNumforDay=chinaDayList.size()-1;
            }
            JSONObject dayInfo = chinaDayList.getJSONObject(dateNumforDay);

            Object dateTime = dayAddInfo.get("date");
            Object y = dayAddInfo.get("y");
            Object confirm_add = dayAddInfo.get("confirm");//当日新增确诊
            Object heal_add = dayAddInfo.get("heal");//当日新增治愈
            Object dead_add = dayAddInfo.get("dead");//当日新增死亡
            Object suspect_add = dayAddInfo.get("suspect");//当日新增疑似

            Object confirm = dayInfo.get("confirm");//当日累计确诊
            Object heal = dayInfo.get("heal");//当日累计治愈
            Object dead = dayInfo.get("dead");//当日累计死亡
            Object suspect = dayInfo.get("suspect");//当日剩余疑似

            Integer confirm_add1 = Integer.valueOf(String.valueOf(confirm_add));
            Integer heal_add1 = Integer.valueOf(String.valueOf(heal_add));
            Integer dead_add1 = Integer.valueOf(String.valueOf(dead_add));
            Integer suspect_add1 = Integer.valueOf(String.valueOf(suspect_add));

            Integer confirm1 = Integer.valueOf(String.valueOf(confirm));
            Integer heal1 = Integer.valueOf(String.valueOf(heal));
            Integer dead1 = Integer.valueOf(String.valueOf(dead));
            Integer suspect1 = Integer.valueOf(String.valueOf(suspect));

            history.setConfirm(confirm1);
            history.setConfirmAdd(confirm_add1);
            history.setDead(dead1);
            history.setDeadAdd(dead_add1);
            history.setHeal(heal1);
            history.setHealAdd(heal_add1);
            history.setSuspect(suspect1);
            history.setSuspectAdd(suspect_add1);
            history.setDs(y.toString()+"."+dateTime.toString());
            //保存结果
            UUID uuid = UUID.randomUUID();
            page.putField("history"+uuid,history);
        }
    }
    private  Site site=Site.me()
            .setCharset("utf-8")//设置编码
            .setTimeOut(10*1000)//超时时间
            .setRetrySleepTime(3000)//重试的间隔时间
            .setRetryTimes(3);//重试的次数
    @Override
    public Site getSite() {
        return site;
    }

    @Autowired
    private HistorySpringDataPipeline historySpringDataPipeline;

    @Scheduled(initialDelay = 1000,fixedDelay = 12*60 *60* 1000)
    public void process(){
        Spider.create(new HistoryProcesser())
                .addUrl("https://view.inews.qq.com/g2/getOnsInfo?name=disease_other")
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(1000000)))
                .thread(10)
                .addPipeline(historySpringDataPipeline)
                .run();
    }


}
