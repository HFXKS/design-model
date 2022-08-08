package com.xu.design.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xu.design.mapper.DesignUserMapper;
import com.xu.design.DO.DesignUserDO;
import com.xu.design.service.DesignUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DesignUserServiceImpl implements DesignUserService {

    @Resource
    private DesignUserMapper designUserMapper;

    @Override
    public Object selectAll() {
        return designUserMapper.selectList(new QueryWrapper<DesignUserDO>()
                .lambda()
                .eq(DesignUserDO::getDeleted, 0));
    }
}
