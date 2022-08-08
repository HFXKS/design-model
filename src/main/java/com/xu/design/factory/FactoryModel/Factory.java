package com.xu.design.factory.FactoryModel;

import com.xu.design.service.factory.Phone;

/**
 * 工厂模式
 * 抽象工厂即为多个方法（违反开闭原则）
 */
public interface Factory {
    Phone createPhone();
}
