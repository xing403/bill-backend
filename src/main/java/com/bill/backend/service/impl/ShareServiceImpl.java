package com.bill.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bill.backend.mapper.ShareMapper;
import com.bill.backend.modules.entity.Share;
import com.bill.backend.service.ShareService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ShareServiceImpl extends ServiceImpl<ShareMapper, Share> implements ShareService {

    @Resource
    private ShareMapper shareMapper;

    @Override
    public Share getByKey(String key) {
        QueryWrapper<Share> shareQueryWrapper = new QueryWrapper<>();
        shareQueryWrapper.eq("k", key);

        return shareMapper.selectOne(shareQueryWrapper);
    }

    @Override
    public Integer delete(String key) {
        return shareMapper.delete(new QueryWrapper<Share>().eq("k", key));
    }

    @Override
    public Boolean add(Share share) {
        return this.save(share);
    }
}
