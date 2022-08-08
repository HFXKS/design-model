package com.xu.design.service.factory.impl;

import com.xu.design.service.factory.Phone;

public class IPhone implements Phone {
    @Override
    public void print() {
        System.out.println("IPhone");
    }
}
