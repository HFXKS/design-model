package com.xu.design.service.activityService.impl;

import com.xu.design.common.anno.ExtensionInject;
import com.xu.design.model.ActivityModel;
import com.xu.design.service.activityService.ActivityPermitExtension;
import org.springframework.stereotype.Component;

@Component
@ExtensionInject("ACTIVITY_CHANCE_PERMIT_ABILITY")
public class ActivityChancePermitAbility implements ActivityPermitExtension {

    @Override
    public void isPermit(ActivityModel activityModel) {
        System.out.println("ACTIVITY_CHANCE_PERMIT_ABILITY");
    }

}