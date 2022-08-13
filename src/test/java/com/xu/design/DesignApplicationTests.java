package com.xu.design;

import com.xu.design.common.enums.PayTypeEnum;
import com.xu.design.factory.FactoryModel.IPhoneFactory;
import com.xu.design.factory.PaymentFactory;
import com.xu.design.factory.SimpleFactory;
import com.xu.design.factory.StateContext;
import com.xu.design.service.chainResp.impl.Boss;
import com.xu.design.service.chainResp.impl.Leader;
import com.xu.design.service.decorator.impl.FirstRobot;
import com.xu.design.service.decorator.impl.RobotDecorator;
import com.xu.design.service.proxyModel.impl.RealSubjectProxy;
import com.xu.design.service.state.impl.Happy;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DesignApplicationTests {

//    @Resource
//    private PaymentFactory paymentFactory;

    /**
     * 注解式策略test
     */
    @Test
    void AnnoPolicyTest() {
        PaymentFactory.getPayment(PayTypeEnum.WX).pay(1L);
    }

    /**
     * 装饰器test
     */
    @Test
    void DecoratorTest() {
        new RobotDecorator(new FirstRobot()).doMorething();
    }

    /**
     * 简单工厂test
     */
    @Test
    void SimpleFactoryTest(){
        SimpleFactory.createProduct("A").print();
    }

    /**
     * 工厂test
     */
    @Test
    void FactoryTest(){
        new IPhoneFactory().createPhone().print();
    }

    /**
     * 状态test
     */
    @Test
    void StateTest(){
        StateContext stateContext = new StateContext(new Happy());
        stateContext.doWork();
    }

    /**
     * 代理test
     */
    @Test
    void ProxyTest(){
        new RealSubjectProxy().doWork();
    }

    /**
     * 责任链test
     */
    @Test
    void HandlerTest(){
        Leader leader = new Leader();
        Boss boss = new Boss();
        leader.setNextHandler(boss);
        leader.process(9);
    }
}
