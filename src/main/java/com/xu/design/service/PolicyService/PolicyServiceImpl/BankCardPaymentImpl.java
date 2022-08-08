package com.xu.design.service.PolicyService.PolicyServiceImpl;

import com.xu.design.common.enums.PayTypeEnum;
import com.xu.design.service.PolicyService.Payment;
import org.springframework.stereotype.Service;

@Service
public class BankCardPaymentImpl implements Payment {
    @Override
    public PayTypeEnum getPayType() {
        return PayTypeEnum.BANK_CARD;
    }

    @Override
    public void pay(Long orderId) {
        System.out.println(PayTypeEnum.BANK_CARD );
    }
}
