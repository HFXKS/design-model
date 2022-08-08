package com.xu.design.DTO.mq;


import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;

/**
 * rocketmq异步回调监听
 */
@Slf4j
public class SendCallbackListener implements SendCallback {

    private Long id;

    public SendCallbackListener(Long id) {
        this.id = id;
    }

    @Override
    public void onSuccess(SendResult sendResult) {
        log.info("CallBackListener on success : " + JSONUtil.toJsonStr(sendResult));
    }

    @Override
    public void onException(Throwable throwable) {
        log.error("CallBackListener on exception : ", throwable);
    }
}
