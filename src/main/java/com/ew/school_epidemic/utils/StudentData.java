package com.ew.school_epidemic.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.ew.school_epidemic.entity.Student;
import com.ew.school_epidemic.service.impl.StudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ew
 * @date 2022/3/9 13:17
 */
public class StudentData {
    @Autowired
    StudentServiceImpl studentService;
    public void saveStudent(MultipartFile file) throws Exception {
        EasyExcel.read(file.getInputStream(), Student.class, new AnalysisEventListener<Student>() {
            //批处理阈值，减轻数据库压力
            private static final  int BATCH_CIUNT=50;
            List<Student> students=new ArrayList<>(BATCH_CIUNT);

            //easyExcel每次从Excel中读取一行数据就会调用一次invoke方法
            @Override
            public void invoke(Student student, AnalysisContext analysisContext) {
                System.out.println("解析到一条数据:{}"+JSON.toJSONString(student));
                Student studentone=new Student();
                studentone.setUsername(student.getUsername());
                studentone.setName(student.getName());
                studentone.setGender(student.getGender());
                studentone.setEmail(student.getEmail());
                studentone.setCollege(student.getCollege());
                studentone.setBrithday(student.getBrithday());
                studentone.setHealthstate(student.getHealthstate());
                studentone.setTel(student.getTel());
                students.add(studentone);
                if(students.size()>=BATCH_CIUNT){
                    studentService.saveBatch(students);
                    students.clear();
                }
            }

            //easyExcel在将Excel表中数据读取完毕后，最终执行此方法
            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                //最后，如果size<BATCH_CIUNT就在这里进行数据的处理
                if(students.size()>0){
                    studentService.saveBatch(students);
                }
            }
        }).sheet().doRead();//sheel()参数指定，默认读取第一张工作表
    }
}
