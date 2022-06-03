package com.ew.school_epidemic.Task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ew.school_epidemic.entity.Details;
import com.ew.school_epidemic.mapper.DetailsMapper;
import com.ew.school_epidemic.service.DetailsService;
import com.ew.school_epidemic.service.impl.DetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author ew
 * @since 2022-02-07
 */
@Component
public class DetailsSpringDataPipeline implements Pipeline {

    @Autowired
    private DetailsMapper detailsService;
    @Autowired
    private DetailsServiceImpl detailsServiceimpl;
    @Override
    public void process(ResultItems resultItems, Task task) {
        QueryWrapper queryWrapper=new QueryWrapper();
        Map<String, Object> all = resultItems.getAll();
        for (Map.Entry<String, Object> entry : all.entrySet()) {
            String key = entry.getKey();
            Details details = resultItems.get(key);
            String update_times = details.getUpdate_time();
            queryWrapper.eq("update_time",update_times);
            String city = details.getCity();
            queryWrapper.eq("city",city);
//            queryWrapper.last("LIMIT 1");
            Details one = detailsServiceimpl.getOne(queryWrapper);
            if(one!=null){
                continue;
            }else {
                this.detailsService.saveDetails(details);
            }

        }
    }
}