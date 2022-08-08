package com.xu.design.service.activityService;

import com.xu.design.common.anno.ExtensionInject;
import com.xu.design.model.ActivityModel;

@ExtensionInject("ACTIVITY_PERMIT_EXTENSION")
public interface ActivityPermitExtension {

    void isPermit(ActivityModel activityModel);
}
