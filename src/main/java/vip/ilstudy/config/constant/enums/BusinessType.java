package vip.ilstudy.config.constant.enums;

public enum BusinessType {
    /**
     * 其它
     */
    OTHER(0, "other"),

    /**
     * 新增
     */
    INSERT(1, "insert"),

    /**
     * 修改
     */
    UPDATE(2, "update"),

    /**
     * 删除
     */
    DELETE(3, "delete");


    BusinessType(Integer code, String name) {
    }
}