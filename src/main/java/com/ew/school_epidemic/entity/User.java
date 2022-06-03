package com.ew.school_epidemic.entity;

import java.io.Serializable;
import java.util.Collection;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


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
@ApiModel(value = "User对象", description = "用户")
public class User implements Serializable {

    private static final long serialVersionUID = 510136418747157646L;

    @ApiModelProperty("用户id")
    private Integer userid;


    /**
     * 账号
     */
    @ApiModelProperty("账号")
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty("密码")
    private String password;

    /**
     * 姓名
     */
    @ApiModelProperty("姓名")
    private String name;



}
