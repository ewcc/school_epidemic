package com.ew.school_epidemic.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ew.school_epidemic.entity.Student;
import com.ew.school_epidemic.entity.Trip;
import com.ew.school_epidemic.service.impl.StudentServiceImpl;
import com.ew.school_epidemic.service.impl.TripServiceImpl;
import com.ew.school_epidemic.utils.DateUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ew
 * @since 2022-02-07
 */
@Controller
@RequestMapping("/trip")
public class TripController {
    @Autowired
    TripServiceImpl tripService;
    @Autowired
    StudentServiceImpl studentService;

    @RequestMapping("/list")
    @ApiOperation(value = "查看所有行程信息",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo",value = "当前页",dataType = "int" ),
            @ApiImplicitParam(name = "pageSize", value = "每页大小",dataType = "int")
    })
    public String list(@RequestParam(value = "pageNo",required =false
            ,defaultValue = "1")int pageNo,
                       @RequestParam(value = "pageSize",required =false
                               ,defaultValue = "20")int pageSize,
                       Model model,HttpSession session){
        Object permission = session.getAttribute("permission");
        String s = permission.toString();
        /**
         * 通过账户数据分辨用户与管理员不同数据显示
         * 用户数据仅限于个人
         */
        if(s.equals("user")){
            String name = session.getAttribute("name").toString();
            QueryWrapper<Trip> queryWrapper=new QueryWrapper();
            queryWrapper.eq("username",name);
            Page<Trip> page = new Page<>(pageNo, pageSize);
            Page page1 = tripService.page(page, queryWrapper);
            model.addAttribute("list", page1);
        }else {
            QueryWrapper<Trip> queryWrapper=new QueryWrapper<>();
            queryWrapper.orderByDesc("tripid");
            Page<Trip> pagea = new Page<>(pageNo,pageSize);
            Page<Trip> page1 = tripService.page(pagea, queryWrapper);
            model.addAttribute("list",page1);
        }
        return "trip_list";
    }
    @RequestMapping("/toAdd")
    @ApiOperation(value = "去往添加界面",httpMethod = "GET")
    public  String toAdd(HttpSession session, Model model){
        String name = session.getAttribute("name").toString();
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("username",name);
        Student one = studentService.getOne(queryWrapper);
        model.addAttribute("student",one);
        return "trip_add";
    }
    @RequestMapping("/add")
    @ApiOperation(value = "添加行程",httpMethod = "POST")
    public String add(Trip trip,HttpServletRequest request,Model model){
        List<MultipartFile> files = ((MultipartHttpServletRequest) request)
                .getFiles("file");
        trip.setTripid(null);
        LocalDate now = LocalDate.now();
        trip.setAddtime(now);
        String dir="D://school_picture//";
        MultipartFile file = null;
        BufferedOutputStream stream = null;
        for (int i = 0; i < files.size(); ++i) {
            file = files.get(i);
            if (!file.isEmpty()) {
                try {
                    //文件名
                    String filename = file.getOriginalFilename();
                    filename= UUID.randomUUID().toString()+".jpg";
                    //保存路径
                    String ds=dir+filename;
                    if(i==0){
                        trip.setTripcode("/school_picture/"+filename);
                    }else {
                        trip.setHealthcode("/school_picture/"+filename);
                    }
                    byte[] bytes = file.getBytes();
                    stream = new BufferedOutputStream(new FileOutputStream(
                            new File(ds)));
                    stream.write(bytes);
                    stream.close();
                    if(i!=0){
                        boolean save = tripService.save(trip);
                    }
                } catch (Exception e) {
                    stream = null;
                    model.addAttribute("msg","You failed to upload "+i+"=>"+e.getMessage());
                    return "trip_add";
                }
            } else {
                model.addAttribute("msg","You failed to upload "+i+"because the file was empty");
                return "trip_add";
            }
        }
        return "redirect:/trip/list";
    }

    @RequestMapping("/toUpdate/{name}")
    @ApiOperation(value = "去往更新页面",httpMethod = "GET")
    public String toUpdate(@PathVariable("name")String name, Model model){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("username",name);
        Trip one = tripService.getOne(queryWrapper);
        model.addAttribute("trip",one);
        return "trip_update";
    }

    public String update(){
        return "redirect:/trip/list";
    }

    @RequestMapping("/delete/{id}")
    @ApiOperation(value = "通过id删除行程",httpMethod = "DELETE")
    @ApiImplicitParam(name = "id",value = "行程id",dataType = "int")
    public  String delete(@PathVariable("id") Integer id,Model model){
        QueryWrapper<Trip> queryWrapper=new QueryWrapper();
        queryWrapper.eq("tripid",id);
        boolean remove = tripService.remove(queryWrapper);
        if(remove==true){
            model.addAttribute("msg","删除成功！");
        }else {
            model.addAttribute("msg","删除失败!");

        }
        return "trip_list";
    }
}

