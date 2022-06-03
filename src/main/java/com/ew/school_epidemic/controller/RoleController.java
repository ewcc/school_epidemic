package com.ew.school_epidemic.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ew.school_epidemic.entity.Role;
import com.ew.school_epidemic.entity.User;
import com.ew.school_epidemic.service.impl.RoleServiceImpl;
import com.ew.school_epidemic.service.impl.UserServiceImpl;
import com.ew.school_epidemic.utils.MD5Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ew
 * @since 2022-02-23
 */
@Controller
@RequestMapping("/role")
@Api(description = "管理员管理")
public class RoleController {
    @Autowired
    RoleServiceImpl roleServiceImpl;
    @Autowired
    UserServiceImpl userServiceImpl;
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ApiOperation(value = "管理员列表",notes = "管理员列表",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo",value = "当前页",dataType = "int" ),
            @ApiImplicitParam(name = "pageSize", value = "每页大小",dataType = "int")
    })
    public String  list(@RequestParam(value = "pageNo",required =false,defaultValue = "1")int pageNo,
                        @RequestParam(value = "pageSize",required =false,defaultValue = "10")int pageSize
            ,Model model){
        QueryWrapper<Role> queryWrapper=new QueryWrapper();
        queryWrapper.ne("rolecode","user");
        Page<Role> rolePage=new Page<>(pageNo,pageSize);
        Page<Role> iPage = roleServiceImpl.page(rolePage, queryWrapper);
        model.addAttribute("list",iPage);
        return "role_list";
    }
    @RequestMapping("/toAdd")
    public String toAdd(){
        return "role_add";
    }
    @RequestMapping("/toUpdate/{name}")
    public String toUpdate(@PathVariable("name")String name,Model model){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("rolename",name);
        Role one = roleServiceImpl.getOne(queryWrapper);
        model.addAttribute("role",one);
        return "role_update";
    }
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ApiOperation(value = "添加管理员",notes = "向数据库添加一个管理员",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(name = "password", value = "密码", paramType = "query", dataType = "String", required = true)
    })
    public String add(@RequestParam("username")String username, @RequestParam("password")String password,Model model) throws Exception {
        User user=new User();
        user.setUsername(username);
        user.setPassword(password);
        QueryWrapper<Role> queryWrapper_admin=new QueryWrapper();
        QueryWrapper<User> queryWrapper_user=new QueryWrapper<>();
        if(user != null){
            String s = MD5Util.md5Encode(user.getPassword());
            user.setPassword(s);
            user.setName("管理员");
            //先查询user里有没有这个人
            queryWrapper_user.eq("username",user.getUsername());
            User one = userServiceImpl.getOne(queryWrapper_user);
            if (one==null){
                //如果没有就加入
                boolean save = userServiceImpl.save(user);
                if(save!=false){
                    //向角色表中加入
                    Role role=new Role();
                    role.setRolename(user.getUsername());
                    role.setRolecode("admin");
                    boolean save1 = roleServiceImpl.save(role);
                    if (save1==true){
                        model.addAttribute("msg","添加管理员成功");
                        return "redirect:/role/list";
                    }else {
                        model.addAttribute("msg","添加管理员失败");
                        return "redirect:/role/list";
                    }
                }
            }else {
                //如果user表有，那么角色表就有，就看权限是不是admin
                queryWrapper_admin.eq("rolename",one.getUsername());
                Role one1 = roleServiceImpl.getOne(queryWrapper_admin);
                if(one1.getRolecode()!="admin"){
                    one1.setRolecode("admin");
                    boolean save = roleServiceImpl.save(one1);
                    if (save==true){
                        model.addAttribute("msg","添加管理员成功");
                        return "redirect:/role/list";
                    }else {
                        model.addAttribute("msg","添加管理员失败");
                        return "redirect:/role/list";
                    }
                }
            }
        }else {
            model.addAttribute("msg", "未输入用户信息");
            return "role_list";
        }

        return  "";
    }
    @RequestMapping(value="/delete/{id}")
    @ApiOperation(value = "删除管理员",notes = "删除数据库中的记录",httpMethod = "DELETE")
    public String delete(@PathVariable("id") Integer id, Model model){
        QueryWrapper<Role> queryWrapper=new QueryWrapper();
        queryWrapper.eq("roleid",id);
        Role one = roleServiceImpl.getOne(queryWrapper);
        QueryWrapper<User> queryWrapper1=new QueryWrapper();
        queryWrapper1.eq("username",one.getRolename());
        User one1 = userServiceImpl.getOne(queryWrapper1);
        if(one!=null&&one1!=null){
            userServiceImpl.remove(queryWrapper1);
            roleServiceImpl.remove(queryWrapper);
        }else {
            model.addAttribute("msg","删除失败！");
            return "role_list";
        }
        return "role_list";
    }
    @ApiOperation(value = "更改管理员信息",notes = "更改数据库管理员信息",httpMethod = "PUT")
    @RequestMapping(value = "/update")
    public  String  update(Role role,Model model){
        UpdateWrapper updateWrapper=new UpdateWrapper();
        updateWrapper.set("rolecode",role.getRolecode());
        updateWrapper.set("state",role.getState());
        updateWrapper.eq("rolename",role.getRolename());
        boolean update = roleServiceImpl.update(updateWrapper);
        if(update==true){
            model.addAttribute("msg","更新成功");
        }else{
            model.addAttribute("msg","更新失败");
        }
        return "redirect:/role/list";
    }

    @RequestMapping("/getAdminByName/{username}/{pageSize}")
    @ApiOperation(value = "根据名字模糊查询管理员",notes = "查询数据库中的管理员",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username",value = "用户名",dataType = "String"),
            @ApiImplicitParam(name = "pageNo",value = "当前页",dataType = "int"),
            @ApiImplicitParam(name = "pageSize",value = "每页大小",dataType = "int")
    })
    public String getAdminByName(@PathVariable("username")String username,
                                 @RequestParam(value = "pageNo",required =false,defaultValue = "1")Integer pageNo,
                                 @PathVariable("pageSize")Integer pageSize,Model model)
    {QueryWrapper<Role> queryWrapper = new QueryWrapper();
    if(pageSize==null){
        pageSize=10;
        Page<Role> page = new Page<>(pageNo,pageSize);
        queryWrapper.like("rolename",username);
        queryWrapper.ne("rolecode","user");
        Page<Role> roleIdPage = roleServiceImpl.page(page,queryWrapper);
        model.addAttribute("list",roleIdPage);
        if(roleIdPage!=null){
            model.addAttribute("msg","查询成功");
        }else {
            model.addAttribute("msg","查询失败");
        }
        return "role_list";
    }else {
        Page<Role> page = new Page<>(pageNo,pageSize);
        queryWrapper.like("rolename",username);
        queryWrapper.ne("rolecode","user");
        Page<Role> roleIdPage = roleServiceImpl.page(page,queryWrapper);
        model.addAttribute("list",roleIdPage);
        if(roleIdPage!=null){
            model.addAttribute("msg","查询成功");
        }else {
            model.addAttribute("msg","查询失败");
        }
        return "role_list";
    }

    }

}

