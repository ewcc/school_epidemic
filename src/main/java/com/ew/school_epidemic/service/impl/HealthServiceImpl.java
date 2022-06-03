package com.ew.school_epidemic.service.impl;

import com.ew.school_epidemic.entity.Health;
import com.ew.school_epidemic.mapper.HealthMapper;
import com.ew.school_epidemic.service.HealthService;
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
public class HealthServiceImpl extends ServiceImpl<HealthMapper, Health> implements HealthService {

}
