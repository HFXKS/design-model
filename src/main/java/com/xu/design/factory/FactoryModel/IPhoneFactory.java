package com.xu.design.factory.FactoryModel;

import com.xu.design.service.factory.impl.IPhone;
import com.xu.design.service.factory.Phone;

public class IPhoneFactory implements Factory{
    @Override
    public Phone createPhone() {
        return new IPhone();
    }
}
