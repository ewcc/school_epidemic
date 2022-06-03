package com.ew.school_epidemic.entity;

import java.time.LocalDate;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 
 * </p>
 *
 * @author ew
 * @since 2022-02-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "Area对象", description = "风险地区")
public class Area implements Serializable {

    private static final long serialVersionUID= 381862429491664017L;

    private Integer areaid;

    private String areaname;
    /**
     *  1为低风险 2为中风险 3为高风险
     */
    private String arealevel;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate time;


}
