package com.ew.school_epidemic.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ew.school_epidemic.entity.Area;
import com.ew.school_epidemic.entity.Student;
import com.ew.school_epidemic.service.impl.AreaServiceImpl;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ew
 * @since 2022-02-07
 */
@Controller
@RequestMapping("/area")
public class AreaController {
    @Autowired
    AreaServiceImpl areaService;

    @RequestMapping("/list")
    @ApiOperation(value = "查询所有地区",notes = "查询数据库中所有的地区",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo",value = "当前页",dataType = "int" ),
            @ApiImplicitParam(name = "pageSize", value = "每页大小",dataType = "int")
    })
    public String list(@RequestParam(value = "pageNo",required =false,defaultValue = "1")int pageNo,
                       @RequestParam(value = "pageSize",required =false,defaultValue = "10")int pageSize,
                       Model model){
        QueryWrapper<Area> queryWrapper=new QueryWrapper();
        queryWrapper.gt("arealevel","0");
        queryWrapper.eq("time",LocalDate.now().toString());
        queryWrapper.orderByDesc("time");
        Page<Area> page = new Page<>(pageNo,pageSize);
        Page<Area> page1 = areaService.page(page, queryWrapper);
        model.addAttribute("list",page1);
        return "area_list";
    }
    @RequestMapping("/select")
    @ApiOperation(value = "根据条件查询",notes = "根据条件查询数据库里的内容",httpMethod = "GET")
    @ApiImplicitParams({@ApiImplicitParam(name = "leavel",value = "风险等级",dataType = "String"),
            @ApiImplicitParam(name = "pageNo",value = "当前页",dataType = "int"),
            @ApiImplicitParam(name = "pageSize",value = "每页大小",dataType = "int")})
    public String selectMore(@RequestParam(name = "pageNo",required = false,defaultValue = "1") int pageNo,
                             @RequestParam(name = "pageSize",required = false,defaultValue = "10") int pageSize,
                             @RequestParam(name = "leavel",required = false) String leavel,
                             @RequestParam(name = "areaname",required = false)String areaname,Model model)
    {
        QueryWrapper<Area> queryWrapper=new QueryWrapper();
        if(areaname!=null&&areaname!=""){
            queryWrapper.like("areaname",areaname);
        }
        if(leavel!=null&&leavel.equals(0)&&leavel!=""){
            queryWrapper.eq("arealevel",leavel);
        }else {
            queryWrapper.gt("arealevel","0");
        }
        queryWrapper.eq("time",LocalDate.now().toString());
        queryWrapper.orderByDesc("time");
        Page<Area> page=new Page(pageNo,pageSize);
        Page<Area> page2 = areaService.page(page, queryWrapper);
        model.addAttribute("list",page2);
        return "area_list";
    }
    @RequestMapping("/toAdd")
    @ApiOperation(value = "去往添加界面",httpMethod = "GET")
    public  String toAdd(){
        return "area_add";
    }
    @RequestMapping("/add")
    @ApiOperation(value ="添加风险地区",httpMethod = "POST")
    public String add(Area area,Model model){
        QueryWrapper<Area> queryWrapper= new QueryWrapper();
        queryWrapper.eq("areaname",area.getAreaname());
        Area one = areaService.getOne(queryWrapper);
        if(one==null){
            boolean save = areaService.save(area);
            if(save==true){
                model.addAttribute("msg","添加成功");
                return "redirect:/area/list";
            }else {
                model.addAttribute("msg","添加失败");
                return "area_add";
            }
        }else {
            model.addAttribute("msg","添加失败，该地区已存在！");
        }
        return "area_list";
    }
    @RequestMapping("/delete/{id}")
    @ApiOperation(value = "通过id删除地区",httpMethod = "DELETE")
    @ApiImplicitParam(name = "id",value = "地区id",dataType = "int")
    public  String delete(@PathVariable("id") Integer id,Model model){
        QueryWrapper<Area> queryWrapper=new QueryWrapper();
        queryWrapper.eq("areaid",id);
        boolean remove = areaService.remove(queryWrapper);
        if(remove==true){
            model.addAttribute("msg","删除成功！");
        }else {
            model.addAttribute("msg","删除失败!");

        }
        return "area_list";
    }
    @RequestMapping("/toUpdate/{name}")
    @ApiOperation(value = "去往更新页面",httpMethod = "GET")
    public String toUpdate(@PathVariable("name")String name, Model model){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("areaname",name);
        Area one = areaService.getOne(queryWrapper);
        model.addAttribute("area",one);
        return "area_update";
    }
    @RequestMapping("/update")
    @ApiOperation(value = "更新地区",httpMethod = "PUT")
    public String update(Area area,Model model){
        UpdateWrapper updateWrapper=new UpdateWrapper();
        updateWrapper.eq("areaid",area.getAreaid());
        updateWrapper.set("arealevel",area.getArealevel());
        updateWrapper.set("areaname",area.getAreaname());
        updateWrapper.set("time",area.getTime());
        boolean b = areaService.update(updateWrapper);
        if(b==true){
            model.addAttribute("msg","更新成功");
            return "redirect:/area/list";
        }else {
            model.addAttribute("msg","更新失败！");
        }
        return "area_update";
    }
}

