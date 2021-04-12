package org.msksk.esdemo.config;

import lombok.extern.slf4j.Slf4j;
import org.msksk.esdemo.constants.ResultCodeEnum;
import org.msksk.esdemo.dto.Result;
import org.msksk.esdemo.utils.R;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    /**
     * RestDescribeException 异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = BusinessException.class)
    public Result doException(BusinessException e) {
        e.printStackTrace();
        log.info("异常:",e);
        return R.error(e.getCode(), e.getMessage());
    }

    /**
     * NoHandlerFoundException 异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = NoHandlerFoundException.class)
    public Result doException(NoHandlerFoundException e) {
        e.printStackTrace();
        log.info("异常:",e);
        return R.error(ResultCodeEnum.NO_HANDLER_FOUND);
    }

    /**
     * HttpRequestMethodNotSupportedException 异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public Result doException(HttpRequestMethodNotSupportedException e) {
        e.printStackTrace();
        log.info("异常:",e);
        return R.error(ResultCodeEnum.NO_HANDLER_FOUND);
    }


    /**
     * Exception 异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = RuntimeException.class)
    public Result doRuntimeException(RuntimeException e) {
        e.printStackTrace();
        log.error("异常:",e);
        return R.error(ResultCodeEnum.UNKNOW_ERROR.getCode(),e.getMessage());
    }

    /**
     * Exception 异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public Result doException(Exception e) {
        e.printStackTrace();
        log.error("异常:",e);;
        return R.error(ResultCodeEnum.UNKNOW_ERROR.getCode(),e.getMessage());
    }
}
