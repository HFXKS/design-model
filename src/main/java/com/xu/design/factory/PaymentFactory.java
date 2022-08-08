package com.xu.design.factory;

import com.xu.design.common.enums.PayTypeEnum;
import com.xu.design.service.PolicyService.Payment;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 策略模式
 */
@Component
public class PaymentFactory implements InitializingBean, ApplicationContextAware {

    private static final Map<PayTypeEnum, Payment> payStrategies = new HashMap<>();

    private ApplicationContext appContext;

    public static Payment getPayment(PayTypeEnum payType) {
        if (payType == null) {
            throw new IllegalArgumentException("pay type is empty.");
        }
        if (!payStrategies.containsKey(payType)) {
            throw new IllegalArgumentException("pay type not supported.");
        }
        return payStrategies.get(payType);
    }

    /**
     * 实现InitializingBean接口，初始化bean时，对某个具体的bean(Payment的实现类)进行增强处理
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        // 将 Spring 容器中所有的 Payment 接口实现类注册到 payStrategies
        Map<String, Payment> beansOfType = appContext.getBeansOfType(Payment.class);
        beansOfType.values().forEach(payment ->
                        payStrategies.put(payment.getPayType(), payment)
                );
    }

    /**
     * 容器本身ApplicationContext对象传递给自定义appContext
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        appContext = applicationContext;
    }
}
