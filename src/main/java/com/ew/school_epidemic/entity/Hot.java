package com.ew.school_epidemic.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

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
public class Hot implements Serializable {

    private static final long serialVersionUID= 3339194597478297024L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String dt;

    private String content;


}
