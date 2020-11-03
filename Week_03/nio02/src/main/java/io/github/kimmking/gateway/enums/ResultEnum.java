package io.github.kimmking.gateway.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {

    SUCCESS(0, "成功"),

    FAILED(1, "失败"),

    PARAM_ERROR(1, "参数不正确"),

    PARAM_TOKEN_ERROR(2, "Token信息不存在"),
    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
