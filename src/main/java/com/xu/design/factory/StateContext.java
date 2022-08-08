package com.xu.design.factory;

import com.xu.design.service.state.State;

/**
 * 状态模式
 * 类似适配器
 */
public class StateContext {
    private State state;

    public StateContext(State state) {
        this.state = state;
    }

    public void doWork(){
        state.doWork();
    }
}
