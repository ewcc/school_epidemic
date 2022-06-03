package com.ew.school_epidemic.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ew.school_epidemic.entity.Role;
import com.ew.school_epidemic.entity.Student;
import com.ew.school_epidemic.entity.User;
import com.ew.school_epidemic.service.impl.RoleServiceImpl;
import com.ew.school_epidemic.service.impl.StudentServiceImpl;
import com.ew.school_epidemic.service.impl.UserServiceImpl;
import com.ew.school_epidemic.utils.MD5Util;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ew
 * @since 2022-02-07
 */
@Controller
@RequestMapping("/student")
public class StudentController {
    @Autowired
    RoleServiceImpl roleService;
    @Autowired
    StudentServiceImpl studentService;
    @Autowired
    UserServiceImpl userService;
    @RequestMapping("/list")
    @ApiOperation(value = "学生列表",notes = "学生列表",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo",value = "当前页",dataType = "int" ),
            @ApiImplicitParam(name = "pageSize", value = "每页大小",dataType = "int")
    })
    public String  list(@RequestParam(value = "pageNo",required =false
            ,defaultValue = "1")int pageNo,
                        @RequestParam(value = "pageSize",required =false
                                ,defaultValue = "10")int pageSize
            , Model model){
        QueryWrapper<Student> queryWrapper=new QueryWrapper<>();
        queryWrapper.ne("studentid",0);
        Page<Student> studentPage=new Page<>(pageNo,pageSize);
        Page<Student> page = studentService.page(studentPage, queryWrapper);
        model.addAttribute("list",page);
        return "student_list";
    }
    @RequestMapping("/toAdd")
    public String toAdd(){
        return "student_add";
    }
    @RequestMapping("/toUpdate/{name}")
    public String toUpdate(@PathVariable("name")String name, Model model){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("username",name);
        Student one = studentService.getOne(queryWrapper);
        model.addAttribute("student",one);
        return "student_update";
    }
    @RequestMapping("/update")
    public String update(Student student,Model model){
        UpdateWrapper updateWrapper=new UpdateWrapper();
        updateWrapper.eq("username",student.getUsername());
        updateWrapper.set("name",student.getName());
        updateWrapper.set("gender",student.getGender());
        updateWrapper.set("tel",student.getTel());
        updateWrapper.set("email",student.getEmail());
        updateWrapper.set("brithday",student.getBrithday());
        updateWrapper.set("healthstate",student.getHealthstate());
        updateWrapper.set("college",student.getCollege());
        boolean update = studentService.update(updateWrapper);
        if(update==true){
            return "redirect:/student/list";
        }else {
            model.addAttribute("msg","更新失败");
            return "student_update";
        }

    }
    @RequestMapping("/add")
    public String add(Student student,Model model) throws Exception {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("username",student.getUsername());
        Student one = studentService.getOne(queryWrapper);
        if(one!=null){
            model.addAttribute("msg","用户以存在");
            return "student_add";
        }else {
            Student student1=new Student();
            User user=new User();
            Role role=new Role();
            user.setUsername(student.getUsername());
            String username = student.getUsername();
            String substring = username.substring(6, username.length());
            String s = MD5Util.md5Encode(substring);
            user.setPassword(s);
            user.setName("学生");
            role.setRolename(student.getUsername());
            role.setRolecode("user");
            role.setState("1");
            student1.setUsername(student.getUsername());
            student1.setName(student.getName());
            student1.setGender(student.getGender());
            student1.setEmail(student.getEmail());
            student1.setBrithday(student.getBrithday());
            student1.setCollege(student.getCollege());
            student1.setTel(student.getTel());
            student1.setHealthstate(student.getHealthstate());
            userService.save(user);
            studentService.save(student1);
            roleService.save(role);
        }
        return "redirect:/student/list";
    }

    @RequestMapping("/select")
    @ApiOperation(value = "根据条件查询",notes = "根据条件查询数据库里的内容",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo",value = "当前页",dataType = "int" ,required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页大小",dataType = "int",required = false),
            @ApiImplicitParam(name = "username",value = "用户名",dataType = "String",required = false),
            @ApiImplicitParam(name = "name" ,value="姓名",dataType = "String",required = false),
            @ApiImplicitParam(name = "healthstate",value = "健康状态",dataType = "String",required = false),
    })
    public String selectMore(@RequestParam(name = "pageNo",required = false,defaultValue = "1") int pageNo,
                             @RequestParam(name = "pageSize",required = false,defaultValue = "10") int pageSize,
                             @RequestParam(name = "username",required = false) String username,
                             @RequestParam(name = "name",required = false) String name,
                             @RequestParam(name = "healthState",required = false) String healthState,Model model){
        QueryWrapper<Student> queryWrapper=new QueryWrapper();
        System.out.println("hh"+healthState);
        if(username!=null&&username!=""){
            queryWrapper.like("username",username);
        }
        if (healthState!=null&&healthState!=""){
            queryWrapper.eq("healthstate",healthState);
        }
        if (name!=null&&name!=""){
            queryWrapper.like("name",name);
        }
        Page<Student> page=new Page(pageNo,pageSize);
        Page<Student> page1 = studentService.page(page, queryWrapper);
        model.addAttribute("list",page1);

        return "student_list";
    }
    @RequestMapping(value="/delete/{id}")
    @ApiOperation(value = "删除学生",notes = "删除数据库中的记录",httpMethod = "DELETE")
    public String delete(@PathVariable("id") Integer id, Model model){
        QueryWrapper<Student> queryWrapper=new QueryWrapper();
        queryWrapper.eq("studentid",id);
        Student one = studentService.getOne(queryWrapper);
        QueryWrapper<User> queryWrapper1=new QueryWrapper();
        queryWrapper1.eq("username",one.getUsername());
        User one1 = userService.getOne(queryWrapper1);
        if(one!=null&&one1!=null){
            userService.remove(queryWrapper1);
            studentService.remove(queryWrapper);
        }else {
            model.addAttribute("msg","删除失败！");
            return "student_list";
        }
        return "student_list";
    }
}

