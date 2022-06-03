package com.ew.school_epidemic.mapper;

import com.ew.school_epidemic.entity.Hot;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

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
public interface HotMapper extends BaseMapper<Hot> {
    //保存
    void saveHot(Hot hot);

    List<Hot> findTopHot20();
}
