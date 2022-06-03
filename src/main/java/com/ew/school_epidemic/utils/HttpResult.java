package com.ew.school_epidemic.utils;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data                              //get，set
@NoArgsConstructor                //无参构造
@AllArgsConstructor                //有参构造
public class HttpResult<T> {

    /* 服务器成功返回用户请求数据的状态码 */
    public static Integer SUCCESS__CODE = 200;
    /* 服务器成功返回用户请求数据的提示信息 */
    public static String SUCCESS__MSG = "ok";

    public static Integer ERROR_CODE = 500;

    /* 状态码 */
    private Integer code;

    /* 成功返回的数据 */
    private T data;

    /* 提示信息 */
    private String msg;


}
