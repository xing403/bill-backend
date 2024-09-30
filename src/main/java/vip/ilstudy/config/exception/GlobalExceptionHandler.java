package vip.ilstudy.config.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import vip.ilstudy.entity.ResultEntity;
import vip.ilstudy.utils.ResultUtils;

import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {
    Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 校验参数异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultEntity<?> MethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String message = Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage();
        log.error(message);
        return ResultUtils.error(message);
    }

    /**
     * 请求路径中缺少必需的路径变量
     */
    @ExceptionHandler(MissingPathVariableException.class)
    public ResultEntity<?> handleMissingPathVariableException(MissingPathVariableException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();

        log.error(String.format("请求路径中缺少必需的路径变量[%s]", e.getVariableName()));
        return ResultUtils.error(String.format("请求路径中缺少必需的路径变量[%s]", e.getVariableName()));
    }

    /**
     * 运行异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    public ResultEntity doHandleRuntimeException(RuntimeException e) {
        log.error("系统异常");
        e.printStackTrace();
        return ResultUtils.error("系统异常");
    }
}
