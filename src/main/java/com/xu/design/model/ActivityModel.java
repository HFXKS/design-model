package com.xu.design.model;

import com.google.common.collect.Lists;
import com.xu.design.extension.ExtContext;
import com.xu.design.extension.ExtStrategy;
import com.xu.design.extension.IExtContext;
import com.xu.design.utils.CommonUtils;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class ActivityModel implements IExtContext {

    /**
     * 活动准入策略
     */
    private List<ExtStrategy> permitStrategies;

    /**
     * 策略缓存
     */
    private Map<String, List<ExtStrategy>> strategyCache;

    @Override
    public ExtContext buildContext() {
        return ExtContext.newContext(fetchStrategy());
    }

    private Map<String, List<ExtStrategy>> fetchStrategy() {
        if (CommonUtils.isEmpty(this.strategyCache)) {
            List<ExtStrategy> strategies = Lists.newArrayList();
            // 添加活动准入策略
            if (CommonUtils.isNotEmpty(this.permitStrategies)) {
                strategies.addAll(this.permitStrategies);
            }
            // 添加活动申请策略
//            strategies.addAll(this.type.getApplyStrategies());
            this.strategyCache = strategies.stream().collect(Collectors.groupingBy(ExtStrategy::getExtension));
        }
        return this.strategyCache;
    }
}
