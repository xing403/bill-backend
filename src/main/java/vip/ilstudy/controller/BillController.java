package vip.ilstudy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vip.ilstudy.config.annotation.Log;
import vip.ilstudy.config.constant.enums.BusinessType;
import vip.ilstudy.entity.BillEntity;
import vip.ilstudy.entity.ResultEntity;
import vip.ilstudy.entity.TablePageEntity;
import vip.ilstudy.utils.ResultUtils;
import vip.ilstudy.service.BillService;

@RestController
@RequestMapping("/bill")
public class BillController extends BaseController {

    @Autowired
    private BillService billService;

    /**
     * 根据id查询账单信息
     *
     * @param billId
     * @return
     */
    @GetMapping("{billId}")
    public ResultEntity<BillEntity> getBillById(@Valid @PathVariable("billId") Long billId) {
        BillEntity billById = billService.getBillById(billId);
        return ResultUtils.success(billById);
    }

    /**
     * 查询账单列表
     *
     * @return
     */
    @GetMapping("list")
    @Log(title = "查询", businessType = BusinessType.OTHER)
    public ResultEntity<TablePageEntity<BillEntity>> getBillList(@RequestParam("pageNum") Long pageNum, @RequestParam("pageSize") Long pageSize) {
        IPage<BillEntity> billListPage = billService.getBillListPage(pageNum, pageSize);

        return ResultUtils.success(new TablePageEntity<>(billListPage));
    }

    /**
     * 查询账单列表
     *
     * @return
     */
    @PutMapping("")
    @Log(title = "更新账单", businessType = BusinessType.UPDATE)
    public ResultEntity<Boolean> updateBill(@Valid @RequestBody BillEntity billEntity) {
        if (billService.updateBillById(billEntity)) {
            return ResultUtils.success();
        }
        return ResultUtils.error(500, "添加失败");
    }

    @PostMapping("")
    @Log(title = "新增账单", businessType = BusinessType.INSERT)
    public ResultEntity<Boolean> addBill(@Valid @RequestBody BillEntity billEntity) {
        Integer res = billService.insertBill(billEntity);
        if (res > 0) {
            return ResultUtils.success();
        }
        return ResultUtils.error(500, "添加失败");
    }

    @DeleteMapping("{billId}")
    @Log(title = "删除账单", businessType = BusinessType.DELETE)
    public ResultEntity<Boolean> deleteBill(@Valid @PathVariable Long billId) {
        if (billService.deleteBillById(billId)) {
            return ResultUtils.success();
        }
        return ResultUtils.error(500, "添加失败");
    }
}
