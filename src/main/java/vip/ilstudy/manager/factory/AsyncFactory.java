package vip.ilstudy.manager.factory;

import vip.ilstudy.entity.LogEntity;
import vip.ilstudy.entity.UserEntity;
import vip.ilstudy.service.LogService;
import vip.ilstudy.service.UserService;
import vip.ilstudy.utils.SpringUtils;

import java.util.TimerTask;

/**
 * 异步工厂（产生任务用）
 */
public class AsyncFactory {

    /**
     * 记录用户登录时间
     * @param userEntity
     * @return
     */
    public static TimerTask recordLoginInfo(final UserEntity userEntity) {
        return new TimerTask() {
            @Override
            public void run() {
                SpringUtils.getBean(UserService.class).updateUserLoginTimeByUsername(userEntity);
            }
        };
    }

    /**
     * 记录 操作日志
     */
    public static TimerTask recordLog(final LogEntity logEntity) {
        return new TimerTask() {
            @Override
            public void run() {
                SpringUtils.getBean(LogService.class).insertLogEntity(logEntity);
            }
        };
    }
}
