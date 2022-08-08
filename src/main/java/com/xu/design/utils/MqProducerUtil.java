package com.xu.design.utils;

import cn.hutool.core.lang.generator.SnowflakeGenerator;
import com.xu.design.DTO.mq.SendCallbackListener;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 消息发送util
 * @param <T>
 */
@Component
public class MqProducerUtil<T> {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    public void sendAsyncTag(String tag, T t){
        Long id = new SnowflakeGenerator().next();
        Message<T> message = MessageBuilder.withPayload(t)
                .setHeader(RocketMQHeaders.KEYS, id)
                .build();
        // 设置发送地和消息信息并发送异步消息
        rocketMQTemplate.asyncSend(tag, message, new SendCallbackListener(id));
    }
}
