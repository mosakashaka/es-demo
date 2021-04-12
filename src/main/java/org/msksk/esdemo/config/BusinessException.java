package org.msksk.esdemo.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.msksk.esdemo.constants.ResultCodeEnum;

@Data
@EqualsAndHashCode(callSuper = true)
public class BusinessException extends RuntimeException {

    private String code;

    /**
     * 继承exception，加入错误状态值
     *
     * @param codeEnum
     */
    public BusinessException(ResultCodeEnum codeEnum) {
        super(codeEnum.getMessage());
        this.code = codeEnum.getCode();
    }

    /**
     * 自定义错误信息
     *
     * @param message
     * @param code
     */
    public BusinessException(String message, String code) {
        super(message);
        this.code = code;
    }

    /**
     * 业务异常
     *
     * @param message
     */
    public BusinessException(String message) {
        super(message);
        this.code = ResultCodeEnum.BUSINESS_ERROR.getCode();

    }
}


