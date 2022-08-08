package com.xu.design.service.chainResp.impl;

import com.xu.design.service.chainResp.Handler;

public class Leader extends Handler {

    @Override
    public void process(Integer info) {
        if (info > 0 && info < 10){
            System.out.println("Leader 处理！");
        }else {
            super.nextHandler.process(info);
        }
    }
}
