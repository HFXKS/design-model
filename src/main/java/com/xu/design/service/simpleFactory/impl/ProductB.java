package com.xu.design.service.simpleFactory.impl;

import com.xu.design.service.simpleFactory.Product;

public class ProductB implements Product {
    @Override
    public void print() {
        System.out.println("B");
    }
}
