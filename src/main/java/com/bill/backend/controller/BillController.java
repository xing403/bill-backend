package com.bill.backend.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bill.backend.modules.BaseResponse;
import com.bill.backend.modules.constant.ErrorCode;
import com.bill.backend.modules.entity.Bill;
import com.bill.backend.modules.entity.User;
import com.bill.backend.modules.exception.BusinessException;
import com.bill.backend.service.BillService;
import com.bill.backend.service.UserService;
import com.bill.backend.utils.ResultUtils;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@Api(tags = "账单")
@RequestMapping("/bill")
public class BillController {
    @Resource
    private BillService billService;
    @Resource
    private UserService userService;

    @PostMapping("/addition")
    public BaseResponse<Boolean> addBill(@RequestHeader("Authorization") String token, @RequestBody Bill bill) {
        User currentUser = userService.getCurrentUser(token);

        if (bill.getAmount() == null || bill.getAmount() == 0 || bill.getTitle().isEmpty() || bill.getType() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数错误");
        }
        if (currentUser.getAuth().isEmpty()) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "无权限");
        }
        Boolean b = billService.addBill(bill);
        return ResultUtils.success(b, "添加成功");
    }

    @RequestMapping("/update")
    public BaseResponse<Integer> updateBill(@RequestHeader("Authorization") String token, @RequestBody Bill bill) {
        User currentUser = userService.getCurrentUser(token);
        if (bill.getId() == null || bill.getId() == 0)
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数错误");

        if (currentUser.getAuth().isEmpty() || currentUser.getAuth().equals("user") ||!Objects.equals(currentUser.getId(), bill.getUserId()))
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "无权限");

        Bill byIdAndUserId = billService.getByIdAndUserId(bill.getId(), currentUser.getId());

        if(byIdAndUserId == null)
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "未找到该账单");

        Integer i = billService.updateBill(bill);

        if( i == 0)
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "更新失败");
        return ResultUtils.success(i, "更新成功");
    }

    @RequestMapping("/delete")
    public BaseResponse<Integer> deleteBill(@RequestHeader("Authorization") String token, @RequestBody Bill bill) {
        User currentUser = userService.getCurrentUser(token);

        Bill byId = billService.getById(bill.getId());
        if (byId == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "未找到该账单");
        }
        if (byId.getIsDelete() == 1) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "该账单已删除");
        }
        if (currentUser.getAuth().isEmpty() || currentUser.getAuth().equals("user") || !Objects.equals(currentUser.getId(), byId.getUserId())) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "无权限");
        }

        Integer i = billService.deleteBill(bill.getId());
        return ResultUtils.success(i, "删除成功");
    }

    @PostMapping("/listByUserIdAndDataTime")
    public BaseResponse<Map<String, Object>> listByUserIdAndDataTime(
            @RequestHeader("Authorization") String token,
            @RequestParam("dataTime") String dataTime,
            @RequestParam("page") Long pageNum,
            @RequestParam("pageSize") Integer pageSize) {
        User currentUser = userService.getCurrentUser(token);

        if (currentUser.getAuth().isEmpty()) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "无权限");
        }
        if (pageNum == null || pageNum < 0 || pageSize == null || pageSize < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数错误");
        }
        Page<Bill> billPage = new Page<>(pageNum, pageSize);
        Bill bill = new Bill();
        bill.setUserId(currentUser.getId());
        bill.setDataTime(dataTime);
        bill.setIsDelete(0);
        IPage<Bill> byUserIdAndDataTime = billService.findByUserIdAndDataTime(billPage, bill);
        Map<String, Object> map = new HashMap<>();

        map.put("total", byUserIdAndDataTime.getTotal());
        map.put("list", byUserIdAndDataTime.getRecords());
        map.put("page", byUserIdAndDataTime.getCurrent());
        map.put("size", byUserIdAndDataTime.getSize());

        return ResultUtils.success(map);
    }

    @RequestMapping("/getById")
    public BaseResponse<Bill> listByUserId(@RequestHeader("Authorization") String token, @RequestParam("id") Long id) {
        User currentUser = userService.getCurrentUser(token);

        if (currentUser.getAuth().isEmpty())
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "无权限");

        if (id == null || id < 0)
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数错误");

        Bill byIdAndUserId = billService.getByIdAndUserId(id, currentUser.getId());

        if (byIdAndUserId == null)
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "未找到该账单");

        return ResultUtils.success(byIdAndUserId, "获取成功");
    }

    @PostMapping("/incomeAndExpenseByMonth")
    public BaseResponse<List<Map<String, Object>>> getDetailByMonth(@RequestHeader("Authorization") String token, @RequestParam("dataTime") String dataTime) {
        User currentUser = userService.getCurrentUser(token);
        if(dataTime == null || dataTime.isEmpty())
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数错误");

        List<Map<String, Object>> incomeAndExpenseByMonth = billService.getIncomeAndExpenseByMonth(dataTime, currentUser.getId());
        return ResultUtils.success(incomeAndExpenseByMonth, "获取成功");
    }
}
