package vip.ilstudy.entity;

import lombok.Data;

/**
 * 二维码登录实体
 */
@Data
public class QRLoginEntity {
    /**
     * 登录token
     */
    private String token;
    private String uuid;
    private Status status;

    public enum Status{
        WAITING, //等待扫码
        SCANNED, //已扫码, 但未确认
        SUCCESS, //已扫描，并确认
        FAILED, // 已扫描，但被取消
        EXPIRED // 已过期
    }
}
