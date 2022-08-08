package com.xu.design.service.state.impl;

import com.xu.design.service.state.State;

public class Angry implements State {
    @Override
    public void doWork() {
        System.out.println("Angry");
    }
}
