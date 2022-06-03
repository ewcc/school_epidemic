package com.ew.school_epidemic.service.impl;

import com.ew.school_epidemic.entity.Student;
import com.ew.school_epidemic.mapper.StudentMapper;
import com.ew.school_epidemic.service.StudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ew
 * @since 2022-02-07
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

}
