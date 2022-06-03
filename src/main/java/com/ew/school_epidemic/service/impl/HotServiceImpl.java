package com.ew.school_epidemic.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ew.school_epidemic.entity.Hot;
import com.ew.school_epidemic.mapper.HotMapper;
import com.ew.school_epidemic.service.HotService;
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
public class HotServiceImpl  implements HotService {
    @Autowired
    HotMapper hotMapper;

    @Override
    public void saveHot(Hot hot) {
        hotMapper.saveHot(hot);
    }

    @Override
    public List<Hot> findTopHot20() {
        return hotMapper.findTopHot20();
    }

    @Override
    public int insert(Hot entity) {
        return hotMapper.insert(entity);
    }

    @Override
    public int deleteById(Serializable id) {
        return 0;
    }

    @Override
    public int deleteByMap(Map<String, Object> columnMap) {
        return hotMapper.deleteByMap(columnMap);
    }

    @Override
    public int delete(Wrapper<Hot> wrapper) {
        return 0;
    }

    @Override
    public int deleteBatchIds(Collection<? extends Serializable> idList) {
        return hotMapper.deleteBatchIds(idList);
    }

    @Override
    public int updateById(Hot entity) {
        return hotMapper.updateById(entity);
    }

    @Override
    public int update(Hot entity, Wrapper<Hot> updateWrapper) {
        return 0;
    }

    @Override
    public Hot selectById(Serializable id) {
        return null;
    }

    @Override
    public List<Hot> selectBatchIds(Collection<? extends Serializable> idList) {
        return hotMapper.selectBatchIds(idList);
    }

    @Override
    public List<Hot> selectByMap(Map<String, Object> columnMap) {
        return hotMapper.selectByMap(columnMap);
    }

    @Override
    public Hot selectOne(Wrapper<Hot> queryWrapper) {
        return null;
    }

    @Override
    public Integer selectCount(Wrapper<Hot> queryWrapper) {
        return null;
    }

    @Override
    public List<Hot> selectList(Wrapper<Hot> queryWrapper) {
        return null;
    }

    @Override
    public List<Map<String, Object>> selectMaps(Wrapper<Hot> queryWrapper) {
        return null;
    }

    @Override
    public List<Object> selectObjs(Wrapper<Hot> queryWrapper) {
        return null;
    }

    @Override
    public <E extends IPage<Hot>> E selectPage(E page, Wrapper<Hot> queryWrapper) {
        return null;
    }

    @Override
    public <E extends IPage<Map<String, Object>>> E selectMapsPage(E page, Wrapper<Hot> queryWrapper) {
        return null;
    }
}
