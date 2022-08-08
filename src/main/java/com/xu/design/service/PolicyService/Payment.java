package com.xu.design.service.PolicyService;

import com.xu.design.common.enums.PayTypeEnum;

public interface Payment {
    /**
     * 获取支付方式
     *
     * @return 响应，支付方式
     */
    PayTypeEnum getPayType();

    /**
     * 支付调用
     *
     * @param orderId 订单id
     * @return 响应，支付结果
     */
    void pay(Long orderId);
}
