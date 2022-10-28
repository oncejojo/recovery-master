package com.jojo.recovery.common.enums;

/**
 * Exception的错误代码 与 错误信息 的 枚举
 */
public enum JsonResultEnum {

    UNKONW_ERROR(-1, "未知错误"),
    SUCCESS(0, "成功"),

    TARGET_NULL(100, "对象不存在"),
    ADMIN_USER_NULL(101, "用户名输入错误！"),
    ADMIN_PASS_ERROR(102, "密码输入错误！"),
    ADMIN_USER_LOCKING(103, "该管理员已被系统锁定！"),
    USER_NULL(104, "用户不存在"),
    USER_EXISTS(105, "用户已存在"),
    USER_OPENID_NULL(106,"支付宝验证失败"),
    USER_ROLE_NULL(107, "用户角色未指定"),
    COIN_NOT_ENOUGH(108, "余额不足"),
    COIN_NOT_ENOUGH2(109,"不足200"),
    FATHER_NULL(110,"邀请人不存在"),
    AUTH_CODE_ERROR(111,"验证码错误"),

    ORDER_NULL(202, "订单不存在"),
    ORDER_EXISTS(203, "重复下单"),
    PARAM_NULL(204, "参数不能为空"),
    TITLE_NULL(205, "标题不能为空"),
    IMAGE_NULL(205, "图片不能为空"),
    CONTENT_NULL(205, "内容不能为空"),
    PRICE_NULL(205, "价格不能为空"),
    NUMBER_NULL(205, "数量不能为空"),

    GOODS_SELLED(302, "商品已被拍走"),
    GOODS_NULL(303, "商品不存在"),
    SIGN_EXISTS(304, "重复签到"),

    BALANCE_INSUFFICIENT(305,"余额不足"),
    PRICE_OVER_SETTING(306, "转拍价格超出上限")
    ;

    private Integer code;

    private String msg;

    JsonResultEnum(Integer code, String msg) {
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
