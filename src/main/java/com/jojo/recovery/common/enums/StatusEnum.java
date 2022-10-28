package com.jojo.recovery.common.enums;

public enum StatusEnum {
    STATUS_CREATE(0,"创建"),
    STATUS_HANDLE(1,"处理"),
    STATUS_CONFIRM(2,"确认"),
    STATUS_COMPLETE(3,"完成"),
    STATUS_FAIL(4,"失败"),
    ;

    private Integer code;

    private String msg;

    StatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
