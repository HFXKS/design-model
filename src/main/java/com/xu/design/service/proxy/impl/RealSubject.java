package com.xu.design.service.proxy.impl;

import com.xu.design.service.proxy.Subject;

public class RealSubject implements Subject {
    @Override
    public void doWork() {
        System.out.println("Hello World!!!");
    }
}
