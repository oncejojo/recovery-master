package com.jojo.recovery.common.enums;

public enum RecordEnum {
    STATUS_FIRST(0,"签到"),
    STATUS_SECOND(1,"转让"),
    STATUS_THIRD(2,"积分购物"),
    STATUS_FOURTH(3,"拍卖"),
    STATUS_FIFTH(4,"直接用户"),
    STATUS_SIXTH(5,"间接用户"),
    TYPE_0(0,"增加"),
    TYPE_1(1,"减少"),
    ;

    private Integer code;

    private String msg;

    RecordEnum(Integer code, String msg) {
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
