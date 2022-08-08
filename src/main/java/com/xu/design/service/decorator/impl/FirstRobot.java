package com.xu.design.service.decorator.impl;

import com.xu.design.service.decorator.Robot;
import org.springframework.stereotype.Service;

@Service
public class FirstRobot implements Robot {
    @Override
    public void doSomething() {
        System.out.println("A");
        System.out.println("B");
    }
}
