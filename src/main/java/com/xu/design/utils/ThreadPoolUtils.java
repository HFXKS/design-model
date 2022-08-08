package com.xu.design.utils;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Setter
@Getter
public class ThreadPoolUtils {

    /**
     * 持有私有静态实例，防止被引用，此处赋值为null，目的是实现延迟加载，volatile防止指令重排序，在instance未被new时就被返回
     */
    private static volatile ThreadPoolUtils instance = null;

    // 线程池对象
    private ThreadPoolExecutor threadPoolExecutor;


    private ThreadPoolUtils() {

    }

    public static synchronized ThreadPoolUtils getInstance() {
        if (instance == null) {
            synchronized(ThreadPoolUtils.class){
                if(instance == null){
                    instance = new ThreadPoolUtils();
                }
            }
        }
        return instance;
    }

    public static ThreadPoolExecutor getThreadPool(){
        return ThreadPoolUtils.getInstance().getThreadPoolExecutor();
    }

    /**
     * 打印线程池信息
     */
    public void getThreadPoolInfo() {
        log.info("thread pool core size:{}",threadPoolExecutor.getCorePoolSize());
        log.info("thread pool total size:{}",threadPoolExecutor.getPoolSize());
        log.info("thread pool queue size:{}",threadPoolExecutor.getQueue().size());
    }
}
