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
@ApiModel(value = "Health对象", description = "健康检测")
public class Health implements Serializable {

    private static final long serialVersionUID= -1592433768690850275L;

    private Integer healthid;

    /**
     * 账号
     */
    private String username;

    /**
     * 姓名
     */
    private String name;

    /**
     * 检测时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate testtime;

    /**
     * 温度
     */
    private String temperature;

    /**
     * 检测状态;0为正常；1为隔离,2为确诊
     */
    private String state;

    /**
     * 上传时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate time;


}
