package com.xu.design.config;

import com.xu.design.utils.ThreadPoolUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 初始化加载系统常用资源
 */
@Component
@Order(3)
public class ThreadPoolConfig implements CommandLineRunner {

    @Value("${threadPoolExecutor.corePoolSize:15}")
    private Integer corePoolSize;

    @Value("${threadPoolExecutor.maximumPoolSize:30}")
    private Integer maximumPoolSize;

    @Value("${threadPoolExecutor.keepAliveTime:30}")
    private Integer keepAliveTime;


    @Override
    public void run(String... args) {
        ThreadPoolUtils threadPoolUtils = ThreadPoolUtils.getInstance();
        ThreadPoolExecutor threadPoolExecutor = threadPoolUtils.getThreadPoolExecutor();
        if(threadPoolExecutor == null){
            threadPoolExecutor = new ThreadPoolExecutor(
                    corePoolSize,
                    maximumPoolSize,
                    keepAliveTime,
                    TimeUnit.SECONDS,
                    new ArrayBlockingQueue<>(100),
                    new ThreadPoolExecutor.CallerRunsPolicy());
            threadPoolUtils.setThreadPoolExecutor(threadPoolExecutor);
        }
//        threadPoolUtils.getThreadPoolInfo();
    }
}
