package com.xu.design.factory.FactoryModel;

import com.xu.design.service.factory.impl.HuaWei;
import com.xu.design.service.factory.Phone;

public class HuaWeiFactory implements Factory {
    @Override
    public Phone createPhone() {
        return new HuaWei();
    }
}
