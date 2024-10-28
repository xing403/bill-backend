package vip.ilstudy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vip.ilstudy.config.constant.Constant;
import vip.ilstudy.entity.QRLoginEntity;
import vip.ilstudy.entity.ResultEntity;
import vip.ilstudy.service.RedisCacheService;
import vip.ilstudy.utils.ResultUtils;
import vip.ilstudy.utils.StringUtils;
import vip.ilstudy.utils.UUIDUtils;

import java.util.concurrent.TimeUnit;

/**
 * 二维码登录
 */
@RestController
@RequestMapping("/qrcode")
public class QRCodeLoginController extends BaseController {

    @Autowired
    private RedisCacheService redisCacheService;

    /**
     * 生成二维码
     *
     * @return
     */
    @GetMapping("")
    public ResultEntity<String> generateQRCode() {
        String uuid = UUIDUtils.getUUID();
        QRLoginEntity qrLoginEntity = new QRLoginEntity();
        qrLoginEntity.setStatus(QRLoginEntity.Status.WAITING);
        qrLoginEntity.setUuid(uuid);
        redisCacheService.setCacheObject(
                Constant.QRCODE_LOGIN_KEY + uuid,
                qrLoginEntity,
                Constant.QRCODE_LOGIN_EXPIRE_TIME,
                TimeUnit.SECONDS
        );
        return ResultUtils.success(uuid);
    }

    /**
     * 二维码状态
     *
     * @return
     */
    @GetMapping("/{qrcode}/status")
    public ResultEntity<String> getQRCodeStatus(@PathVariable("qrcode") String qrcode) {
        if (redisCacheService.hasKey(Constant.QRCODE_LOGIN_KEY + qrcode)) {
            QRLoginEntity qRLoginEntity = redisCacheService.getCacheObject(Constant.QRCODE_LOGIN_KEY + qrcode);
            return ResultUtils.success(qRLoginEntity.getStatus().toString());
        }
        return ResultUtils.error("二维码已过期");
    }

    @GetMapping("/{qrcode}/information")
    public ResultEntity<QRLoginEntity> getQRCodeInformation(@PathVariable("qrcode") String qrcode) {
        if (redisCacheService.hasKey(Constant.QRCODE_LOGIN_KEY + qrcode)) {
            QRLoginEntity qRLoginEntity = redisCacheService.getCacheObject(Constant.QRCODE_LOGIN_KEY + qrcode);
            if (StringUtils.isNotNull(qRLoginEntity) && qRLoginEntity.getStatus() == QRLoginEntity.Status.SUCCESS) {
                redisCacheService.deleteObject(Constant.QRCODE_LOGIN_KEY + qrcode);
                return ResultUtils.success(qRLoginEntity);
            }
        }
        return ResultUtils.error("二维码已过期");
    }

    @PutMapping("/change/{qrcode}/scanned")
    public ResultEntity<String> changeScannedStatusScanned(@PathVariable("qrcode") String qrcode) {
        if (redisCacheService.hasKey(Constant.QRCODE_LOGIN_KEY + qrcode)) {
            QRLoginEntity qRLoginEntity = redisCacheService.getCacheObject(Constant.QRCODE_LOGIN_KEY + qrcode);
            if (qRLoginEntity.getStatus() == QRLoginEntity.Status.WAITING) {
                qRLoginEntity.setStatus(QRLoginEntity.Status.SCANNED);
                redisCacheService.setCacheObject(Constant.QRCODE_LOGIN_KEY + qrcode, qRLoginEntity);
                return ResultUtils.success();
            }
        }
        return ResultUtils.error("二维码已失效");
    }

    @PutMapping("/change/{qrcode}/success")
    public ResultEntity<String> changeScannedStatusSuccess(@PathVariable("qrcode") String qrcode, @RequestBody String token) {
        if (redisCacheService.hasKey(Constant.QRCODE_LOGIN_KEY + qrcode)) {
            QRLoginEntity qRLoginEntity = redisCacheService.getCacheObject(Constant.QRCODE_LOGIN_KEY + qrcode);
            if (qRLoginEntity.getStatus() != QRLoginEntity.Status.SCANNED) {
                return ResultUtils.error("二维码状态异常");
            }
            qRLoginEntity.setStatus(QRLoginEntity.Status.SUCCESS);
            qRLoginEntity.setToken(token);
            redisCacheService.setCacheObject(Constant.QRCODE_LOGIN_KEY + qrcode, qRLoginEntity);
            return ResultUtils.success();
        }
        return ResultUtils.error("二维码已过期");
    }

    @PutMapping("/change/{qrcode}/failed")
    public ResultEntity<String> changeScannedStatusFailed(@PathVariable("qrcode") String qrcode) {
        if (redisCacheService.hasKey(Constant.QRCODE_LOGIN_KEY + qrcode)) {
            QRLoginEntity qRLoginEntity = redisCacheService.getCacheObject(Constant.QRCODE_LOGIN_KEY + qrcode);

            if (qRLoginEntity.getStatus() != QRLoginEntity.Status.SCANNED) {
                return ResultUtils.error("二维码状态异常");
            }

            qRLoginEntity.setStatus(QRLoginEntity.Status.CANCELED);
            redisCacheService.setCacheObject(Constant.QRCODE_LOGIN_KEY + qrcode, qRLoginEntity);
            return ResultUtils.success();
        }
        return ResultUtils.error("二维码已过期");
    }
}
