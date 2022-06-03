package com.ew.school_epidemic.entity;

import java.time.LocalDate;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ew.school_epidemic.config.DateConfig;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Value;
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
@ApiModel(value = "Student对象", description = "学生")
public class Student implements Serializable {

    private static final long serialVersionUID= -8668889019386628912L;

    @ExcelIgnore
    private Integer studentid;
    /**
     * 用户名
     */
    @ExcelProperty(index = 0)
    private String username;
    /**
     * 姓名
     */
    @ExcelProperty(index = 1)
    private String name;

    /**
     * 性别；0为女；1为男
     */
    @ExcelProperty(index = 2)
    private String gender;

    /**
     * 电话
     */
    @ExcelProperty(index = 3)
    private String tel;

    /**
     * 邮箱
     */
    @ExcelProperty(index = 4)
    private String email;

    /**
     * 生日
     */
    @ExcelProperty(index = 5,converter = DateConfig.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate brithday;

    /**
     * 健康状态
     */
    @ExcelProperty(index = 6)
    private String healthstate;

    /**
     * 学院
     */
    @ExcelProperty(index = 7)
    private String college;


}
