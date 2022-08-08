package com.xu.design.service.state.impl;

import com.xu.design.service.state.State;

public class Sad implements State {
    @Override
    public void doWork() {
        System.out.println("Sad implements");
    }
}
