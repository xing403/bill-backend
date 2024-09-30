package vip.ilstudy.utils;

import java.util.UUID;

/**
 * UUID 工具类
 */
public class UUIDUtils {
    /**
     * 获取UUID
     *
     * @return String
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
