package com.ew.school_epidemic.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ew.school_epidemic.entity.Details;
import com.ew.school_epidemic.mapper.DetailsMapper;
import com.ew.school_epidemic.service.DetailsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ew
 * @since 2022-02-23
 */
@Service
public class DetailsServiceImpl extends ServiceImpl<DetailsMapper,Details> implements DetailsService {

}
