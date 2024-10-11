package vip.ilstudy.entity;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.util.List;

/**
 * 列表分页实体
 */
@Data
public class TablePageEntity<T> {
    /**
     * 当前页码
     */
    private Long pageNum;
    /**
     * 每页条数
     */
    private Long pageSize;
    /**
     * 数据列表
     */
    private List<T> data;
    /**
     * 总条数
     */
    private Long total;

    public TablePageEntity(IPage<T> page) {
        this.setData(page.getRecords());
        this.setPageNum(page.getCurrent());
        this.setPageSize(page.getSize());
        this.setTotal(page.getTotal());
    }

    public TablePageEntity(List<T> data,Long pageNum,Long pageSize,Long total) {
        this.setData(data);
        this.setPageNum(pageNum);
        this.setPageSize(pageSize);
        this.setTotal(total);
    }
}
