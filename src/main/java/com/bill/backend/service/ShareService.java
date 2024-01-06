package com.bill.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bill.backend.modules.entity.Share;
import org.springframework.stereotype.Service;

@Service
public interface ShareService extends IService<Share> {
    Share getByKey(String key);
    Integer delete(String key);
    Boolean add(Share share);
}
