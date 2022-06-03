package com.ew.school_epidemic.entity;

import java.io.Serializable;

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
 * @since 2022-02-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "Role对象", description = "角色")
public class Role implements Serializable {

    private static final long serialVersionUID= -4665699401443827252L;

    @ApiModelProperty("角色id")
    private Integer roleid;
    @ApiModelProperty("角色名")
    private String rolename;
    @ApiModelProperty("角色权限级别；user,admin,super")
    private String rolecode;
    @ApiModelProperty("状态")
    private String state;


}
