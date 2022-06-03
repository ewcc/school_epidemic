package com.ew.school_epidemic.service.impl;

import com.ew.school_epidemic.entity.User;
import com.ew.school_epidemic.mapper.UserMapper;
import com.ew.school_epidemic.service.UserService;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
