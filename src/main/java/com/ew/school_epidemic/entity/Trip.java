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
@ApiModel(value = "Trip对象", description = "行程")
public class Trip implements Serializable {

    private static final long serialVersionUID= 1576506398678855487L;

    private Integer tripid;

    /**
     * 账号
     */
    private String username;

    /**
     * 姓名
     */
    private String name;

    /**
     * 时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate time;

    /**
     * 行程码
     */
    private String tripcode;

    /**
     * 健康码
     */
    private String healthcode;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate addtime;


}
