package com.ew.school_epidemic.mapper;

import com.ew.school_epidemic.entity.Details;
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
public interface DetailsMapper extends BaseMapper<Details> {
    //存储
    void saveDetails(Details details);

    //更新
    void updateDetails(Details details);

    //查找(省份和市名相同的)
    List<Details> findDetails(Details details);

    //查找省
    List<String> findProvince();

    //查找每个省的确诊人数
    List<Integer> findProvinceValue();

    //查找城市
    List<String> findCity();

    //查找每个城市的确诊人数
    List<Long> findCityValue();
}
