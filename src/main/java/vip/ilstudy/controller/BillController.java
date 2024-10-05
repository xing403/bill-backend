package vip.ilstudy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vip.ilstudy.entity.BillEntity;
import vip.ilstudy.entity.ResultEntity;
import vip.ilstudy.entity.TablePageEntity;
import vip.ilstudy.utils.ResultUtils;
import vip.ilstudy.service.BillService;

@RestController
@RequestMapping("/bill")

public class BillController extends BaseController {
    private static final long serialVersionUID = 1L;

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
    public ResultEntity<Boolean> updateBill(@Valid @RequestBody BillEntity billEntity) {
        if (billService.updateBillById(billEntity)) {
            return ResultUtils.success();
        }
        return ResultUtils.error(500, "添加失败");
    }

    @PostMapping("")
    public ResultEntity<Boolean> addBill(@Valid @RequestBody BillEntity billEntity) {
        Integer res = billService.insertBill(billEntity);
        if (res > 0) {
            return ResultUtils.success();
        }
        return ResultUtils.error(500, "添加失败");
    }

    @DeleteMapping("{billId}")
    public ResultEntity<Boolean> deleteBill(@Valid @PathVariable Long billId) {
        if (billService.deleteBillById(billId)) {
            return ResultUtils.success();
        }
        return ResultUtils.error(500, "添加失败");
    }
}
