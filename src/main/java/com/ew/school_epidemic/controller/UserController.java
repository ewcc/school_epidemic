package com.ew.school_epidemic.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ew.school_epidemic.entity.Role;
import com.ew.school_epidemic.entity.Student;
import com.ew.school_epidemic.entity.User;
import com.ew.school_epidemic.service.impl.RoleServiceImpl;
import com.ew.school_epidemic.service.impl.StudentServiceImpl;
import com.ew.school_epidemic.service.impl.UserServiceImpl;
import com.ew.school_epidemic.utils.HttpResult;
import com.ew.school_epidemic.utils.MD5Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ew
 * @since 2022-02-07
 */
@Controller
@Api(description = "用户登录表")
public class UserController {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    RoleServiceImpl roleService;
    @Autowired
    StudentServiceImpl studentService;

    @RequestMapping(value = "/userLogin")
    @ApiOperation(value = "用户登录",notes = "用户登录",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", paramType = "query", dataType = "String", required = false),
            @ApiImplicitParam(name = "password", value = "密码", paramType = "query", dataType = "String", required = false)
    })
    public String userLogin(@RequestParam("username")String username, @RequestParam("password")String password, Model model, HttpSession session) throws Exception {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("username",username);
        String encode = MD5Util.md5Encode(password);
        queryWrapper.eq("password",encode);
        User user = userService.getOne(queryWrapper);
        if(user!=null){
            QueryWrapper<Role> wrapper=new QueryWrapper<>();
            wrapper.eq("rolename",user.getUsername());
            Role one = roleService.getOne(wrapper);
            session.setAttribute("name",user.getUsername());
            //存储权限
            session.setAttribute("permission",one.getRolecode());
            //设置session过期时间1800秒
            session.setMaxInactiveInterval(30 * 60);
            return "redirect:/main.html";
        }else {
            model.addAttribute("msg","账户或密码错误");
            return "login";
        }

    }

    @RequestMapping("/toPersonal")
    @ApiOperation(value = "去往个人资料页面",httpMethod = "GET")
    public String toPersonal(HttpSession session,Model model){
        Object name = session.getAttribute("name");
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("username",name);
        Student one = studentService.getOne(queryWrapper);
        model.addAttribute("student",one);
        return "/personal";
    }

    @RequestMapping("/updatePersonal")
    @ApiOperation(value = "修改个人资料",httpMethod = "GET")
    public String updatePersonal(Student student, HttpSession session, Model model){
        UpdateWrapper updateWrapper=new UpdateWrapper();
        updateWrapper.eq("username",student.getUsername());
        updateWrapper.set("name",student.getName());
        updateWrapper.set("gender",student.getGender());
        updateWrapper.set("tel",student.getTel());
        updateWrapper.set("email",student.getEmail());
        updateWrapper.set("brithday",student.getBrithday());
        updateWrapper.set("college",student.getCollege());
        boolean update = studentService.update(updateWrapper);
        if(update){
            session.invalidate();
            return "redirect:/login";
        }else {
            model.addAttribute("msg","更新失败！");
            return "/personal";
        }
    }
    @RequestMapping("/toUpdatePassword")
    @ApiOperation(value = "去往修改密码页面",httpMethod = "GET")
    public String toUpdatePassword(){
        return "/update_password";
    }

    @RequestMapping("/password")
    @ApiOperation(value = "修改密码",httpMethod ="GET")
    public  String updatePassword(User user
            ,@RequestParam(value = "newPassword" ,required = true) String newPassword
            ,HttpSession  session,Model model) throws Exception {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("username",user.getUsername());
        User one = userService.getOne(queryWrapper);
        String s = MD5Util.md5Encode(user.getPassword());
        if(s.equals(one.getPassword())){
            UpdateWrapper updateWrapper =new UpdateWrapper();
            updateWrapper.eq("username",user.getUsername());
            String s1 = MD5Util.md5Encode(newPassword);
            updateWrapper.set("password",s1);
            boolean update = userService.update(updateWrapper);
            if(update){
                session.invalidate();
                return "redirect:/login";
            }else {
                model.addAttribute("msg","修改失败！");
                return "/update_password";
            }
        }else {
            model.addAttribute("msg","当前密码输入错误！");
            return "/update_password";
        }

    }

    @RequestMapping("/loginOut")
    @ApiOperation(value = "注销登录",httpMethod ="GET")
    public String loginOut(HttpSession  session){
        session.invalidate();
        return "login";
    }

    @RequestMapping("/toRegister")
    @ApiOperation(value = "去往注册页面",httpMethod ="GET")
    public String toRegister(){
        return "register_user";
    }

    @RequestMapping("/register")
    @ApiOperation(value = "注册用户",notes = "向数据库添加一个学生用户",httpMethod ="POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", paramType = "query", dataType = "String", required = true),
        @ApiImplicitParam(name = "password", value = "密码", paramType = "query", dataType = "String", required = true)}
    )
    public String register(@RequestParam("username")String username,@RequestParam("password")String password,Model model) throws Exception {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("username",username);
        User one = userService.getOne(queryWrapper);
        User user=new User();
        if(one==null){
            String s = MD5Util.md5Encode(password);
            user.setUsername(username);
            user.setPassword(s);
            user.setName("学生");
            userService.save(user);
            Role role1=new Role();
            role1.setRolename(one.getUsername());
            role1.setRolecode("user");
            roleService.save(role1);
            model.addAttribute("msg","注册成功");
        }else {
            model.addAttribute("msg","注册失败，用户已存在！");
        }
        return "login";
    }
}

