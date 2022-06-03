package com.ew.school_epidemic.mapper;

import com.ew.school_epidemic.entity.History;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ew
 * @since 2022-02-23
 */
@Mapper
@Repository
public interface HistoryMapper extends BaseMapper<History> {
    //保存
    void saveHistory(History history);

    //更新
    void updateHistory(History history);

    //查找 日期相同的
    List<History> findHistory(History history);

    //查找今日数据
    History findToday();

    //返回每天历史累计数据
    List<History> findEachDayTotal();

    //返回每天历史增加数据
    List<History> findEachDayAdd();
}
