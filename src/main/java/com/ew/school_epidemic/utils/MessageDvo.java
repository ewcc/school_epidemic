package com.ew.school_epidemic.utils;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ew
 * @date 2022/3/8 15:39
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MessageDvo {
    private Integer code;
    private String msg;
    private Map<String, Object> extend=new HashMap<>();
}
