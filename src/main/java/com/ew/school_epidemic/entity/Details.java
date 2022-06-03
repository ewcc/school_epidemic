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
@ApiModel(value = "Details对象", description = "省市病例数据")
public class Details implements Serializable {

    private static final long serialVersionUID= 5541920024977458955L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 数据最后更新时间
     */
    private String update_time;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 累计确诊
     */
    private Integer confirm;

    /**
     * 新增确诊
     */
    private Integer confirm_add;

    /**
     * 累计治愈
     */
    private Integer heal;

    /**
     * 累计死亡
     */
    private Integer dead;


}
