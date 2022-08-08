package com.xu.design.service.chainResp.impl;

import com.xu.design.service.chainResp.Handler;

public class Boss extends Handler {

    @Override
    public void process(Integer info) {
        System.out.println("Boss 处理！");
    }
}
