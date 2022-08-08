package com.xu.design.common.enums;

import lombok.Getter;

@Getter
public enum PayTypeEnum {

    WX(1000, "微信支付"),

    ALIPAY(2000, "微信支付"),

    BANK_CARD(3000, "微信支付");

    PayTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer code;
    public String desc;
}
