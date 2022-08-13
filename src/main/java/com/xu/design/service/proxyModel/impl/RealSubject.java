package com.xu.design.service.proxyModel.impl;

import com.xu.design.service.proxyModel.Subject;

public class RealSubject implements Subject {
    @Override
    public void doWork() {
        System.out.println("Hello World!!!");
    }
}
