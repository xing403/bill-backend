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
    public ResultEntity<BillEntity> getBillById(@PathVariable("billId") Long billId) {
        BillEntity billById = billService.getBillById(billId);
        return ResultUtils.success(billById);
    }

    /**
     * 查询账单列表
     *
     * @return
     */
    @GetMapping("")
    public ResultEntity<TablePageEntity<BillEntity>> getBillList(@RequestParam("pageNum") Long pageNum, @RequestParam("pageSize") Long pageSize) {
        if (pageNum == null) {
            pageNum = 1L;
        }
        if (pageSize == null) {
            pageSize = 10L;
        }
        IPage<BillEntity> billListPage = billService.getBillListPage(pageNum, pageSize);

        return ResultUtils.success(new TablePageEntity<>(billListPage));
    }

    @PostMapping("")
    public ResultEntity<Boolean> addBill(@Valid @RequestBody BillEntity billEntity) {
        Integer res = billService.insertBill(billEntity);
        if (res > 0) {
            return ResultUtils.success();
        }
        return ResultUtils.error(500, "添加失败");
    }
}
