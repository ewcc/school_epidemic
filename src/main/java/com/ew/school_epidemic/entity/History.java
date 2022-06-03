package com.ew.school_epidemic.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 
 * </p>
 *
 * @author ew
 * @since 2022-02-23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "History对象", description = "历史数据")
public class History implements Serializable {

    private static final long serialVersionUID= -5211083802724035946L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 日期
     */
    private String ds;

    /**
     * 累计确诊
     */
    private Integer confirm;

    /**
     * 当日新增确诊
     */
    private Integer confirmAdd;

    /**
     * 剩余疑似
     */
    private Integer suspect;

    /**
     * 当日新增疑似
     */
    private Integer suspectAdd;

    /**
     * 累计治愈
     */
    private Integer heal;

    /**
     * 今日新增治愈
     */
    private Integer healAdd;

    /**
     * 累计死亡
     */
    private Integer dead;

    /**
     * 当日新增死亡
     */
    private Integer deadAdd;


}
