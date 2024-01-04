package com.bill.backend.controller;

import com.alibaba.fastjson.JSON;
import com.bill.backend.modules.BaseResponse;
import com.bill.backend.modules.constant.ErrorCode;
import com.bill.backend.modules.entity.User;
import com.bill.backend.modules.exception.BusinessException;
import com.bill.backend.utils.FileUtils;
import com.bill.backend.utils.RedisCacheUtils;
import com.bill.backend.utils.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "common")
@RequestMapping("/common")
public class CommonController {

    @Resource
    private RedisCacheUtils redisCacheUtils;

    /**
     * 通用上传请求（单个）
     */
    @ApiOperation("单文件上传")
    @PostMapping("/upload")
    public BaseResponse<Map<String, String>> uploadFile(HttpServletRequest request, @RequestHeader("Authorization") String token, MultipartFile file) {
        User self = JSON.parseObject(JSON.toJSONString(redisCacheUtils.getCache(token)), User.class);
        if (self == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR, "未登录/无效请求");
        }
        String upload = FileUtils.upload(request, file);
        Map<String, String> stringStringMap = new HashMap<>();
        stringStringMap.put("url", upload);
        return ResultUtils.success(stringStringMap, "上传成功");
    }

    /**
     * 单图片上传（单个）
     */
    @ApiOperation("单图片上传")
    @PostMapping("/upload/image")
    public BaseResponse<Map<String, String>> uploadImage(HttpServletRequest request, @RequestHeader("Authorization") String token, MultipartFile image) {
        User self = JSON.parseObject(JSON.toJSONString(redisCacheUtils.getCache(token)), User.class);
        if (self == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR, "未登录/无效请求");
        }
        String upload = FileUtils.upload(request, image);
        Map<String, String> stringStringMap = new HashMap<>();
        stringStringMap.put("url", upload);
        return ResultUtils.success(stringStringMap, "上传成功");
    }

    /**
     * 通用上传请求（多个）
     */
    @ApiOperation("多文件上传")
    @PostMapping("/uploads")
    public BaseResponse<List<Map<String, String>>> uploadFiles(HttpServletRequest request, @RequestHeader("Authorization") String token, List<MultipartFile> files) {
        User self = JSON.parseObject(JSON.toJSONString(redisCacheUtils.getCache(token)), User.class);
        if (self == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR, "未登录/无效请求");
        }

        List<Map<String, String>> list = new ArrayList<>();
        files.forEach(file -> {
            String upload = FileUtils.upload(request, file);
            Map<String, String> stringStringMap = new HashMap<>();
            stringStringMap.put("url", upload);
            list.add(stringStringMap);
        });

        return ResultUtils.success(list, "上传成功");
    }
}
