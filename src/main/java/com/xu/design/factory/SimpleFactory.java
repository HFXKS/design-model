package com.xu.design.factory;

import com.xu.design.service.simpleFactory.Product;
import com.xu.design.service.simpleFactory.impl.ProductA;
import com.xu.design.service.simpleFactory.impl.ProductB;

/**
 * 简单工厂
 * 违反开闭原则
 * 对修改关闭 对扩展开放
 * 有新增需修改工厂类
 */
public class SimpleFactory {
    public static Product createProduct(String type){
        if ("A".equals(type)){
            return new ProductA();
        }else {
            return new ProductB();
        }
    }
}
