package com.xu.design.service.simpleFactory.impl;

import com.xu.design.service.simpleFactory.Product;

public class ProductA implements Product {
    @Override
    public void print() {
        System.out.println("A");
    }
}
