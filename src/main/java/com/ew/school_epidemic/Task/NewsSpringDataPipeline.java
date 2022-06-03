package com.ew.school_epidemic.Task;


import com.ew.school_epidemic.entity.Hot;
import com.ew.school_epidemic.service.HotService;
import com.ew.school_epidemic.service.impl.HotServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Map;

/**
 * @author ew
 * @since 2022-02-07
 */
@Component
public class NewsSpringDataPipeline implements Pipeline {

    @Autowired
    private HotServiceImpl hotService;

    @Override
    public void process(ResultItems resultItems, Task task) {
        Map<String, Object> all = resultItems.getAll();

        for (Map.Entry<String, Object> entry : all.entrySet()) {
            String key = entry.getKey();
            Hot hot = resultItems.get(key);
            this.hotService.saveHot(hot);
        }
    }
}