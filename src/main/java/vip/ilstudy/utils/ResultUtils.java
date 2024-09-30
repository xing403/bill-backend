package vip.ilstudy.utils;

import lombok.Data;
import vip.ilstudy.entity.ResultEntity;

import java.io.Serializable;

/**
 * 响应体类
 */
@Data
public class ResultUtils implements Serializable {
    /**
     * 响应成功
     */
    public static <T> ResultEntity<T> success() {
        ResultEntity<T> result = new ResultEntity<>();
        result.setCode(200);
        result.setMessage("success");
        return result;
    }

    /**
     * 响应成功
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResultEntity<T> success(T data) {
        ResultEntity<T> result = new ResultEntity<>();
        result.setCode(200);
        result.setMessage("success");
        result.setData(data);
        return result;
    }

    /**
     * 响应成功
     *
     * @param data
     * @param message
     * @param <T>
     * @return
     */
    public static <T> ResultEntity<T> success(T data, String message) {
        ResultEntity<T> result = new ResultEntity<>();
        result.setCode(200);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    /**
     * 响应失败
     *
     * @param code
     * @param error
     * @param <T>
     * @return
     */
    public static <T> ResultEntity<T> error(Integer code, String error) {
        ResultEntity<T> result = new ResultEntity<>();
        result.setCode(code);
        result.setMessage(error);
        return result;
    }

    public static <T> ResultEntity<T> error(String error) {
        ResultEntity<T> result = new ResultEntity<>();
        result.setCode(500);
        result.setMessage(error);
        return result;
    }
}
