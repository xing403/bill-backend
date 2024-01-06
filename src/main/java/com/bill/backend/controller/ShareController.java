package com.bill.backend.controller;

import com.bill.backend.modules.BaseResponse;
import com.bill.backend.modules.constant.ErrorCode;
import com.bill.backend.modules.entity.Share;
import com.bill.backend.modules.entity.User;
import com.bill.backend.modules.exception.BusinessException;
import com.bill.backend.service.ShareService;
import com.bill.backend.service.UserService;
import com.bill.backend.utils.ResultUtils;
import com.bill.backend.utils.UUIDUtil;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Api(tags = "share")
@RequestMapping("/share")
public class ShareController {
    @Resource
    private ShareService shareService;

    @Resource
    private UserService userService;

    @PostMapping("/get")
    public BaseResponse<Share> get(@RequestParam("key") String key) {// 所有人访问
        Share share = shareService.getByKey(key);

        if (share == null)
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "分享不存在");

        long time = share.getCreateTime().getTime();
        Integer valid = share.getValid();
        long l = System.currentTimeMillis();

        if (l - time > valid * 24 * 60 * 60 * 1000 && valid != 0)
            throw new BusinessException(ErrorCode.FORBIDDEN_ERROR, "分享已过期");

        return ResultUtils.success(share);
    }
    @PostMapping("/add")
    public BaseResponse<String> add(@RequestHeader("Authorization") String token, @RequestBody Share share) {
        User currentUser = userService.getCurrentUser(token);

        String key = UUIDUtil.get32UUID();

        share.setUserId(currentUser.getId());
        share.setK(key);

        Boolean add = shareService.add(share);

        if(!add)
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "添加失败");

        return ResultUtils.success(key, "添加成功");
    }

    @PostMapping("/delete")
    public BaseResponse<Integer> delete(@RequestHeader("Authorization") String token, @RequestParam("key") String key) {
        User currentUser = userService.getCurrentUser(token);

        Share byKey = shareService.getByKey(key);
        if (byKey == null)
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "分享不存在");

        if (!byKey.getUserId().equals(currentUser.getId()))
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "无权限");


        Integer count = shareService.delete(key);
        return ResultUtils.success(count, "删除成功");
    }
}
