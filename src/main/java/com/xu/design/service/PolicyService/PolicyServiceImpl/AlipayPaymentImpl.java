package com.xu.design.service.PolicyService.PolicyServiceImpl;

import com.xu.design.common.enums.PayTypeEnum;
import com.xu.design.service.PolicyService.Payment;
import org.springframework.stereotype.Service;

@Service
public class AlipayPaymentImpl implements Payment {
    @Override
    public PayTypeEnum getPayType() {
        return PayTypeEnum.ALIPAY;
    }

    @Override
    public void pay(Long orderId) {
        System.out.println(PayTypeEnum.ALIPAY);
    }
}
