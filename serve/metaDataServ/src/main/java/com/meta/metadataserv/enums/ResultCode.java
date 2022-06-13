package com.meta.metadataserv.enums;

import java.io.Serializable;

public enum ResultCode implements Serializable {
    SUCCESS("200", "成功"),
    USER_ERROR("A0001", "用户端错误"),
    USER_LOGIN_ERROR("A0200", "用户登录异常"),
    USER_NOT_EXIST("A0201", "用户不存在"),
    USER_ACCOUNT_LOCKED("A0202", "用户账户被冻结"),
    USER_ACCOUNT_INVALID("A0203", "用户账户已作废"),
    USERNAME_OR_PASSWORD_ERROR("A0210", "用户名或密码错误"),
    INPUT_PASSWORD_EXCEED_LIMIT("A0211", "用户输入密码次数超限"),
    CLIENT_AUTHENTICATION_FAILED("A0212", "客户端认证失败"),
    TOKEN_INVALID_OR_EXPIRED("A0230", "登录状态已失效，请重新登录！"),
    USER_AUTHORIZED_ERROR("A0300", "访问权限异常"),
    USER_ACCESS_UNAUTHORIZED("A0301", "访问未授权"),
    USER_REQUEST_PARAM_ERROR("A0400", "用户请求参数错误"),
    USER_REQUEST_PARAM_IS_BLANK("A0410", "请求必填参数为空"),
    SYSTEM_EXECUTION_ERROR("500", "系统执行出错"),
    SYSTEM_CUSTOM_ERROR("400", "自定义异常"),
    SYSTEM_EXECUTION_TIMEOUT("B0100", "系统执行超时");

    private String code;
    private String msg;

    public String getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    private ResultCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private ResultCode() {
    }
}
