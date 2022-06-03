package com.ew.school_epidemic.entity;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
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
@ApiModel(value = "function对象", description = "功能")
public class Function implements Serializable {

    private static final long serialVersionUID= -6770418623517983416L;

    private Integer functionid;

    /**
     * 权限标识；v1包含2和3；v2包含3
     */
    private String functionlogo;

    private String functionname;

    private String functionaddress;

    private String functionfather;

    private String state;


}
