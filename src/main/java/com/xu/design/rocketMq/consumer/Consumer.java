package com.xu.design.rocketMq.consumer;


import cn.hutool.json.JSONUtil;
import com.xu.design.DTO.mq.MqMessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * 设置消息监听
 * 监听组：监听topic：监听tag(默认监听topic下所有)
 * 监听消费模式:默认负载均衡：CLUSTERING（每一个消息只发给一个消费者）、广播模式：BROADCASTING（发送给所有消费者）
 *
 */
@Component
@RocketMQMessageListener(consumerGroup = "${rocketmq.consumer.group}", topic = "${rocketmq.consumer.topic}",
        selectorExpression = "${rocketmq.consumer.tags}", messageModel = MessageModel.BROADCASTING)
@Slf4j
public class Consumer implements RocketMQListener<MqMessageDTO> {
    @Override
    public void onMessage(MqMessageDTO message) {
        log.info("消息监听--->" + JSONUtil.toJsonStr(message));
    }
}
