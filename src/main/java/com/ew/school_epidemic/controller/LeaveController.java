package com.ew.school_epidemic.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ew.school_epidemic.entity.Leaves;
import com.ew.school_epidemic.entity.Student;
import com.ew.school_epidemic.service.impl.LeaveServiceImpl;
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
@RequestMapping("/leave")
public class LeaveController {
    @Autowired
    StudentServiceImpl studentService;
    @Autowired
    LeaveServiceImpl leaveServiceImpl;

    @RequestMapping("/list")
    @ApiOperation(value = "查询所有请假",notes = "查询数据库中所有的请假",httpMethod = "GET")
    public  String list(@RequestParam(value = "pageNo",required =false,defaultValue = "1")int pageNo,
                        @RequestParam(value = "pageSize",required =false,defaultValue = "10")int pageSize,
                        Model model, HttpSession session){
        Object permission = session.getAttribute("permission");
        String s = permission.toString();
        /**
         * 通过账户数据分辨用户与管理员不同数据显示
         * 用户数据仅限于个人
         */
        if(s.equals("user")){
            String name = session.getAttribute("name").toString();
            QueryWrapper<Leaves> queryWrapper=new QueryWrapper();
            queryWrapper.eq("username",name);
            Page<Leaves> page = new Page<>(pageNo, pageSize);
            Page page1 = leaveServiceImpl.page(page, queryWrapper);
            model.addAttribute("list", page1);
        }else {
            QueryWrapper<Leaves> queryWrapper = new QueryWrapper();
            queryWrapper.ne("username","eee");
            Page<Leaves> page = new Page<>(pageNo, pageSize);
            Page page1 = leaveServiceImpl.page(page, queryWrapper);
            model.addAttribute("list", page1);
        }
        return "leave_list";
    }
    @RequestMapping("/select")
    @ApiOperation(value = "根据条件查询",notes = "根据条件查询数据库里的内容",httpMethod = "GET")
    @ApiImplicitParams({@ApiImplicitParam(name = "username",value = "用户名",dataType = "String"),
            @ApiImplicitParam(name = "pageNo",value = "当前页",dataType = "int"),
            @ApiImplicitParam(name = "pageSize",value = "每页大小",dataType = "int")})
    public String selectMore(@RequestParam(name = "pageNo",required = false,defaultValue = "1") int pageNo,
                             @RequestParam(name = "pageSize",required = false,defaultValue = "10") int pageSize,
                             @RequestParam(name = "username",required = false) String username,
                             @RequestParam(name = "name",required = false) String name,Model model)
    {
        QueryWrapper<Leaves> queryWrapper=new QueryWrapper();
        if(username!=null&&username!=""){
            queryWrapper.like("username",username);
        }
        if (name!= null&&name!=""){
            queryWrapper.like("name",name);
        }
        Page<Leaves> page=new Page(pageNo,pageSize);
        Page<Leaves> page2 = leaveServiceImpl.page(page, queryWrapper);
        model.addAttribute("list",page2);
        return "leave_list";
    }
    @RequestMapping("/toAdd")
    @ApiOperation(value = "去往添加界面",httpMethod = "GET")
    public  String toAdd(HttpSession session,Model model){
        String name = session.getAttribute("name").toString();
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("username",name);
        Student one = studentService.getOne(queryWrapper);
        model.addAttribute("student",one);
        return "leave_add";
    }
    @RequestMapping("/add")
    public String add(Leaves leaves,Model model){
        leaves.setLeaveid(null);
        boolean save = leaveServiceImpl.save(leaves);
        if(save==true){
            return "redirect:/leave/list";
        }else {
            model.addAttribute("msg","添加失败！");
            return "leave_add";
        }
    }
    @RequestMapping("/toUpdate/{name}")
    @ApiOperation(value = "去往更新页面",httpMethod = "GET")
    public String toUpdate(@PathVariable("name")String name, Model model){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("username",name);
        Leaves one = leaveServiceImpl.getOne(queryWrapper);
        model.addAttribute("leave",one);
        return "leave_update";
    }
    @RequestMapping("/update")
    public String update(Leaves leaves,Model model){
        UpdateWrapper updateWrapper=new UpdateWrapper();
        updateWrapper.set("state",leaves.getState());
        updateWrapper.eq("username",leaves.getUsername());
        boolean update = leaveServiceImpl.update(updateWrapper);
        if(update==true){
            return "redirect:/leave/list";
        }else {
            model.addAttribute("msg","更新失败！");
            return "leave_update";
        }

    }
    @RequestMapping("/delete/{id}")
    @ApiOperation(value = "通过id删除请假",httpMethod = "DELETE")
    @ApiImplicitParam(name = "id",value = "请假id",dataType = "int")
    public  String delete(@PathVariable("id") Integer id,Model model){
        QueryWrapper<Leaves> queryWrapper=new QueryWrapper();
        queryWrapper.eq("leaveid",id);
        boolean remove = leaveServiceImpl.remove(queryWrapper);
        if(remove==true){
            model.addAttribute("msg","删除成功！");
        }else {
            model.addAttribute("msg","删除失败!");

        }
        return "leave_list";
    }
}

