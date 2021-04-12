package org.msksk.esdemo.constants;

public enum ResultCodeEnum {

    SUCCESS("200", "成功"),
    DATA_ERROR("600", "数据异常"),
    NO_HANDLER_FOUND("404", "404"),
    BUSINESS_ERROR("-100", "服务器错误"),
    UNKNOW_ERROR("500", "服务器异常!");

    private String code;

    private String message;

    ResultCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
