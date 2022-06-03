package com.ew.school_epidemic.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ew.school_epidemic.entity.Health;
import com.ew.school_epidemic.entity.Student;
import com.ew.school_epidemic.service.impl.HealthServiceImpl;
import com.ew.school_epidemic.service.impl.StudentServiceImpl;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ew
 * @since 2022-02-07
 */
@Controller
@RequestMapping("/health")
public class HealthController {
    @Autowired
    HealthServiceImpl healthService;

    @Autowired
    StudentServiceImpl studentService;

    @RequestMapping("/list")
    @ApiOperation(value = "查看所有健康信息",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo",value = "当前页",dataType = "int" ),
            @ApiImplicitParam(name = "pageSize", value = "每页大小",dataType = "int")
    })
    public String list(
            @RequestParam(value = "pageNo",required =false,defaultValue = "1")int pageNo,
            @RequestParam(value = "pageSize",required =false
                    ,defaultValue = "20")int pageSize, Model model
            , HttpSession session){
        Object permission = session.getAttribute("permission");
        String s = permission.toString();
        /**
         * 通过账户数据分辨用户与管理员不同数据显示
         * 用户数据仅限于个人
         */
        if(s.equals("user")){
            String name = session.getAttribute("name").toString();
            QueryWrapper<Health> queryWrapper=new QueryWrapper();
            queryWrapper.eq("username",name);
            Page<Health> page = new Page<>(pageNo, pageSize);
            Page page1 = healthService.page(page, queryWrapper);
            model.addAttribute("list", page1);
        }else {
            QueryWrapper<Health> queryWrapper=new QueryWrapper<>();
            queryWrapper.orderByDesc("healthid");
            Page<Health> pagea = new Page<>(pageNo,pageSize);
            Page<Health> page1 = healthService.page(pagea, queryWrapper);
            model.addAttribute("list",page1);
        }
        return "health_list";
    }

    @RequestMapping("/toAdd")
    @ApiOperation(value = "去往添加界面",httpMethod = "GET")
    public  String toAdd(HttpSession session, Model model){
        String name = session.getAttribute("name").toString();
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("username",name);
        Student one = studentService.getOne(queryWrapper);
        model.addAttribute("student",one);
        return "health_add";
    }

    @RequestMapping("/add")
    public String add(Health health,Model model){
        health.setHealthid(null);
        boolean save = healthService.save(health);
        if(save==true){
            return "redirect:/health/list";
        }else {
            model.addAttribute("msg","添加失败！");
            return "health_add";
        }
    }

    @RequestMapping("/delete/{id}")
    @ApiOperation(value = "通过id删除行程",httpMethod = "DELETE")
    @ApiImplicitParam(name = "id",value = "行程id",dataType = "int")
    public  String delete(@PathVariable("id") Integer id,Model model){
        QueryWrapper<Health> queryWrapper=new QueryWrapper();
        queryWrapper.eq("healthid",id);
        boolean remove = healthService.remove(queryWrapper);
        if(remove==true){
            model.addAttribute("msg","删除成功！");
        }else {
            model.addAttribute("msg","删除失败!");

        }
        return "trip_list";
    }
}

